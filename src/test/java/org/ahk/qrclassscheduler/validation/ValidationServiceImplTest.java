package org.ahk.qrclassscheduler.validation;

import org.ahk.qrclassscheduler.activity.model.Activity;
import org.ahk.qrclassscheduler.activity.model.Course;
import org.ahk.qrclassscheduler.classroom.model.Classroom;
import org.ahk.qrclassscheduler.validation.exception.ActivityStartedException;
import org.ahk.qrclassscheduler.validation.exception.SeatsNotAvailableException;
import org.ahk.qrclassscheduler.validation.exception.UserAlreadyRegisteredException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class ValidationServiceImplTest {

    @Autowired
    ValidationService validationService;

    @Test
    @DisplayName("validation passed")
    void validation_passed() {
        Course course = new Course("course1");
        Classroom classroom = new Classroom("classId1", "Red Cherry", 2);
        Activity activity = new Activity(course, classroom, LocalDateTime.now().plusMinutes(15), Collections.emptyList());
        assertTrue(validationService.validate("user1", activity));
    }

    @Test
    @DisplayName("no available seats")
    void seats_not_available() {
        Course course = new Course("course1");
        Classroom classroom = new Classroom("classId1", "Red Cherry", 1);
        Activity activity = new Activity(course, classroom, LocalDateTime.now().plusMinutes(15), Collections.singletonList("user2"));
        assertThrows(SeatsNotAvailableException.class, () -> validationService.validate("user1", activity));
    }

    @Test
    @DisplayName("activity already started")
    void activity_started() {
        Course course = new Course("course1");
        Classroom classroom = new Classroom("classId1", "Red Cherry", 1);
        Activity activity = new Activity(course, classroom, LocalDateTime.now().minusMinutes(15), Collections.singletonList("user2"));
        assertThrows(ActivityStartedException.class, () -> validationService.validate("user1", activity));
    }

    @Test
    @DisplayName("user already registered")
    void user_already_registered() {
        Course course = new Course("course1");
        Classroom classroom = new Classroom("classId1", "Red Cherry", 2);
        Activity activity = new Activity(course, classroom, LocalDateTime.now().plusMinutes(15), Collections.singletonList("user1"));
        assertThrows(UserAlreadyRegisteredException.class, () -> validationService.validate("user1", activity));
    }

}