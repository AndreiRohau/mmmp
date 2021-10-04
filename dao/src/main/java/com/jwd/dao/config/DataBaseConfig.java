package com.jwd.dao.config;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConfig {

    private static String DRIVER = "org.postgresql.Driver";
    private static String URL = "jdbc:postgresql://localhost/";
    private static String DATABASE_NAME = "mytestdb";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "root";
    private boolean driverIsLoaded = false;

    public DataBaseConfig() {
    }

    public Connection getConnection() throws SQLException {
        loadJdbcDriver();
        Connection connection;
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        connection = DriverManager.getConnection(URL + DATABASE_NAME, properties);
        return connection;
    }

    private void loadJdbcDriver() {
        if (!driverIsLoaded) {
            try {
                // Class.forName(DRIVER);
                DriverManager.registerDriver(new Driver());
                driverIsLoaded = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
/*
    public void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (nonNull(resultSet)) {
                resultSet.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (nonNull(preparedStatement)) {
                preparedStatement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (nonNull(connection)) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
*/
}
