package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.impl.UserDaoImpl;
import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;
import com.jwd.service.serviceLogic.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public List<UserDto> getUsers() {
        // validate parameters from higher layer
        final List<com.jwd.dao.domain.UserDto> users = userDao.getUsers();
        final List<UserDto> userDtos = new ArrayList<>();
        for (final com.jwd.dao.domain.UserDto daoUserDto : users) {
            userDtos.add(new UserDto(daoUserDto));
        }
        return userDtos;
    }

    @Override
    public UserDto registerUser(User user) {
        // validate parameters from higher layer
        final com.jwd.dao.domain.User daoUser = new com.jwd.dao.domain.User();
        daoUser.setId(user.getId());
        daoUser.setLogin(user.getLogin());
        daoUser.setFirstName(user.getFirstName());
        daoUser.setLastName(user.getLastName());
        daoUser.setPassword(user.getLastName());
        com.jwd.dao.domain.UserDto userDaoDto = userDao.saveUser(daoUser);
        final UserDto userDto = new UserDto(userDaoDto);
        return userDto;
    }
}
