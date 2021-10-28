package com.jwd.service.serviceLogic;

import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;
import com.jwd.service.exception.ServiceException;

public interface UserService {
    /**
     *
     * @param user - to be saved in app, received from UI
     * @return ClientDto to display saved user
     */
    UserDto registerUser(final User user) throws ServiceException;

    UserDto login(User user) throws ServiceException;
}
