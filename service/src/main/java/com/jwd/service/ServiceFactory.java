package com.jwd.service;

import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.ProductServiceImpl;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final ProductService productService = new ProductServiceImpl();

    private ServiceFactory() {}

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
