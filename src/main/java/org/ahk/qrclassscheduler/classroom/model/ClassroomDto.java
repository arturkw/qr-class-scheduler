package org.ahk.qrclassscheduler.classroom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClassroomDto {
    private final String classRoomQrCode;
}
