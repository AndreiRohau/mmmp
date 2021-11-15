package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

import static com.jwd.controller.util.Constant.HOME;

public class LogOutCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogOutCommand.class.getName());

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOGGER.info("LOG OUT STARTS.");
        try {
            request.getSession().invalidate();
            response.sendRedirect(HOME);
        } catch (Exception e) {
            throw new ControllerException(e);
        }
    }
}
