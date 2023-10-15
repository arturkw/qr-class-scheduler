package org.ahk.qrclassscheduler.classroom;

import org.ahk.qrclassscheduler.classroom.model.Classroom;
import org.ahk.qrclassscheduler.classroom.model.ClassroomDto;

import java.util.List;

public interface ClassroomService {
    void addClassrooms(List<Classroom> classrooms);

    List<Classroom> classrooms();

    //This method displays QR codes visible on the LCD screen for every class
    //It should not be here
    //TODO: Each QR code should be valid for given period of time and be signed with a key...
    List<ClassroomDto> classRoomDtos();
}
