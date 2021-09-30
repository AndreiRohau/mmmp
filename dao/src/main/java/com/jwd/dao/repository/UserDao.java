package com.jwd.dao.repository;

import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserDto;

import java.util.List;

public interface UserDao {
    List<UserDto> getUsers();
    UserDto saveUser(User user);
}
