package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.DaoFactory;
import com.jwd.dao.domain.ProductRow;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.ProductDao;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.Product;
import com.jwd.service.exception.ServiceException;
import org.junit.Test;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceImplTest {

    private ProductDao productDao = DaoFactory.getInstance().getProductDao();
    // testing class
    private ProductServiceImpl productService = new ProductServiceImpl(productDao);

    //region parameters
    private int pageNumber = 1;
    private long totalElements = 18;
    private int limit = 3;
    private List<Product> emptyElements = new ArrayList<>();
    private List<Product> elements = Arrays.asList(
            new Product(17L, "PC", "hp", "elite-book"),
            new Product(18L, "PC", "hp", "free"),
            new Product(4L, "PC", "DELL", "future-inspiron")
    );
    private List<ProductRow> emptyElementRows = new ArrayList<>();
    private Product filter = new Product();
    private ProductRow filterRow = new ProductRow();
    private String sortBy = "name";
    private String direction = "ASC";
    //endregion

    @Test(expected = ServiceException.class)
    public void testShowProducts_validationExceptionNullParameter() throws ServiceException {
        Page<Product> productPageRequest = null;

        productService.showProducts(productPageRequest);
    }

    /*
    1. productService.addNewProduct() write tests
    2. in testShowProducts() - run productService.addNewProduct(), adding new products
    3. in testShowProducts() - productService.showProducts(productPageRequest), pageNumber=1, limit==totalElements
    4. in testShowProducts() - assert that NewProducts are among Page<Product> actual result
     */
    @Test
    public void testShowProducts_positive() throws ServiceException {
        final Page<Product> productPageRequest = new Page<>(pageNumber, totalElements, limit, emptyElements, filter, sortBy, direction);
        final Page<Product> expectedProductPageRequest = new Page<>(pageNumber, totalElements, limit, elements, filter, sortBy, direction);

        final Page<Product> actualProductPage = productService.showProducts(productPageRequest);
        // 1. add new_products
        // 2. showProducts() -> actualSecondResult
        // 3. assert THAT actualSecondResult-elements minus actualProductPage-elements ARE new_products that you have saved before

        assertEquals(expectedProductPageRequest, actualProductPage);
    }

    @Test
    public void testShowProducts_daoException() throws DaoException {
        final Page<Product> productPageRequest = new Page<>(-123, totalElements, limit, emptyElements, filter, sortBy, direction);
        final PSQLException psqlException = new PSQLException("ERROR: OFFSET must not be negative", PSQLState.UNKNOWN_STATE);

        ServiceException actual = null;
        try {
            productService.showProducts(productPageRequest);
        } catch (ServiceException e) {
            actual = e;
        }

        // assert
        assertEquals((new ServiceException(new DaoException(psqlException))).getMessage(), actual.getMessage());
    }
}