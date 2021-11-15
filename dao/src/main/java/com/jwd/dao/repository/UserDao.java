package com.jwd.dao.repository;

import com.jwd.dao.domain.UserRow;
import com.jwd.dao.domain.UserRowDto;
import com.jwd.dao.exception.DaoException;

public interface UserDao {
    /**
     *
     * @param userRow - to be saved, received from UI
     * @return UserRowDto to displayed
     */
    UserRowDto saveUser(UserRow userRow) throws DaoException;
}
