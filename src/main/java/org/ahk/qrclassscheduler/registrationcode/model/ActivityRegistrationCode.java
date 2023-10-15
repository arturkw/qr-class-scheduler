package org.ahk.qrclassscheduler.registrationcode.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActivityRegistrationCode {
    private String activityId;
    private String sessionId;
    private LocalDateTime expiresAt;
}
