package org.ahk.qrclassscheduler.classroom.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Classroom {
    private final String id;
    private final String name;
    private final int size;
}
