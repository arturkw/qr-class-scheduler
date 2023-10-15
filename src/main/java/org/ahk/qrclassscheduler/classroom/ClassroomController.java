package org.ahk.qrclassscheduler.classroom;

import lombok.RequiredArgsConstructor;
import org.ahk.qrclassscheduler.classroom.model.ClassroomDto;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping("/classrooms")
    List<ClassroomDto> classrooms(@CookieValue(value = "JSESSIONID") String sessionId) {
        return classroomService.classRoomDtos(sessionId);
    }

}
