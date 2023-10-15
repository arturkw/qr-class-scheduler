package org.ahk.qrclassscheduler.classroom;

import lombok.RequiredArgsConstructor;
import org.ahk.qrclassscheduler.classroom.model.Classroom;
import org.ahk.qrclassscheduler.classroom.model.ClassroomDto;
import org.ahk.qrclassscheduler.registrationcode.RegistrationCodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final List<Classroom> classrooms = new ArrayList<>();
    private final RegistrationCodeService registrationCodeService;

    @Value("${registration.code.lifeTimeInMinutes:5}")
    private Integer codeLifeTime;

    public void addClassrooms(List<Classroom> classrooms) {
        this.classrooms.addAll(classrooms);
    }

    @Override
    public List<Classroom> classrooms() {
        return Collections.unmodifiableList(classrooms);
    }

    @Override
    public List<ClassroomDto> classRoomDtos(String jSessionId) {
        return classrooms.stream().map(c -> toDto(c, jSessionId)).toList();
    }

    private ClassroomDto toDto(Classroom c, String jSessionId) {
        return ClassroomDto.builder()
                .classRoomQrCode(registrationCodeService.classRoomQrCodeAsString(jSessionId, c.getId()))
                .build();
    }

}
