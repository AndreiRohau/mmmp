package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.jwd.controller.util.Constant.HOME;
import static com.jwd.controller.util.Constant.LOCALE;

public class ChangeLanguageCommand implements Command {
    private static final java.util.logging.Logger LOGGER = Logger.getLogger(LogInCommand.class.getName());

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOGGER.info("Processing ChangeLanguageCommand");
        try {
            request.getSession(true).setAttribute(LOCALE, request.getParameter(LOCALE));
            response.sendRedirect(HOME);
        } catch (IOException e) {
            throw new ControllerException(e);
        }
    }
}
