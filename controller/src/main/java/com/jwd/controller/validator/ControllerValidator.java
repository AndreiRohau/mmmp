package com.jwd.controller.validator;

import com.jwd.controller.exception.ControllerException;

import java.util.Arrays;

import static com.jwd.controller.util.Util.isNullOrEmpty;

public class ControllerValidator  {

    public void isValidLogin(final String login) throws ControllerException {
        if (isNullOrEmpty(login)) {
            throw new ControllerException("Login is null or empty.");
        }
    }

    public void isValidPassword(final char[] password) throws ControllerException {
        if (isNullOrEmpty(password)) {
            throw new ControllerException("Password is null or empty.");
        }
    }

    public void isValidPassword(final char[] password1, final char[] password2) throws ControllerException {
        if (isNullOrEmpty(password1)) {
            throw new ControllerException("Password is null or empty.");
        } else if (!Arrays.equals(password1, password2)) {
            throw new ControllerException("Passwords are not equal.");
        }
    }
}
