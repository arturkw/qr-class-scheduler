package org.ahk.qrclassscheduler.validation.exception;

public class ActivityStartedException extends RuntimeException {
    public ActivityStartedException() {
        super("Course activity already started.");
    }
}
