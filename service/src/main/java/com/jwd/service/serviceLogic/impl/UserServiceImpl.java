package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.domain.UserRow;
import com.jwd.dao.domain.UserRowDto;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.impl.UserDaoImpl;
import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.validator.ServiceValidator;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
    private final ServiceValidator validator = new ServiceValidator();

    @Override
    public UserDto registerUser(User user) throws ServiceException {
        try {
            // validation
            validator.validate(user);

            // prepare data
            final UserRow userRow = convertServiceUserToDaoUser(user);
            // process data
            UserRowDto userDaoDto = userDao.saveUser(userRow);

            // return
            return new UserDto(userDaoDto);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    private UserRow convertServiceUserToDaoUser(User user) {
        final UserRow userRow = new UserRow();
        userRow.setId(user.getId());
        userRow.setLogin(user.getLogin());
        userRow.setFirstName(user.getFirstName());
        userRow.setLastName(user.getLastName());
        userRow.setPassword(user.getPassword());
        return userRow;
    }


}
