package com.jwd.service.validator;

import com.jwd.service.domain.Client;
import com.jwd.service.exception.ServiceException;

import static com.jwd.service.util.Util.isNullOrEmpty;

public class ServiceValidator {

    public void validate(Client client) throws ServiceException {
        isValidString(client.getLogin(), "Login");
        isValidString(client.getPassword(), "Password");
    }

    public void isValidString(final String string, final String subject) throws ServiceException {
        if (isNullOrEmpty(string)) {
            throw new ServiceException("Exception: " + subject + " is null or empty.");
        }
    }
}
