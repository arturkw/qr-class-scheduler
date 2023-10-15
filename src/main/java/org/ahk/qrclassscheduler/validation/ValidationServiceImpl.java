package org.ahk.qrclassscheduler.validation;

import lombok.RequiredArgsConstructor;
import org.ahk.qrclassscheduler.activity.model.Activity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final List<ValidationStrategy> validationStrategies;

    @Override
    public boolean validate(String userName, Activity activity) {
        for (ValidationStrategy vs : validationStrategies) {
            vs.validate(userName, activity);
        }
        return true;
    }
}
