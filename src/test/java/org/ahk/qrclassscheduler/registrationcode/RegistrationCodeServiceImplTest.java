package org.ahk.qrclassscheduler.registrationcode;

import org.ahk.qrclassscheduler.ownership.OwnershipValidationService;
import org.ahk.qrclassscheduler.registrationcode.model.ActivityRegistrationCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@ActiveProfiles("test")
public class RegistrationCodeServiceImplTest {

    @Autowired
    RegistrationCodeService registrationCodeService;

    @MockBean
    OwnershipValidationService ownershipValidationService;

    @Test
    @DisplayName("registration code is created and encoded correctly")
    public void registration_code() {
        UUID activityId = UUID.randomUUID();
        String sessionId = "sessionId12345";

        doNothing().when(ownershipValidationService).validateCodeOwnership(any());

        String qrCode = registrationCodeService.registrationQrCode(sessionId, activityId);
        ActivityRegistrationCode registrationCode = registrationCodeService.activityRegistrationCode(qrCode);

        assertEquals(sessionId, registrationCode.getSessionId());
        assertEquals(activityId.toString(), registrationCode.getActivityId());
    }

}
