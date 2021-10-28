package com.jwd.dao.repository;

import com.jwd.dao.domain.UserRow;
import com.jwd.dao.domain.UserRowDto;
import com.jwd.dao.exception.DaoException;

import java.util.List;

public interface UserDao {
    /**
     *
     * @param userRow - to be saved, received from UI
     * @return UserDto to display saved user
     */
    UserRowDto saveUser(UserRow userRow) throws DaoException;

    UserRowDto findUserByLoginAndPassword(UserRow userRow) throws DaoException;

    /**
     * returns all users in our app
     * @return List<UserDto>
     */
    List<UserRowDto> getUsers();

    UserRowDto getUserById(Long id) throws DaoException;
}
