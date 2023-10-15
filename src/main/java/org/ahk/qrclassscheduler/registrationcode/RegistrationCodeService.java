package org.ahk.qrclassscheduler.registrationcode;

import org.ahk.qrclassscheduler.registrationcode.model.ActivityRegistrationCode;

import java.util.UUID;

public interface RegistrationCodeService {

    String registrationQrCode(String sessionId, UUID activityId);

    ActivityRegistrationCode activityRegistrationCode(String registrationQrCode);
}
