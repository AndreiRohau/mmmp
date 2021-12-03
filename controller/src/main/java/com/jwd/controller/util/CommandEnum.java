package com.jwd.controller.util;

public enum CommandEnum {
    DEFAULT("default"),
    LOGOUT("logout"),
    LOGIN("login"),
    REGISTRATION("registration"),
    CHANGE_LANGUAGE("change_language"),
    SHOW_PRODUCTS("show_products");

    private final String frontEndName;

    CommandEnum(final String frontEndName) {
        this.frontEndName = frontEndName;
    }

    public String getFrontEndName() {
        return frontEndName;
    }
}
