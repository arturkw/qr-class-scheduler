package org.ahk.qrclassscheduler.validation.exception;

public class SeatsNotAvailableException extends RuntimeException {
    public SeatsNotAvailableException() {
        super("All seats are occupied.");
    }
}
