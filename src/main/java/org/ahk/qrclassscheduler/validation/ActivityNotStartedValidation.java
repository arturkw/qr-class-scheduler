package org.ahk.qrclassscheduler.validation;

import lombok.RequiredArgsConstructor;
import org.ahk.qrclassscheduler.activity.model.Activity;
import org.ahk.qrclassscheduler.clock.ClockService;
import org.ahk.qrclassscheduler.validation.exception.ActivityStartedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ActivityNotStartedValidation implements ValidationStrategy {

    private final ClockService clockService;

    @Override
    public boolean validate(String userName, Activity activity) {
        if (activity.getStartDate().isBefore(clockService.localDateTimeNow())) {
            throw new ActivityStartedException();
        }
        return true;
    }
}
