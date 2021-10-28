package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserDto;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserDaoPostgresqlImpl extends AbstractDao implements UserDao {
    private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u WHERE u.login = ?;";
    private static final String SAVE_USER_QUERY = "INSERT INTO users (login, firstname, lastname, password) VALUES (?, ?, ?, ?)";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u WHERE u.login = ? AND u.password = ?;";
    private static final String FIND_ALL_USERS_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u;";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u WHERE id = ?;";

    public UserDaoPostgresqlImpl(final ConnectionPoolImpl connectionPool) {
        super(connectionPool);
    }

    @Override
    public UserDto saveUser(User user) throws DaoException {
        List<Object> parameters1 = Collections.singletonList(
                user.getLogin()
        );
        List<Object> parameters2 = Arrays.asList(
                user.getLogin(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword()
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        int affectedRows = 0;
        try {
            connection = getConnection(false);
            preparedStatement1 = getPreparedStatement(FIND_USER_BY_LOGIN_QUERY, connection, parameters1);
            preparedStatement2 = getPreparedStatement(SAVE_USER_QUERY, connection, parameters2);

            resultSet = preparedStatement1.executeQuery();
            if (!resultSet.next()) {
                affectedRows = preparedStatement2.executeUpdate();
            }
            connection.commit();

            return (affectedRows > 0) ? new UserDto(user) : null;
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }

    @Override
    public UserDto findUserByLoginAndPassword(User user) throws DaoException {
        List<Object> parameters = Arrays.asList(
                user.getLogin(),
                user.getPassword()
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            preparedStatement = getPreparedStatement(FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();

            UserDto userDto = null;
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String login = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                userDto = new UserDto(id, login, firstName, lastName);
            }

            return userDto;
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    @Override
    public List<UserDto> getUsers() {
        try (Connection connection = getConnection(true);
             PreparedStatement preparedStatement = getPreparedStatement(FIND_ALL_USERS_QUERY, connection, Collections.emptyList());
             ResultSet resultSet = preparedStatement.executeQuery();) {
            final List<UserDto> users = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String login = resultSet.getString(2);
                String fn = resultSet.getString(3);
                String ln = resultSet.getString(4);
                users.add(new UserDto(id, login, fn, ln));
            }
            return users;
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public UserDto getUserById(Long id) throws DaoException {
        List<Object> parameters = Arrays.asList(
                id
        );
        try (Connection connection = getConnection(true);
             PreparedStatement preparedStatement = getPreparedStatement(FIND_USER_BY_ID_QUERY, connection, parameters);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            UserDto userDto = null;
            while (resultSet.next()) {
                long foundId = resultSet.getLong(1);
                String login = resultSet.getString(2);
                String fn = resultSet.getString(3);
                String ln = resultSet.getString(4);
                userDto = new UserDto(foundId, login, fn, ln);
            }
            return userDto;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }
}
