package org.ahk.qrclassscheduler.activity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ahk.qrclassscheduler.classroom.model.ClassroomDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityDto {
    private String registrationQrCode;
    private CourseDto course;
    private ClassroomDto classroom;
    private LocalDateTime startDate;
}
