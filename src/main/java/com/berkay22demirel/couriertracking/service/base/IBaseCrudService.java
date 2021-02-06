package com.berkay22demirel.couriertracking.service.base;

import java.io.IOException;
import java.util.Collection;

public interface IBaseCrudService<T, ID> {

    public ID add(T entity) throws IOException, IllegalAccessException;

    public void update(T entity) throws IOException;

    public int delete(ID id) throws IOException;

    public T get(ID id) throws IOException;

    public Collection<T> getAll() throws IOException;

}
