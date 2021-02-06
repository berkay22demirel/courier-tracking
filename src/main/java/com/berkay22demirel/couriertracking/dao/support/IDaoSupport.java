package com.berkay22demirel.couriertracking.dao.support;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public interface IDaoSupport<T, ID extends Serializable> {

    ID persist(T entity) throws IOException, IllegalAccessException;

    void update(T entity) throws IOException;

    int delete(ID id) throws IOException;

    T get(ID id) throws IOException;

    Collection<T> findAll() throws IOException;
}
