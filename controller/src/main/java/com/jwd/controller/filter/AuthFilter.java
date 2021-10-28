package com.jwd.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.jwd.controller.util.CommandEnum.*;
import static com.jwd.controller.util.Constant.COMMAND;
import static com.jwd.controller.util.Constant.ROLE;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        final List<String> alwaysAvailableCommands = Arrays.asList(
                DEFAULT.getName(),
                CHANGE_LOCATION.getName(),
                REGISTRATION.getName(),
                LOGIN.getName(),
                LOGOUT.getName()
        );
        if (alwaysAvailableCommands.contains(req.getParameter(COMMAND)) ||
                req.getSession().getAttribute(ROLE) != null) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect("home.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}
