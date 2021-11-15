package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.domain.UserRow;
import com.jwd.dao.domain.UserRowDto;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.impl.UserDaoPostgresqlImpl;
import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.validator.ServiceValidator;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoPostgresqlImpl(new ConnectionPoolImpl(new DataBaseConfig()));
    private final ServiceValidator validator = new ServiceValidator();

    @Override
    public UserDto registerUser(final User user) throws ServiceException {
        try {
            // validation
            validator.validate(user);

            // prepare data
            final UserRow daoUser = convertServiceUserToDaoUser(user);
            // process data
            UserRowDto userDaoDto = userDao.saveUser(daoUser);

            // return
            return new UserDto(userDaoDto);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDto login(User user) throws ServiceException {
        try {
            // validation
            validator.validate(user);

            // prepare data
            final UserRow daoUser = convertServiceUserToDaoUser(user);
            // process data
            UserRowDto userDaoDto = userDao.findUserByLoginAndPassword(daoUser);

            // return
            return new UserDto(userDaoDto);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    private UserRow convertServiceUserToDaoUser(User user) {
        final UserRow daoUser = new UserRow();
        daoUser.setId(user.getId());
        daoUser.setLogin(user.getLogin());
        daoUser.setFirstName(user.getFirstName());
        daoUser.setLastName(user.getLastName());
        daoUser.setPassword(user.getPassword());
        return daoUser;
    }
}
