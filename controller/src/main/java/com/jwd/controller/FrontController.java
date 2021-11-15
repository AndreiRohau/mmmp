package com.jwd.controller;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.impl.*;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.util.CommandEnum;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.jwd.controller.util.CommandEnum.LOGIN;
import static com.jwd.controller.util.CommandEnum.*;
import static com.jwd.controller.util.Constant.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class FrontController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FrontController.class.getName());

    private Map<CommandEnum, Command> commands;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initCommandsMap();
    }

    private void initCommandsMap() {
        if (isNull(commands)) {
            commands = new HashMap<>();
        }
        commands.put(DEFAULT, new DefaultCommand());
        commands.put(REGISTRATION, new RegistrationCommand());
        commands.put(LOGIN, new LogInCommand());
        commands.put(LOGOUT, new LogOutCommand());
        commands.put(SHOW_PRODUCTS, new ShowProductsCommand());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doExecute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doExecute(req, resp);
    }

    private void doExecute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Call to FrontController#doExecute()");
        try {
            final CommandEnum command = getCommand(req);
            commands.get(command).execute(req, resp);
        } catch (ControllerException e) {
            // recursion
            Throwable cause = getCause(e);
            req.setAttribute(ERROR_MESSAGE, "Exception: " + cause.getMessage());
            req.getRequestDispatcher(HOME + JSP).forward(req, resp);
            e.printStackTrace();
        }
    }

    private Throwable getCause(Throwable cause) {
        if (nonNull(cause.getCause())) {
            cause = getCause(cause.getCause()); // recursive call of same method inside of it
        }
        return cause;
    }

    private CommandEnum getCommand(final HttpServletRequest req) {
        String commandNameParam = req.getParameter(COMMAND);
        if (isNull(commandNameParam)) {
            commandNameParam = DEFAULT.getName();
        }
        return CommandEnum.valueOf(commandNameParam.toUpperCase());
    }
}
