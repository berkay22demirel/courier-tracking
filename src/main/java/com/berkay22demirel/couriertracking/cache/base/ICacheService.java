package com.berkay22demirel.couriertracking.cache.base;

import java.io.IOException;
import java.util.Collection;

public interface ICacheService<K, T> {

    Collection<T> getAll();

    T findByKey(K key);

    void invalidate() throws IOException;
}
