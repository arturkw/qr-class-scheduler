package org.ahk.qrclassscheduler.registrationcode.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActivityRegistrationCode {
    private final String activityId;
    private final String sessionId;
    private final LocalDateTime expiresAt;
}
