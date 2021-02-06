package com.berkay22demirel.couriertracking.service;

import java.util.Collection;

public interface IBaseCrudService<T, ID> {

    public ID add(T entity);

    public void update(T entity);

    public int delete(ID id);

    public T get(ID id);

    public Collection<T> getAll();

}
