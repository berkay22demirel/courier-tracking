package com.berkay22demirel.couriertracking.dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public interface IJsonDaoSupport<T, ID extends Serializable> {

    ID persist(T entity) throws IOException, IllegalAccessException;

    void update(T entity) throws IOException;

    int delete(T entity) throws IOException;

    T get(ID id) throws IOException;

    Collection<T> findAll() throws IOException;
}
