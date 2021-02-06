package com.berkay22demirel.couriertracking.cache;

import java.util.Collection;

public interface ICacheService<K, T> {

    Collection<T> getAll();

    T findByKey(K key);
}
