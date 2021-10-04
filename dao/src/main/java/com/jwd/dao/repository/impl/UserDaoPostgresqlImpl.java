package com.jwd.dao.repository.impl;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserDto;
import com.jwd.dao.repository.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserDaoPostgresqlImpl implements UserDao {
    private static final String FIND_ALL_USERS_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u;";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u WHERE id = ?;";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY = "SELECT u.id, u.login, u.firstname, u.lastname FROM users u WHERE login = ? AND password = ?;";
    private static final String SAVE_USER_QUERY = "INSERT INTO users (login, firstname, lastname, password) VALUES (?, ?, ?, ?)";
    private final DataBaseConfig dataBaseConfig;

    public UserDaoPostgresqlImpl() {
        dataBaseConfig = new DataBaseConfig();
    }

    @Override
    public List<UserDto> getUsers() {
        try (Connection connection = dataBaseConfig.getConnection();
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        List<Object> parameters = Arrays.asList(
                id
        );
        try (Connection connection = dataBaseConfig.getConnection();
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
            throw new RuntimeException();
        }
    }

    @Override
    public UserDto getUserByLoginAndPassword(User user) {
        List<Object> parameters = Arrays.asList(
                user.getLogin(),
                user.getPassword()
        );
        try (Connection connection = dataBaseConfig.getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY, connection, parameters);
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
            throw new RuntimeException();
        }
    }

    @Override
    public UserDto saveUser(User user) {
        List<Object> parameters = Arrays.asList(
                user.getLogin(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword()
        );
        try (Connection connection = dataBaseConfig.getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(SAVE_USER_QUERY, connection, parameters);) {

            int affectedRows = preparedStatement.executeUpdate();
            UserDto userDto = null;
            if (affectedRows > 0) {
                userDto = new UserDto(user);
            }
            return userDto;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private PreparedStatement getPreparedStatement(String query, Connection connection, final List<Object> parameters) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        setPreparedStatementParameters(preparedStatement, parameters);
        return preparedStatement;
    }

    private void setPreparedStatementParameters(PreparedStatement preparedStatement, List<Object> parameters) throws SQLException {
        for (int i = 0, queryParameterIndex = 1; i < parameters.size(); i++, queryParameterIndex++) {
            Object parameter = parameters.get(i);
            setPreparedStatementParameter(preparedStatement, queryParameterIndex, parameter);
        }
    }

    private void setPreparedStatementParameter(PreparedStatement preparedStatement, int queryParameterIndex, Object parameter) throws SQLException {
        if (Long.class == parameter.getClass()) {
            preparedStatement.setLong(queryParameterIndex, (Long) parameter);
        } else if (String.class == parameter.getClass()){
            preparedStatement.setString(queryParameterIndex, (String) parameter);
        }
    }
}
