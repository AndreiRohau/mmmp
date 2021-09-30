package com.jwd.dao.repository.impl;

import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserDto;
import com.jwd.dao.repository.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    // stubbed database
    private List<User> stubbedUsers = new ArrayList<>();

    public UserDaoImpl() {
        initStubbedUsers();
    }

    private void initStubbedUsers() {
        stubbedUsers.add(new User(1L, "abra", "Andrei", "Rohau", "111"));
        stubbedUsers.add(new User(1L, "bara", "Valera", "Petrov", "222"));
        stubbedUsers.add(new User(1L, "cobra", "Serhei", "Skaryna", "333"));
    }

    @Override
    public List<UserDto> getUsers() {
        // validate parameters from higher layer
        final List<User> users = stubbedUsers; // execute query getting users from database
        final List<UserDto> userDtos = new ArrayList<>();
        for (final User daoUserDto : users) {
            userDtos.add(new UserDto(daoUserDto));
        }
        return userDtos;
    }

    @Override
    public UserDto saveUser(User user) {
        // validate parameters from higher layer
        // do not forget to generate user id if needed
        stubbedUsers.add(user); // execute query saving user to database
        return new UserDto(user);
    }
}
