package com.berkay22demirel.couriertracking.service.base;

import java.io.IOException;
import java.util.Collection;

public interface IBaseCacheCrudService<K, V> {

    public K add(V entity) throws IOException, IllegalAccessException;

    public void update(V entity) throws IOException;

    public int delete(K id) throws IOException;

    public V get(K id) throws IOException;

    public Collection<V> getAll() throws IOException;
}
