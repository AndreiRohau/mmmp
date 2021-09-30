package com.jwd.service.serviceLogic;

import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto registerUser(User user);
}
