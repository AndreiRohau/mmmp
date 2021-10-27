package com.jwd.controller;

import com.jwd.service.domain.User;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class FrontController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FrontController.class.getName());

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = new UserServiceImpl();
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
        req.setAttribute("users", userService.getUsers());
        req.setAttribute("serverMessage", "{\"serverMessage\": \"OK\"}");
        String login = req.getParameter("login");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password1");
        req.setAttribute("users", userService.getUsers());
        User user = new User(login, password1, password2);
        try {
            userService.registerUser(user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        String uri = prepareUri(req);
        req.getRequestDispatcher(uri + ".jsp").forward(req, resp);
    }

    private String prepareUri(HttpServletRequest req) {
        String uri = req.getRequestURI().replace("/", "");
        if (uri.length() == 0) {
            uri = "home";
        }
        return uri;
    }


}
