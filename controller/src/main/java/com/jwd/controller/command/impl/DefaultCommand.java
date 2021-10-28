package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class DefaultCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DefaultCommand.class.getName());
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOGGER.info("DEFAULT STARTS.");
        try {
            response.sendRedirect(prepareUri(request) + ".jsp");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException(e);
        }
    }
}
