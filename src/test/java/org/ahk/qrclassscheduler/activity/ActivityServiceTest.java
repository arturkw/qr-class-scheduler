package org.ahk.qrclassscheduler.activity;

import org.ahk.qrclassscheduler.activity.model.Activity;
import org.ahk.qrclassscheduler.activity.model.ActivityDto;
import org.ahk.qrclassscheduler.activity.model.Course;
import org.ahk.qrclassscheduler.classroom.model.Classroom;
import org.ahk.qrclassscheduler.qrcode.QRCodeService;
import org.ahk.qrclassscheduler.registrationcode.RegistrationCodeService;
import org.ahk.qrclassscheduler.registrationcode.model.ActivityRegistrationCode;
import org.ahk.qrclassscheduler.registrationcode.model.ClassroomQrCode;
import org.ahk.qrclassscheduler.validation.ValidationService;
import org.ahk.qrclassscheduler.validation.exception.ActivityStartedException;
import org.ahk.qrclassscheduler.validation.exception.SeatsNotAvailableException;
import org.ahk.qrclassscheduler.validation.exception.UserAlreadyRegisteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.ahk.qrclassscheduler.activity.ActivityServiceTest.CommonData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {
    @InjectMocks
    ActivityService activityService;
    @Mock
    QRCodeService qrCodeService;
    @Mock
    RegistrationCodeService registrationCodeService;
    @Mock
    ValidationService validationService;

    @BeforeEach
    void initMocks() {
        Activity activity = getActivity1(2);
        activityService.addActivities(Collections.singletonList(activity));
        ActivityRegistrationCode registrationCode = registrationCode(activity, jsession1);
        ActivityRegistrationCode registrationCode2 = registrationCode(activity, jsession2);
        ActivityRegistrationCode registrationCode3 = registrationCode(activity, jsession3);

        lenient().when(registrationCodeService.activityRegistrationCode(qrcode1)).thenReturn(registrationCode);
        lenient().when(registrationCodeService.activityRegistrationCode(qrcode2)).thenReturn(registrationCode2);
        lenient().when(registrationCodeService.activityRegistrationCode(qrcode3)).thenReturn(registrationCode3);
        lenient().when(registrationCodeService.classRoomQrCode(qrcode1)).thenReturn(ClassroomQrCode.builder().classRoomId(qrcode1).build());
        lenient().when(registrationCodeService.classRoomQrCode(qrcode2)).thenReturn(ClassroomQrCode.builder().classRoomId(qrcode2).build());
        lenient().when(qrCodeService.decode(anyString())).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    @DisplayName("Registration ok: User is assigned to activity")
    void user_assigned_to_activity() {
        //when:
        activityService.registerActivity(qrcode1, userName1);

        //then:
        List<Activity> activities = activityService.classRoomActivities(qrcode1);
        assertEquals(1, activities.get(0).getRegisteredUsers().size());
        assertTrue(activities.get(0).getRegisteredUsers().contains(userName1));
    }

    @Test
    @DisplayName("Registration failed: user already registered")
    void user_already_registered_exception() {
        activityService.registerActivity(qrcode1, userName1);
        when(validationService.validate(eq(userName1), any())).thenThrow(new UserAlreadyRegisteredException());

        //when:
        UserAlreadyRegisteredException exception = assertThrows(UserAlreadyRegisteredException.class,
                () -> activityService.registerActivity(qrcode1, userName1));

        List<Activity> activities = activityService.classRoomActivities(qrcode1);
        assertEquals(1, activities.size());
    }

    @Test
    @DisplayName("Registration failed: all seats are occupied")
    void all_seats_are_occupied_so_exception_is_thrown() {
        activityService.registerActivity(qrcode1, userName1);
        activityService.registerActivity(qrcode2, userName2);
        when(validationService.validate(eq(userName2), any())).thenThrow(new SeatsNotAvailableException());

        //when:
        SeatsNotAvailableException exception = assertThrows(SeatsNotAvailableException.class,
                () -> activityService.registerActivity(qrcode2, userName2));

        List<Activity> activities = activityService.classRoomActivities(qrcode1);
        assertEquals(1, activities.size());
    }

    @Test
    @DisplayName("Registration failed: activity already started")
    void activity_already_started() {
        when(validationService.validate(eq(userName1), any())).thenThrow(new ActivityStartedException());

        ActivityStartedException exception = assertThrows(ActivityStartedException.class,
                () -> activityService.registerActivity(qrcode1, userName1));

        List<Activity> activities = activityService.classRoomActivities(qrcode1);
        assertEquals(1, activities.size());
    }


    @Test
    @DisplayName("Activities DTO returned")
    void activities_dto() {
        List<ActivityDto> activities = activityService.activitiesDto(qrcode1, jsession1);
        assertEquals(1, activities.size());
    }

    private Activity getActivity1(int size) {
        Course course = new Course("course1");
        Classroom classroom = new Classroom(qrcode1, "Red", size);
        return new Activity(course, classroom, LocalDateTime.now().plusMinutes(15), new LinkedList<>());
    }

    private ActivityRegistrationCode registrationCode(Activity activity, String jsession) {
        return ActivityRegistrationCode.builder()
                .activityId(activity.getId().toString()).sessionId(jsession)
                .expiresAt(LocalDateTime.now().plusMinutes(15)).build();
    }

    static class CommonData {
        static String qrcode1 = "qrcode1";
        static String userName1 = "user1";
        static String jsession1 = "jsession1";

        static String qrcode2 = "qrcode2";
        static String userName2 = "user2";
        static String jsession2 = "jsession2";

        static String qrcode3 = "qrcode3";
        static String userName3 = "user3";
        static String jsession3 = "jsession3";
    }


}