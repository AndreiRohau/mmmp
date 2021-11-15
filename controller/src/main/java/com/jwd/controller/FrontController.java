package com.jwd.controller;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.impl.DefaultCommand;
import com.jwd.controller.command.impl.RegistrationCommand;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.util.CommandEnum;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.jwd.controller.util.CommandEnum.*;
import static com.jwd.controller.util.Constant.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class FrontController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FrontController.class.getName());
    private Map<CommandEnum, Command> commandMap;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initCommandMap();
    }

    private void initCommandMap() {
        if (isNull(commandMap)) {
            commandMap = new EnumMap<>(CommandEnum.class);
        }
        commandMap.put(DEFAULT, new DefaultCommand());
        commandMap.put(REGISTRATION, new RegistrationCommand());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Call to FrontController#doExecute()");
        try {
            final CommandEnum command = getCommand(req.getParameter(COMMAND));
            commandMap.get(command).process(req, resp);
        } catch (ControllerException e) {
            // recursion
            Throwable cause = getCause(e);
            req.setAttribute(ERROR_MESSAGE, "Exception: " + cause.getMessage());
            req.getRequestDispatcher(HOME + JSP).forward(req, resp);
            LOGGER.warning(cause.getMessage());
        }
    }

    private CommandEnum getCommand(String commandNameParam) {
        if (isNull(commandNameParam)) {
            commandNameParam = DEFAULT.getFrontEndName();
        }
        return CommandEnum.valueOf(commandNameParam.toUpperCase());
    }

    private Throwable getCause(Throwable cause) {
        if (nonNull(cause.getCause())) {
            cause = getCause(cause.getCause()); // recursive call of same method inside of it
        }
        return cause;
    }

}
