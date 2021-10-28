package com.jwd.dao.repository.impl;

import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserDto;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class UserDaoImpl implements UserDao {
    // stubbed database
    private List<User> stubbedUsers = new ArrayList<>();

    public UserDaoImpl() {
        initStubbedUsers();
    }

    private void initStubbedUsers() {
        stubbedUsers.add(new User(1L, "abra", "Andrei", "Rohau", "111"));
        stubbedUsers.add(new User(2L, "bara", "Valera", "Petrov", "222"));
        stubbedUsers.add(new User(3L, "cobra", "Serhei", "Skaryna", "333"));
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
    public UserDto getUserById(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public UserDto saveUser(User user) throws DaoException {
        if (isNull(user)) {
            throw new DaoException();
        }
        // validate parameters from higher layer
        // do not forget to generate user id if needed
        stubbedUsers.add(user); // execute query saving user to database
        return new UserDto(user);
    }

    @Override
    public UserDto findUserByLoginAndPassword(User user) throws DaoException {
        return null;
    }
}
