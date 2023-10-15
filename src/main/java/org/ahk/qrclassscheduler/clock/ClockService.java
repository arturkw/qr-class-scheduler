package org.ahk.qrclassscheduler.clock;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ClockService {
    public LocalDateTime localDateTimeNow() {
        return LocalDateTime.now();
    }

    public LocalDate localDateNow() {
        return LocalDate.now();
    }

    public LocalTime localTimeNow() {
        return LocalTime.now();
    }
}


