package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.ProductRow;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.ProductDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jwd.dao.util.Util.convertNullToEmpty;

public class ProductDaoImpl extends AbstractDao implements ProductDao {
    private static final String COUNT_ALL_FILTERED_SORTED =
            "SELECT count(p.id) FROM products p " +
                    "WHERE UPPER(p.type) LIKE CONCAT('%', UPPER(?), '%') " +
                    "AND UPPER(p.company) LIKE CONCAT('%', UPPER(?), '%') " +
                    "AND UPPER(p.name) LIKE CONCAT('%', UPPER(?), '%');";
    //    private static final String COUNT_ALL_FILTERED_SORTED = "SELECT count(p.id) FROM products p WHERE p.colm LIKE CONCAT('%', ?, '%')";";
    //private static final String FIND_PAGE_FILTERED_SORTED = "SELECT * FROM products p ORDER BY p.name ASC LIMIT 5 OFFSET 0";
    private static final String FIND_PAGE_FILTERED_SORTED =
            "SELECT * FROM products p " +
                    "WHERE UPPER(p.type) LIKE CONCAT('%%', UPPER(?), '%%') " +
                    "AND UPPER(p.company) LIKE CONCAT('%%', UPPER(?), '%%') " +
                    "AND UPPER(p.name) LIKE CONCAT('%%', UPPER(?), '%%')" +
                    "ORDER BY p.%s %s LIMIT ? OFFSET ?;";

    //private static final String FIND_PAGE_FILTERED_SORTED = "SELECT * FROM products p WHERE p.colm LIKE CONCAT('%', ?, '%')" ORDER BY p.name ASC LIMIT 5 OFFSET 0";
    public ProductDaoImpl(final ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public Pageable<ProductRow> findPageByFilter(Pageable<ProductRow> daoProductPageable) throws DaoException {
        final List<Object> parameters1 = Arrays.asList(
                convertNullToEmpty(daoProductPageable.getFilter().getType()).toUpperCase(),
                convertNullToEmpty(daoProductPageable.getFilter().getCompany()).toUpperCase(),
                convertNullToEmpty(daoProductPageable.getFilter().getName()).toUpperCase()
        );
        final List<Object> parameters2 = Arrays.asList(
                convertNullToEmpty(daoProductPageable.getFilter().getType()).toUpperCase(),
                convertNullToEmpty(daoProductPageable.getFilter().getCompany()).toUpperCase(),
                convertNullToEmpty(daoProductPageable.getFilter().getName()).toUpperCase(),
                daoProductPageable.getLimit(),
                prepareOffset(daoProductPageable.getPageNumber(), daoProductPageable.getLimit())
        );
        final String getPageQuery = setSortAndDirection(FIND_PAGE_FILTERED_SORTED, daoProductPageable.getSortBy(), daoProductPageable.getDirection());
        final Connection connection = getConnection(false);
        try (final PreparedStatement preparedStatementCountAllProducts =
                     getPreparedStatement(COUNT_ALL_FILTERED_SORTED, connection, parameters1);
             final PreparedStatement preparedStatementGetProductsPageList =
                     getPreparedStatement(getPageQuery, connection, parameters2);
             final ResultSet totalProducts = preparedStatementCountAllProducts.executeQuery();
             final ResultSet productsPageList = preparedStatementGetProductsPageList.executeQuery();) {
            connection.commit();
            return getProductRowPageable(daoProductPageable, totalProducts, productsPageList);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            retrieve(connection);
        }
    }

    private Pageable<ProductRow> getProductRowPageable(Pageable<ProductRow> daoProductPageable,
                                                       ResultSet totalProducts,
                                                       ResultSet productsPageList) throws SQLException {
        final Pageable<ProductRow> pageable = new Pageable<>();
        long totalElements = 0L;
        while (totalProducts.next()) {
            totalElements = totalProducts.getLong(1);
        }
        final List<ProductRow> rows = new ArrayList<>();
        while (productsPageList.next()) {
            long id = productsPageList.getLong(1);
            String type = productsPageList.getString(2);
            String company = productsPageList.getString(3);
            String name = productsPageList.getString(4);
            rows.add(new ProductRow(id, type, company, name));
        }
        pageable.setPageNumber(daoProductPageable.getPageNumber());
        pageable.setLimit(daoProductPageable.getLimit());
        pageable.setTotalElements(totalElements);
        pageable.setElements(rows);
        pageable.setFilter(daoProductPageable.getFilter());
        pageable.setSortBy(daoProductPageable.getSortBy());
        pageable.setDirection(daoProductPageable.getDirection());
        return pageable;
    }
}

