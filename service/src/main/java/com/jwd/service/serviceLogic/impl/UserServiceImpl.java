package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.impl.UserDaoImpl;
import com.jwd.service.serviceLogic.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

}
