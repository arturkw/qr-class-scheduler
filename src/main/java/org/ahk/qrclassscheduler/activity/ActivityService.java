package org.ahk.qrclassscheduler.activity;

import lombok.RequiredArgsConstructor;
import org.ahk.qrclassscheduler.activity.model.Activity;
import org.ahk.qrclassscheduler.activity.model.ActivityDto;
import org.ahk.qrclassscheduler.activity.model.CourseDto;
import org.ahk.qrclassscheduler.classroom.model.ClassroomDto;
import org.ahk.qrclassscheduler.qrcode.QRCodeService;
import org.ahk.qrclassscheduler.registrationcode.RegistrationCodeService;
import org.ahk.qrclassscheduler.registrationcode.model.ActivityRegistrationCode;
import org.ahk.qrclassscheduler.registrationcode.model.ClassroomQrCode;
import org.ahk.qrclassscheduler.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final Map<String, List<Activity>> activitiesByClassRoom = new ConcurrentHashMap<>();
    private final QRCodeService qrCodeService;
    private final RegistrationCodeService registrationCodeService;
    private final ValidationService validationService;

    public List<Activity> classRoomActivities(String classRoomQrCode) {
        String classRoomId = qrCodeService.decode(classRoomQrCode);
        return activitiesByClassRoom.get(classRoomId);
    }

    public void addActivities(List<Activity> activities) {
        activitiesByClassRoom.putAll(activities.stream().collect(Collectors.groupingBy(a -> a.getClassroom().getId())));
    }

    public List<ActivityDto> activitiesDto(String classRoomQrCode, String sessionId) {
        ClassroomQrCode code = registrationCodeService.classRoomQrCode(classRoomQrCode);
        List<Activity> activities = classRoomActivities(code.getClassRoomId());
        return activities.stream().map(a -> ActivityDto.builder()
                        .startDate(a.getStartDate())
                        .classroom(ClassroomDto.builder().classRoomQrCode(a.getClassroom().getId()).build())
                        .course(CourseDto.builder().name(a.getCourse().getName()).build())
                        .registrationQrCode(registrationCodeService.activityQrCodeAsString(sessionId, a.getId()))
                        .build())
                .toList();
    }

    public void registerActivity(String registrationQrCode, String userName) {
        ActivityRegistrationCode registrationCode = registrationCodeService.activityRegistrationCode(registrationQrCode);
        String activityId = registrationCode.getActivityId();
        findActivity(activityId)
                .ifPresentOrElse(activity -> {
                    validationService.validate(userName, activity);
                    activity.getRegisteredUsers().add(userName);
                }, () -> {
                    throw new RuntimeException("Activity not found!");
                });
    }

    private Optional<Activity> findActivity(String activityId) {
        return activitiesByClassRoom.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(a -> activityId.equals(a.getId().toString())).findFirst();
    }

}
