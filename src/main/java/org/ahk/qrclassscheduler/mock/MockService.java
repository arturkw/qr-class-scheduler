package org.ahk.qrclassscheduler.mock;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.ahk.qrclassscheduler.activity.ActivityService;
import org.ahk.qrclassscheduler.activity.model.Activity;
import org.ahk.qrclassscheduler.activity.model.Course;
import org.ahk.qrclassscheduler.classroom.ClassroomService;
import org.ahk.qrclassscheduler.classroom.model.Classroom;
import org.ahk.qrclassscheduler.clock.ClockService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MockService {
    private final ActivityService activityService;
    private final ClockService clockService;
    private final CourseGeneratorService courseGeneratorService;
    private final ClassGeneratorService classGeneratorService;
    private final ClassroomService classroomService;

    @PostConstruct
    void mockData() {
        mockClassRooms();
        mockActivities();
    }

    private void mockActivities() {
        List<Classroom> classrooms = classroomService.classrooms();
        List<Activity> activities = new LinkedList<>();
        for (Classroom classroom : classrooms) {
            for (int i = 0; i <= 23; i++) {
                LocalDateTime startTime = LocalDateTime.of(clockService.localDateNow(), LocalTime.of(i, 0, 0));
                Course course = courseGeneratorService.generateCourse();
                activities.add(new Activity(course, classroom, startTime, Collections.synchronizedList(new LinkedList<>())));
            }
        }
        activityService.addActivities(activities);
    }

    private void mockClassRooms() {
        List<Classroom> classrooms = classGeneratorService.generateClassRooms();
        classroomService.addClassrooms(classrooms);
    }

    @RequiredArgsConstructor
    @Service
    static class ClassGeneratorService {
        @Value("${classroom.minSize:5}")
        private Integer minClassroomSize;
        @Value("${classroom.maxSize:25}")
        private Integer maxClassRoomSize;
        @Value("${classroom.count:10}")
        private Integer classroomCount;

        List<Classroom> generateClassRooms() {
            int maxClassroomNum = Color.values().length * Fruit.values().length;
            if (classroomCount > maxClassroomNum) {
                throw new RuntimeException(String.format("Number of classrooms too high. Should not be grater than %s", maxClassroomNum));
            }
            List<Classroom> randomClassRooms = new ArrayList<>();
            GENERATE_CLASSROOMS:
            for (Color color : Color.values()) {
                for (Fruit fruit : Fruit.values()) {
                    String classroomName = String.format("%s %s", color, fruit);
                    if (randomClassRooms.size() < classroomCount) {
                        String id = RandomStringUtils.random(4, true, false).toUpperCase();
                        randomClassRooms.add(new Classroom(id, classroomName, randomSize()));
                    } else {
                        break GENERATE_CLASSROOMS;
                    }
                }
            }
            Collections.shuffle(randomClassRooms);
            return randomClassRooms;
        }

        int randomSize() {
            return new Random().nextInt(maxClassRoomSize - minClassroomSize) + minClassroomSize;
        }

        private enum Color {
            Blue,
            Red,
            Green,
            Orange,
            Pink,
            Gray,
            Yellow
        }

        private enum Fruit {
            Apple,
            Strawberry,
            Watermelon,
            Lemon,
            Kiwi,
            Avocado
        }
    }

    @RequiredArgsConstructor
    @Service
    static class CourseGeneratorService {
        private static String courseName() {
            return String.format("%s %s", level(), subject());
        }

        static Level level() {
            return Level.values()[new Random().nextInt(Level.values().length)];
        }

        static Subject subject() {
            return Subject.values()[new Random().nextInt(Subject.values().length)];
        }

        Course generateCourse() {
            return new Course(courseName());
        }

        private enum Subject {
            Biology,
            Chemistry,
            Math,
            History
        }

        private enum Level {
            Elementary,
            Intermediate,
            Advanced
        }
    }

}
