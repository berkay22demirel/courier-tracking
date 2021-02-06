package com.berkay22demirel.couriertracking.service.base;

import java.util.Collection;

public interface IBaseCacheCrudService<K, V> {

    public K add(V entity);

    public void update(V entity);

    public int delete(K id);

    public V get(K id);

    public Collection<V> getAll();
}
