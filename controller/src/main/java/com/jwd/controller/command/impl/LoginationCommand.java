package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.domain.User;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginationCommand implements Command {
    private UserService userService = new UserServiceImpl();
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        try {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);

            Long userId = userService.login(user);

            request.setAttribute("message", "User is " + (userId == null ? "not" : "") + " found");
            if (userId != null) {
                request.setAttribute("userId", userId);
            }

            request.getRequestDispatcher("home.jsp").forward(request, response);

        } catch (ServiceException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
