package com.jwd.controller.exception;

public class ControllerException extends Exception {
    public ControllerException(Exception e) {
        super(e);
    }

    public ControllerException(String message) {
        super(message);
    }
}
