package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.ProductRow;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.ProductDao;
import com.jwd.dao.repository.impl.ProductDaoPostgresqlImpl;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.Product;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.ProductService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao = new ProductDaoPostgresqlImpl(new ConnectionPoolImpl(new DataBaseConfig()));

    @Override
    public Page<Product> showProducts(Page<Product> productPageableRequest) throws ServiceException {
        try {
            // validation
            // ...

            // prepare data
            final Pageable<ProductRow> daoProductPageable = convertToPageableProduct(productPageableRequest);
            // process data
            final Pageable<ProductRow> productRowsPageable = productDao.findPageByFilter(daoProductPageable);

            // return
            return convertToServicePage(productRowsPageable);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    private Page<Product> convertToServicePage(Pageable<ProductRow> productRowsPageable) {
        Page<Product> page = new Page<>();
        page.setPageNumber(productRowsPageable.getPageNumber());
        page.setLimit(productRowsPageable.getLimit());
        page.setTotalElements(productRowsPageable.getTotalElements());
        page.setElements(convertToProducts(productRowsPageable.getElements()));
        page.setFilter(convertToProduct(productRowsPageable.getFilter()));
        page.setSortBy(productRowsPageable.getSortBy());
        page.setDirection(productRowsPageable.getDirection());
        return page;
    }

    private List<Product> convertToProducts(final List<ProductRow> rows) {
        List<Product> list = new ArrayList<>();
        for (ProductRow row : rows) {
            list.add(convertToProduct(row));
        }
        return list;
    }

    private Product convertToProduct(final ProductRow row) {
        Product product = null;
        if (nonNull(row)) {
            product = new Product();
            product.setId(row.getId());
            product.setType(row.getType());
            product.setCompany(row.getCompany());
            product.setName(row.getName());
        }
        return product;
    }

    private Pageable<ProductRow> convertToPageableProduct(Page<Product> pageableRequest) {
        final Pageable<ProductRow> pageable = new Pageable<>();
        pageable.setPageNumber(pageableRequest.getPageNumber());
        pageable.setLimit(pageableRequest.getLimit());
        pageable.setTotalElements(pageableRequest.getTotalElements());
        pageable.setElements(convertToProductRows(pageableRequest.getElements()));
        pageable.setFilter(convertToProductRow(pageableRequest.getFilter()));
        pageable.setSortBy(pageableRequest.getSortBy());
        pageable.setDirection(pageableRequest.getDirection());
        return pageable;
    }

    private List<ProductRow> convertToProductRows(final List<Product> elements) {
        final List<ProductRow> list = new ArrayList<>();
        for (Product product : elements) {
            list.add(convertToProductRow(product));
        }
        return list;
    }

    private ProductRow convertToProductRow(final Product product) {
        ProductRow productRow = null;
        if (nonNull(product)) {
            productRow = new ProductRow();
            productRow.setId(product.getId());
            productRow.setType(product.getType());
            productRow.setCompany(product.getCompany());
            productRow.setName(product.getName());
        }
        return productRow;
    }

}
