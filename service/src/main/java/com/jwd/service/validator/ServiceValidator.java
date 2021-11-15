package com.jwd.service.validator;

import com.jwd.service.domain.User;
import com.jwd.service.exception.ServiceException;

import static com.jwd.service.util.Util.isNullOrEmpty;

public class ServiceValidator {

    public void validate(User user) throws ServiceException {
        isValidString(user.getLogin(), "Login");
        isValidString(user.getPassword(), "Password");
    }

    public void isValidString(final String string, final String subject) throws ServiceException {
        if (isNullOrEmpty(string)) {
            throw new ServiceException(subject + " is null or empty.");
        }
    }
}
