package com.jwd.controller.util;

public enum CommandEnum {
    DEFAULT("default"),
    REGISTRATION("registration");

    private final String frontEndName;

    CommandEnum(final String frontEndName) {
        this.frontEndName = frontEndName;
    }

    public String getFrontEndName() {
        return frontEndName;
    }
}
