package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.security.Salt;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

import static com.jwd.controller.util.Constant.*;
import static java.util.Objects.nonNull;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class.getName());

    private final UserService userService = new UserServiceImpl();
    private final ControllerValidator validator = new ControllerValidator();
    private final Salt salt = new Salt();

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) throws ControllerException {
        LOGGER.info("REGISTRATION STARTS.");
        try {
            // prepare data
            final String login = request.getParameter(LOGIN);
            final char[] password1 = request.getParameter(PASSWORD_1).toCharArray();
            final char[] password2 = request.getParameter(PASSWORD_2).toCharArray();

            // validation
            validate(login, password1, password2);

            // business logic
            final User user = new User(login, passwordToHash(password1));
            final UserDto userDto = userService.registerUser(user);

            // preparation to response
            final String message = nonNull(userDto) ? "User registered successfully." : "User HAS NOT BEEN registered.";
            request.setAttribute(MESSAGE, message);
            request.setAttribute(USER, userDto);

            // send response
            request.getRequestDispatcher(prepareUri(request) + ".jsp").forward(request, response);
        } catch (Exception e) {
            throw new ControllerException(e);
        }
    }

    private void validate(final String login, final char[] password1, final char[] password2) throws ControllerException {
        validator.isValidLogin(login);
        validator.isValidPassword(password1, password2);
    }

    private String passwordToHash(char[] password) {
        return salt.salt(password);
    }
}
