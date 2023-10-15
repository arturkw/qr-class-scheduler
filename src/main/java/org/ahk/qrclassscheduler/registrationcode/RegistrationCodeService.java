package org.ahk.qrclassscheduler.registrationcode;

import org.ahk.qrclassscheduler.registrationcode.model.ActivityRegistrationCode;
import org.ahk.qrclassscheduler.registrationcode.model.ClassroomQrCode;

import java.util.UUID;

public interface RegistrationCodeService {

    String activityQrCodeAsString(String jSessionId, UUID activityId);

    ActivityRegistrationCode activityRegistrationCode(String registrationQrCode);

    String classRoomQrCodeAsString(String classRoomId);

    ClassroomQrCode classRoomQrCode(String classroomQrCode);
}
