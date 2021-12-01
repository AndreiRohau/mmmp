package com.jwd.controller.command;

import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    void process(HttpServletRequest request, HttpServletResponse response) throws ControllerException;

    static String prepareUri(HttpServletRequest request) {
        String uri = request.getRequestURI().replace("/", "");
        if (uri.length() == 0) {
            uri = "home";
        }
        return uri;
    }
}
