package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.security.Salt;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.jwd.controller.util.Constant.*;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class.getName());
    private UserService userService = new UserServiceImpl(); // todo refactor to singleton
    private ControllerValidator  validator = new ControllerValidator(); // todo refactor to singleton
    private Salt salt = new Salt(); // todo refactor to singleton

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOGGER.info("REGISTRATION STARTS.");
        try {
            // get params
            final String login = request.getParameter(LOGIN);
            final char[] password1 = request.getParameter(PASSWORD_1).toCharArray();
            final char[] password2 = request.getParameter(PASSWORD_2).toCharArray();

            // validate params
            validate(login, password1, password2);

            // process params
            final User user = new User(login, passwordToHash(password1));
            final UserDto userDto = userService.registerUser(user);

            // prepare response
            request.setAttribute(MESSAGE, "User registered successfully.");
            request.setAttribute(USER, userDto);

            //send response
            request.getRequestDispatcher(Command.prepareUri(request) + JSP).forward(request, response);
        } catch (ServiceException | ServletException | IOException e) {
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
