package org.ahk.qrclassscheduler.validation;

import org.ahk.qrclassscheduler.activity.model.Activity;

public interface ValidationService {

    boolean validate(String userName, Activity activity);

}
