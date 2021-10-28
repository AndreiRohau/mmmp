package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.Product;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.serviceLogic.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

import static com.jwd.controller.util.Constant.*;
import static com.jwd.controller.util.Util.isNullOrEmpty;

public class ShowProductsCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ShowProductsCommand.class.getName());

    private final ProductService productService = new ProductServiceImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOGGER.info("SHOW PRODUCTS STARTS.");
        try {
            // prepare data
            String currentPageParam = request.getParameter(CURRENT_PAGE);
            if (isNullOrEmpty(currentPageParam)) {
                currentPageParam = "1";
            }
            String currentLimitParam = request.getParameter(PAGE_LIMIT);
            if (isNullOrEmpty(currentLimitParam)) {
                currentLimitParam = "5";
            }
            int currentPage = Integer.parseInt(currentPageParam);
            int pageLimit = Integer.parseInt(currentLimitParam);
            final Page<Product> pageableRequest = new Page<>();
            pageableRequest.setPageNumber(currentPage);
            pageableRequest.setLimit(pageLimit);

            // validation
            // ...

            // business logic
            final Page<Product> pageable = productService.showProducts(pageableRequest);

            // send response
            request.setAttribute(PAGEABLE, pageable);
            request.getRequestDispatcher(prepareUri(request) + ".jsp").forward(request, response);
        } catch (Exception e) {
            throw new ControllerException(e);
        }
    }
}
