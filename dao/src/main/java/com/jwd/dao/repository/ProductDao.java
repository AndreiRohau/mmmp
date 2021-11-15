package com.jwd.dao.repository;

import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.ProductRow;
import com.jwd.dao.exception.DaoException;

public interface ProductDao {
    Pageable<ProductRow> findPageByFilter(Pageable<ProductRow> daoProductPageable) throws DaoException;
}
