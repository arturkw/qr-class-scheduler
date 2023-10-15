package org.ahk.qrclassscheduler.validation.exception;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException() {
        super("User already registered.");
    }
}
