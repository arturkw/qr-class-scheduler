package org.ahk.qrclassscheduler.activity;

import lombok.RequiredArgsConstructor;
import org.ahk.qrclassscheduler.activity.model.Activity;
import org.ahk.qrclassscheduler.activity.model.ActivityDto;
import org.ahk.qrclassscheduler.activity.model.CourseDto;
import org.ahk.qrclassscheduler.classroom.model.ClassroomDto;
import org.ahk.qrclassscheduler.clock.ClockService;
import org.ahk.qrclassscheduler.qrcode.QRCodeService;
import org.ahk.qrclassscheduler.registrationcode.RegistrationCodeService;
import org.ahk.qrclassscheduler.registrationcode.model.ActivityRegistrationCode;
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
    private final ClockService clockService;

    private List<Activity> classRoomActivities(String classRoomQrCode) {
        String classRoomId = qrCodeService.decode(classRoomQrCode);
        return activitiesByClassRoom.get(classRoomId);
    }

    public void addActivities(List<Activity> activities) {
        activitiesByClassRoom.putAll(activities.stream().collect(Collectors.groupingBy(a -> a.getClassroom().getId())));
    }

    public List<ActivityDto> activitiesDto(String classRoomQrCode, String sessionId) {
        List<Activity> activities = classRoomActivities(classRoomQrCode);
        return activities.stream().map(a -> ActivityDto.builder()
                        .startDate(a.getStartDate())
                        .classroom(ClassroomDto.builder().classRoomQrCode(a.getClassroom().getId()).build())
                        .course(CourseDto.builder().name(a.getCourse().getName()).build())
                        .registrationQrCode(registrationCodeService.registrationQrCode(sessionId, a.getId()))
                        .build())
                .toList();
    }

    public void registerActivity(String registrationQrCode, String userName) {
        ActivityRegistrationCode registrationCode = registrationCodeService.activityRegistrationCode(registrationQrCode);
        String activityId = registrationCode.getActivityId();
        findActivity(activityId)
                .ifPresentOrElse(activity -> {
                    validate(activity, userName);
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

    private void validate(Activity activity, String userName) {
        if (activity.getClassroom().getSize() <= activity.getRegisteredUsers().size()) {
            throw new RuntimeException("All seats are occupied.");
        }
        if (activity.getStartDate().isBefore(clockService.localDateTimeNow())) {
            throw new RuntimeException("Course activity already started.");
        }
        if (activity.getRegisteredUsers().contains(userName)) {
            throw new RuntimeException("User already registered.");
        }
    }

}
