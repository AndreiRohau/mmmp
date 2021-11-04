package com.jwd.dao.repository;

import com.jwd.dao.domain.UserRow;
import com.jwd.dao.domain.UserRowDto;
import com.jwd.dao.exception.DaoException;

import java.util.List;

public interface UserDao {
    /**
     * returns all users in our app
     * @return List<UserDto>
     */
    List<UserRowDto> getUsers();
    UserRowDto getUserById(Long id);
    UserRowDto getUserByLoginAndPassword(UserRow userRow);


    /**
     *
     * @param userRow - to be saved, received from UI
     * @return UserDto to display saved user
     */
    UserRowDto saveUser(UserRow userRow) throws DaoException;
}
