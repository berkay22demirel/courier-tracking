package com.berkay22demirel.couriertracking.service.base;

import com.berkay22demirel.couriertracking.aop.annotation.Loggable;
import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public abstract class BaseCrudService<T, ID extends Serializable> implements IBaseCrudService<T, ID> {

    private IDaoSupport<T, ID> dao;

    public BaseCrudService(IDaoSupport<T, ID> dao) {
        this.dao = dao;
    }

    @Loggable
    @Override
    public ID add(T entity) throws IOException, IllegalAccessException {
        return dao.persist(entity);
    }

    @Loggable
    @Override
    public void update(T entity) throws IOException {
        dao.update(entity);
    }

    @Loggable
    @Override
    public int delete(ID id) throws IOException {
        return dao.delete(id);
    }

    @Loggable
    @Override
    public T get(ID id) throws IOException {
        return dao.get(id);
    }
    
    @Loggable
    @Override
    public Collection<T> getAll() throws IOException {
        return dao.findAll();
    }
}
