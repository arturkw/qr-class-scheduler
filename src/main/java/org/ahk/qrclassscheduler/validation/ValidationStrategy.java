package org.ahk.qrclassscheduler.validation;

import org.ahk.qrclassscheduler.activity.model.Activity;

interface ValidationStrategy {
    boolean validate(String userName, Activity activity);
}
