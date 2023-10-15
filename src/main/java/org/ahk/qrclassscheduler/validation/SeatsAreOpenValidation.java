package org.ahk.qrclassscheduler.validation;

import org.ahk.qrclassscheduler.activity.model.Activity;
import org.ahk.qrclassscheduler.validation.exception.SeatsNotAvailableException;
import org.springframework.stereotype.Component;

@Component
class SeatsAreOpenValidation implements ValidationStrategy {

    @Override
    public boolean validate(String userName, Activity activity) {
        if (activity.getClassroom().getSize() <= activity.getRegisteredUsers().size()) {
            throw new SeatsNotAvailableException();
        }
        return true;
    }

}
