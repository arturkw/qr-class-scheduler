package org.ahk.qrclassscheduler.ownership;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahk.qrclassscheduler.security.SecurityService;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnershipValidationService {
    private final SessionRegistry sessionRegistry;
    private final SecurityService securityService;

    @SneakyThrows
    public void validateCodeOwnership(String jSessionId) {
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(jSessionId);
        if (sessionInformation == null) {
            throw new RuntimeException("Unable to find session information.");
        }
        User user = ((User) sessionInformation.getPrincipal());
        if (user != securityService.loggedUser()) {
            throw new RuntimeException("Foreign session id.");
        }
    }


}
