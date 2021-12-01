package com.jwd.dao;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.repository.ProductDao;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.impl.ProductDaoImpl;
import com.jwd.dao.repository.impl.UserDaoImpl;

public class DaoFactory {

    private static final DaoFactory INSTANCE = new DaoFactory();

    private final ConnectionPool connectionPool = new ConnectionPoolImpl(new DataBaseConfig());
    private final UserDao userDao = new UserDaoImpl(connectionPool);
    private final ProductDao productDao = new ProductDaoImpl(connectionPool);

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }
}
