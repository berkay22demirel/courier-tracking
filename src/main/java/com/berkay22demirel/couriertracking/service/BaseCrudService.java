package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.dao.IDaoSupport;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public abstract class BaseCrudService<T, ID extends Serializable> implements IBaseCrudService<T, ID> {

    private IDaoSupport<T, ID> dao;

    public BaseCrudService(IDaoSupport<T, ID> dao) {
        this.dao = dao;
    }

    @Override
    public ID add(T entity) {
        try {
            return dao.persist(entity);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void update(T entity) {
        try {
            dao.update(entity);
        } catch (Exception e) {

        }
    }

    @Override
    public int delete(ID id) {
        try {
            return dao.delete(id);
        } catch (IOException e) {
            return 0;
        }
    }

    @Override
    public T get(ID id) {
        try {
            return dao.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<T> getAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            return null;
        }
    }
}
