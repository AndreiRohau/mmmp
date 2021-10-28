package com.jwd.dao.repository.impl;

import com.jwd.dao.domain.UserRow;
import com.jwd.dao.domain.UserRowDto;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class UserDaoImpl implements UserDao {
    // stubbed database
    private List<UserRow> stubbedUserRows = new ArrayList<>();

    public UserDaoImpl() {
        initStubbedUsers();
    }

    private void initStubbedUsers() {
        stubbedUserRows.add(new UserRow(1L, "abra", "Andrei", "Rohau", "111"));
        stubbedUserRows.add(new UserRow(2L, "bara", "Valera", "Petrov", "222"));
        stubbedUserRows.add(new UserRow(3L, "cobra", "Serhei", "Skaryna", "333"));
    }

    @Override
    public List<UserRowDto> getUsers() {
        // validate parameters from higher layer
        final List<UserRow> userRows = stubbedUserRows; // execute query getting users from database
        final List<UserRowDto> userRowDtos = new ArrayList<>();
        for (final UserRow daoUserRowDto : userRows) {
            userRowDtos.add(new UserRowDto(daoUserRowDto));
        }
        return userRowDtos;
    }

    @Override
    public UserRowDto getUserById(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public UserRowDto saveUser(UserRow userRow) throws DaoException {
        if (isNull(userRow)) {
            throw new DaoException();
        }
        // validate parameters from higher layer
        // do not forget to generate user id if needed
        stubbedUserRows.add(userRow); // execute query saving user to database
        return new UserRowDto(userRow);
    }

    @Override
    public UserRowDto findUserByLoginAndPassword(UserRow userRow) throws DaoException {
        return null;
    }
}
