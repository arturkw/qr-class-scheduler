package org.ahk.qrclassscheduler.classroom;

import lombok.RequiredArgsConstructor;
import org.ahk.qrclassscheduler.classroom.model.Classroom;
import org.ahk.qrclassscheduler.classroom.model.ClassroomDto;
import org.ahk.qrclassscheduler.qrcode.QRCodeService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final List<Classroom> classrooms = new ArrayList<>();
    private final QRCodeService qrCodeService;

    public void addClassrooms(List<Classroom> classrooms) {
        this.classrooms.addAll(classrooms);
    }

    @Override
    public List<Classroom> classrooms() {
        return Collections.unmodifiableList(classrooms);
    }

    @Override
    public List<ClassroomDto> classRoomDtos() {
        return classrooms.stream().map(this::toDto).toList();
    }

    private ClassroomDto toDto(Classroom c) {
        return ClassroomDto.builder()
                .classRoomQrCode(qrCodeService.encode(c.getId()))
                .build();
    }

}
