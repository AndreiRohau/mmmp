package com.jwd.service.serviceLogic;

import com.jwd.service.domain.Page;
import com.jwd.service.domain.Product;
import com.jwd.service.exception.ServiceException;

public interface ProductService {
    Page<Product> showProducts(Page<Product> productPageRequest) throws ServiceException;
}
