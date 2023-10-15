package org.ahk.qrclassscheduler.validation;

import org.ahk.qrclassscheduler.activity.model.Activity;
import org.springframework.stereotype.Component;

@Component
class UserNotRegisteredForAnotherActivity implements ValidationStrategy {
    @Override
    public boolean validate(String userName, Activity activity) {
        //TODO: check if user not registered for another course at the same hour..
        return true;
    }

}
