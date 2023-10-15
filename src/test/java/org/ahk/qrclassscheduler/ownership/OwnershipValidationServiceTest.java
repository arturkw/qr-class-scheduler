package org.ahk.qrclassscheduler.ownership;

import org.ahk.qrclassscheduler.registrationcode.model.ActivityRegistrationCode;
import org.ahk.qrclassscheduler.security.SecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("prd")
public class OwnershipValidationServiceTest {

    @Autowired
    OwnershipValidationService ownershipValidationService;

    @MockBean
    SessionRegistry sessionRegistry;
    @MockBean
    SecurityService securityService;

    @Test
    @DisplayName("ownership validated")
    void ownership_validated_ok() {
        String sessionId = "sessionId1234";
        String userName = "user1";

        User user = new User(userName, "pass1", Collections.emptyList());
        SessionInformation sessionInformation = new SessionInformation(user, sessionId, new Date());

        when(sessionRegistry.getSessionInformation(eq(sessionId))).thenReturn(sessionInformation);
        when(securityService.loggedUser()).thenReturn(user);

        ownershipValidationService.validateCodeOwnership(ActivityRegistrationCode.builder().sessionId(sessionId).build());
    }

    @Test
    @DisplayName("ownership validation failed: 'Unable to find session information'")
    void ownership_validation_failed_no_session() {
        String sessionId = "sessionId1234";
        when(sessionRegistry.getSessionInformation(eq(sessionId))).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ownershipValidationService.validateCodeOwnership(ActivityRegistrationCode.builder().sessionId(sessionId).build())
        );
        assertEquals("Unable to find session information.", exception.getMessage());
    }

    @Test
    @DisplayName("ownership validation failed: 'Foreign session id'")
    void ownership_validation_failed_foreign_session() {
        String sessionId = "sessionId1234";
        String userName = "user1";

        User user = new User(userName, "pass1", Collections.emptyList());
        User user2 = new User("user2", "pass2", Collections.emptyList());
        SessionInformation sessionInformation = new SessionInformation(user, sessionId, new Date());

        when(sessionRegistry.getSessionInformation(eq(sessionId))).thenReturn(sessionInformation);
        when(securityService.loggedUser()).thenReturn(user2);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ownershipValidationService.validateCodeOwnership(ActivityRegistrationCode.builder().sessionId(sessionId).build())
        );
        assertEquals("Foreign session id.", exception.getMessage());
    }

}