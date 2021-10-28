package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.security.Salt;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.service.domain.Client;
import com.jwd.service.domain.ClientDto;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

import static com.jwd.controller.util.Constant.*;
import static java.util.Objects.nonNull;

public class LogInCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogInCommand.class.getName());

    private final UserService userService = new UserServiceImpl();
    private final ControllerValidator validator = new ControllerValidator();
    private final Salt salt = new Salt();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOGGER.info("LOG IN STARTS.");
        try {
            // prepare data
            final String login = request.getParameter(LOGIN);
            final char[] password = request.getParameter(PASSWORD).toCharArray();

            // validation
            validate(login, password);

            // business logic
            final Client client = new Client(login, passwordToHash(password));
            final ClientDto clientDto = userService.login(client);

            // send response
            if (nonNull(clientDto)) {
                final HttpSession session = request.getSession(true);
                session.setAttribute(ROLE, clientDto.getId()); // todo role
                response.sendRedirect(prepareUri(request) + ".jsp");
            } else {
                request.setAttribute(MESSAGE, "No such Client.");
                request.getRequestDispatcher("home.jsp").forward(request, response);
            }
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
