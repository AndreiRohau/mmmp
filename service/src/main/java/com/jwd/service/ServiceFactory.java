package com.jwd.service;

import com.jwd.dao.DaoFactory;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.ProductServiceImpl;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory(DaoFactory.getInstance());

    private final UserService userService;
    private final ProductService productService;

    private ServiceFactory(final DaoFactory daoFactory) {
        userService = new UserServiceImpl();
        productService = new ProductServiceImpl(daoFactory.getProductDao());
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public ProductService getProductService() {
        return productService;
    }
}
