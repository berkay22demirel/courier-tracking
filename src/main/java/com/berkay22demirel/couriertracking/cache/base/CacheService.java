package com.berkay22demirel.couriertracking.cache.base;

import com.berkay22demirel.couriertracking.aop.annotation.Loggable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public abstract class CacheService<K extends Serializable, V> implements ICacheService<K, V> {

    private Map<K, V> values;

    public CacheService(Map<K, V> values) {
        this.values = values;
    }

    @Loggable
    @Override
    public Collection<V> getAll() {
        return values.values();
    }

    @Loggable
    @Override
    public V findByKey(K key) {
        return values.get(key);
    }
}
