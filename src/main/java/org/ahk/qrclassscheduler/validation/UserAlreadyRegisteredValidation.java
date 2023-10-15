package org.ahk.qrclassscheduler.validation;

import org.ahk.qrclassscheduler.activity.model.Activity;
import org.ahk.qrclassscheduler.validation.exception.UserAlreadyRegisteredException;
import org.springframework.stereotype.Component;

@Component
class UserAlreadyRegisteredValidation implements ValidationStrategy {
    @Override
    public boolean validate(String userName, Activity activity) {
        if (activity.getRegisteredUsers().contains(userName)) {
            throw new UserAlreadyRegisteredException();
        }
        return true;
    }
}
