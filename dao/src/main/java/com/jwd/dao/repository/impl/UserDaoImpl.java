package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.domain.UserRow;
import com.jwd.dao.domain.UserRowDto;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.jwd.dao.util.Util.convertNullToEmpty;
import static java.util.Objects.isNull;

public class UserDaoImpl extends AbstractDao implements UserDao {

    private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u WHERE u.login = ?;";
    private static final String SAVE_USER_QUERY = "INSERT INTO users (login, firstname, lastname, password) VALUES (?, ?, ?, ?)";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u WHERE u.login = ? AND u.password = ?;";
    private static final String FIND_ALL_USERS_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u;";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u WHERE id = ?;";

    public UserDaoImpl(final ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public UserRowDto saveUser(UserRow userRow) throws DaoException {
        final List<Object> parameters1 = Collections.singletonList(
                convertNullToEmpty(userRow.getLogin())
        );
        final List<Object> parameters2 = Arrays.asList(
                convertNullToEmpty(userRow.getLogin()),
                convertNullToEmpty(userRow.getFirstName()),
                convertNullToEmpty(userRow.getLastName()),
                convertNullToEmpty(userRow.getPassword())
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

            // todo check isolation level
            resultSet = preparedStatement1.executeQuery();
            if (!resultSet.next()) {
                affectedRows = preparedStatement2.executeUpdate();
            }
            connection.commit();

            processAbnormalCase(affectedRows < 1, "User HAS NOT BEEN registered. Such login exists.");
            return new UserRowDto(userRow);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }

    @Override
    public UserRowDto findUserByLoginAndPassword(UserRow userRow) throws DaoException {
        List<Object> parameters = Arrays.asList(
                userRow.getLogin(),
                userRow.getPassword()
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            preparedStatement = getPreparedStatement(FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();

            UserRowDto userRowDto = null;
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String login = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                userRowDto = new UserRowDto(id, login, firstName, lastName);
            }
            processAbnormalCase(isNull(userRowDto), "No such User.");
            return userRowDto;
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }
}
