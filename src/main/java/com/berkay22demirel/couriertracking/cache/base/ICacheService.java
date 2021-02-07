package com.berkay22demirel.couriertracking.cache.base;

import java.io.IOException;
import java.util.Collection;

/**
 * This interface added to simulate the cache system.
 *
 * @param <K> Key class type
 * @param <T> Value class type
 */
public interface ICacheService<K, T> {

    Collection<T> getAll();

    T findByKey(K key);

    void invalidate() throws IOException;
}
