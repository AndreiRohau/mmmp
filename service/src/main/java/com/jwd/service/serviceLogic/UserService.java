package com.jwd.service.serviceLogic;

import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;
import com.jwd.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    /**
     *
     * @param user - to be saved in app, received from UI
     * @return ClientDto to display saved user
     */
    UserDto registerUser(final User user) throws ServiceException;

    UserDto login(User user) throws ServiceException;

    /**
     * returns all users in our app
     * @return List<ClientDto>
     */
    List<UserDto> getUsers() throws ServiceException;
}
