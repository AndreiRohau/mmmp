package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.security.Salt;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;
import com.jwd.service.serviceLogic.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

import static com.jwd.controller.util.Constant.*;
import static java.util.Objects.nonNull;

public class LogInCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogInCommand.class.getName());

    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final ControllerValidator validator = new ControllerValidator();
    private final Salt salt = new Salt();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOGGER.info("LOG IN STARTS.");
        try {
            // prepare data
            final String login = request.getParameter(LOGIN);
            final char[] password = request.getParameter(PASSWORD).toCharArray();

            // validation
            validate(login, password);

            // business logic
            final User user = new User(login, passwordToHash(password));
            final UserDto userDto = userService.login(user);

            // send response
            HttpSession session;
            session = request.getSession(false);
            if (nonNull(session)) {
                session.invalidate();
            }
            session = request.getSession();
            session.setAttribute(ROLE, userDto.getId()); // todo role
            response.sendRedirect(Command.prepareUri(request));
        } catch (Exception e) {
            throw new ControllerException(e);
        }
    }

    private void validate(String login, char[] password) throws ControllerException {
        validator.isValidLogin(login);
        validator.isValidPassword(password);
    }

    private String passwordToHash(char[] password) {
        return salt.salt(password);
    }
}
