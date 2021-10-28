package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserDto;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.impl.UserDaoPostgresqlImpl;
import com.jwd.service.domain.Client;
import com.jwd.service.domain.ClientDto;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.validator.ServiceValidator;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoPostgresqlImpl(new ConnectionPoolImpl(new DataBaseConfig()));
    private final ServiceValidator validator = new ServiceValidator();

    @Override
    public ClientDto registerUser(final Client user) throws ServiceException {
        try {
            // validation
            validator.validate(user);

            // prepare data
            final User daoUser = convertServiceUserToDaoUser(user);
            // process data
            UserDto userDaoDto = userDao.saveUser(daoUser);

            // return
            return isNull(userDaoDto) ? null : new ClientDto(userDaoDto);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ClientDto login(Client user) throws ServiceException {
        try {
            // validation
            validator.validate(user);

            // prepare data
            final User daoUser = convertServiceUserToDaoUser(user);
            // process data
            UserDto userDaoDto = userDao.findUserByLoginAndPassword(daoUser);

            // return
            return isNull(userDaoDto) ? null : new ClientDto(userDaoDto);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ClientDto> getClients() {
        final List<UserDto> users = userDao.getUsers();
        final List<ClientDto> clients = new ArrayList<>();
        for (final UserDto daoUserDto : users) {
            clients.add(new ClientDto(daoUserDto));
        }
        return clients;
    }

    private User convertServiceUserToDaoUser(Client client) {
        final User daoUser = new User();
        daoUser.setId(client.getId());
        daoUser.setLogin(client.getLogin());
        daoUser.setFirstName(client.getFirstName());
        daoUser.setLastName(client.getLastName());
        daoUser.setPassword(client.getPassword());
        return daoUser;
    }
}
