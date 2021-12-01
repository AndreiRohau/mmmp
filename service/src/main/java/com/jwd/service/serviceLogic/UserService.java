package com.jwd.service.serviceLogic;

import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;
import com.jwd.service.exception.ServiceException;

public interface UserService {
    UserDto registerUser(User user) throws ServiceException;

    UserDto login(User user) throws ServiceException;
}
