package org.ahk.qrclassscheduler.activity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ahk.qrclassscheduler.classroom.model.Classroom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Activity {
    private final UUID id = UUID.randomUUID();
    private Course course;
    private Classroom classroom;
    private LocalDateTime startDate;
    private List<String> registeredUsers;
}
