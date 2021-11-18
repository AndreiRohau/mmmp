package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.ProductRow;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.ProductDao;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.Product;
import com.jwd.service.exception.ServiceException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceImplUnitTest {

    // mock
    private ProductDao productDao = Mockito.mock(ProductDao.class);
    // testing class
    private ProductServiceImpl productService = new ProductServiceImpl(productDao);

    // captors
    private ArgumentCaptor<Pageable<ProductRow>> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

    // parameters
    private int pageNumber = 1;
    private long totalElements = 10;
    private int limit = 3;
    private List<Product> emptyElements = new ArrayList<>();
    private List<Product> elements = Arrays.asList(
            new Product(1L, "type1", "company1", "name1"),
            new Product(2L, "type2", "company2", "name2"),
            new Product(3L, "type3", "company3", "name3")
    );
    private List<ProductRow> emptyElementRows = new ArrayList<>();
    private List<ProductRow> elementRows = Arrays.asList(
            new ProductRow(1L, "type1", "company1", "name1"),
            new ProductRow(2L, "type2", "company2", "name2"),
            new ProductRow(3L, "type3", "company3", "name3")
    );
    private Product filter = new Product();
    private ProductRow filterRow = new ProductRow();
    private String sortBy = "name";
    private String direction = "ASC";

    @Test(expected = ServiceException.class)
    public void testShowProducts_validationExceptionNullParameter() throws ServiceException {
        Page<Product> productPageRequest = null;

        productService.showProducts(productPageRequest);
    }

    @Test
    public void testShowProducts_positive() throws ServiceException, DaoException {
        final Page<Product> productPageRequest = new Page<>(pageNumber, totalElements, limit, emptyElements, filter, sortBy, direction);
        final Pageable<ProductRow> daoProductPageable = new Pageable<>(pageNumber, totalElements, limit, emptyElementRows, filterRow, sortBy, direction);
        final Pageable<ProductRow> productRowsPageable = new Pageable<>(pageNumber, totalElements, limit, elementRows, filterRow, sortBy, direction);
        final Page<Product> expectedProductPageRequest = new Page<>(pageNumber, totalElements, limit, elements, filter, sortBy, direction);

        when(productDao.findPageByFilter(daoProductPageable)).thenReturn(productRowsPageable);

        final Page<Product> actualProductPage = productService.showProducts(productPageRequest);

        verify(productDao, times(1)).findPageByFilter(pageableArgumentCaptor.capture());
        assertEquals(daoProductPageable, pageableArgumentCaptor.getValue());
        assertEquals(expectedProductPageRequest, actualProductPage);
    }

    @Test
    public void testShowProducts_daoException() throws DaoException {
        final Page<Product> productPageRequest = new Page<>(pageNumber, totalElements, limit, emptyElements, filter, sortBy, direction);
        final Pageable<ProductRow> daoProductPageable = new Pageable<>(pageNumber, totalElements, limit, emptyElementRows, filterRow, sortBy, direction);
        final DaoException daoException = new DaoException("any stubbed message");

        when(productDao.findPageByFilter(daoProductPageable)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            productService.showProducts(productPageRequest);
        } catch (ServiceException e) {
            actual = e;
        }

        // assert
        verify(productDao, times(1)).findPageByFilter(pageableArgumentCaptor.capture());
        assertEquals(daoProductPageable, pageableArgumentCaptor.getValue());
        assertEquals((new ServiceException(daoException)).getMessage(), actual.getMessage());
    }
}