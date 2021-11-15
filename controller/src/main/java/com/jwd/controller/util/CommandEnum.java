package com.jwd.controller.util;

public enum CommandEnum {
    DEFAULT("default"),
    REGISTRATION("registration"),
    LOGIN("login"),
    LOGOUT("logout"),
    CHANGE_LOCATION("change_location"),
    SHOW_PRODUCTS("show_products");

    private String name;

    CommandEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
