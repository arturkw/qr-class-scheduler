package org.ahk.qrclassscheduler.registrationcode;

import lombok.RequiredArgsConstructor;
import org.ahk.qrclassscheduler.clock.ClockService;
import org.ahk.qrclassscheduler.mapper.MapperService;
import org.ahk.qrclassscheduler.ownership.OwnershipValidationService;
import org.ahk.qrclassscheduler.qrcode.QRCodeService;
import org.ahk.qrclassscheduler.registrationcode.model.ActivityRegistrationCode;
import org.ahk.qrclassscheduler.registrationcode.model.ClassroomQrCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationCodeServiceImpl implements RegistrationCodeService {

    private final QRCodeService qrCodeService;
    private final MapperService mapperService;
    private final ClockService clockService;
    private final OwnershipValidationService ownershipValidationService;

    @Value("${registration.code.lifeTimeInMinutes:5}")
    private Integer codeLifeTime;

    @Override
    public String activityQrCodeAsString(String jSessionId, UUID activityId) {
        ActivityRegistrationCode registrationCode = ActivityRegistrationCode.builder()
                .activityId(activityId.toString())
                .sessionId(jSessionId)
                .expiresAt(clockService.localDateTimeNow().plusMinutes(codeLifeTime))
                .build();
        return qrCodeService.encode(mapperService.toJson(registrationCode));
    }

    @Override
    public ActivityRegistrationCode activityRegistrationCode(String registrationQrCode) {
        String json = qrCodeService.decode(registrationQrCode);
        ActivityRegistrationCode registrationCode = mapperService.fromString(json, ActivityRegistrationCode.class);
        ownershipValidationService.validateCodeOwnership(registrationCode.getSessionId());
        if (registrationCode.getExpiresAt().isBefore(clockService.localDateTimeNow())) {
            throw new RuntimeException("Registration Code Expired.");
        }
        return registrationCode;
    }

    @Override
    public String classRoomQrCodeAsString(String jSessionId, String classRoomId) {
        ClassroomQrCode qrCode = ClassroomQrCode.builder()
                .classRoomId(classRoomId)
                .sessionId(jSessionId)
                .expiresAt(clockService.localDateTimeNow().plusMinutes(codeLifeTime))
                .build();
        return qrCodeService.encode(mapperService.toJson(qrCode));
    }

    @Override
    public ClassroomQrCode classRoomQrCode(String classroomQrCode) {
        ClassroomQrCode code = mapperService.fromString(classroomQrCode, ClassroomQrCode.class);
        ownershipValidationService.validateCodeOwnership(code.getSessionId());
        if (clockService.localDateTimeNow().isAfter(code.getExpiresAt())) {
            throw new RuntimeException("Classroom Code Expired.");
        }
        return code;
    }
}
