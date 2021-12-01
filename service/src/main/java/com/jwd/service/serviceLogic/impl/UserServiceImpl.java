package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.DaoFactory;
import com.jwd.dao.domain.UserRow;
import com.jwd.dao.domain.UserRowDto;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.service.domain.User;
import com.jwd.service.domain.UserDto;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.validator.ServiceValidator;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private final ServiceValidator validator = new ServiceValidator();

    @Override
    public UserDto registerUser(User user) throws ServiceException {
        try {
            // validation
            validator.validate(user);

            // prepare data
            final UserRow userRow = convertServiceUserToUserRow(user);
            // process data
            UserRowDto userRowDto = userDao.saveUser(userRow);

            // return
            return new UserDto(userRowDto);
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
            final UserRow userRow = convertServiceUserToUserRow(user);
            // process data
            UserRowDto userRowDto = userDao.findUserByLoginAndPassword(userRow);

            // return
            return new UserDto(userRowDto);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    private UserRow convertServiceUserToUserRow(User user) {
        final UserRow userRow = new UserRow();
        userRow.setId(user.getId());
        userRow.setLogin(user.getLogin());
        userRow.setFirstName(user.getFirstName());
        userRow.setLastName(user.getLastName());
        userRow.setPassword(user.getPassword());
        return userRow;
    }


}
