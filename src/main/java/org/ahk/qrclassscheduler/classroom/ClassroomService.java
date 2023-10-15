package org.ahk.qrclassscheduler.classroom;

import org.ahk.qrclassscheduler.classroom.model.Classroom;
import org.ahk.qrclassscheduler.classroom.model.ClassroomDto;

import java.util.List;

public interface ClassroomService {
    void addClassrooms(List<Classroom> classrooms);

    List<Classroom> classrooms();

    List<ClassroomDto> classRoomDtos(String jSessionId);
}
