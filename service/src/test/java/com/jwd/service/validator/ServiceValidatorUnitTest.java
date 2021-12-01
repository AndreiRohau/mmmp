package com.jwd.service.validator;

import com.jwd.service.domain.Page;
import com.jwd.service.domain.Product;
import com.jwd.service.domain.User;
import com.jwd.service.exception.ServiceException;
import org.junit.Test;

public class ServiceValidatorUnitTest {

    private ServiceValidator validator = new ServiceValidator();

    @Test
    public void testIsValidString_valid() throws ServiceException {
        final String str = "test";
        final String subject = "message";

        validator.isValidString(str, subject);
    }

    @Test(expected = ServiceException.class)
    public void testIsValidString_invalid() throws ServiceException {
        final String str = "";
        final String subject = "message";

        validator.isValidString(str, subject);
    }

    @Test
    public void testValidate_valid() throws ServiceException {
        final Page<Product> productPageRequest = new Page<>();

        validator.validate(productPageRequest);
    }

    @Test(expected = ServiceException.class)
    public void testValidate_invalid() throws ServiceException {
        final Page<Product> productPageRequest = null;

        validator.validate(productPageRequest);
    }

    @Test(expected = ServiceException.class)
    public void testValidate_invalidUser_null() throws ServiceException {
        User user = null;

        validator.validate(user);
    }

    @Test(expected = ServiceException.class)
    public void testValidate_invalidUser_nullLogin() throws ServiceException {
        User user = new User(null, "password");

        validator.validate(user);
    }

    @Test(expected = ServiceException.class)
    public void testValidate_invalidUser_nullPassword() throws ServiceException {
        User user = new User("login", null);

        validator.validate(user);
    }

    @Test
    public void testValidate_validUser() throws ServiceException {
        User user = new User("login", "password");

        validator.validate(user);
    }
}