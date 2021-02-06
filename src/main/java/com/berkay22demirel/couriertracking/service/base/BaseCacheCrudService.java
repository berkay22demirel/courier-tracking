package com.berkay22demirel.couriertracking.service.base;

import com.berkay22demirel.couriertracking.aop.annotation.Loggable;
import com.berkay22demirel.couriertracking.cache.base.ICacheService;
import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public abstract class BaseCacheCrudService<K extends Serializable, V> implements IBaseCacheCrudService<K, V> {

    private ICacheService<K, V> cacheService;
    private IDaoSupport<V, K> dao;
    
    public BaseCacheCrudService(ICacheService<K, V> cacheService, IDaoSupport<V, K> dao) {
        this.cacheService = cacheService;
        this.dao = dao;
    }

    @Loggable
    @Override
    public K add(V value) throws IOException, IllegalAccessException {
        K key = dao.persist(value);
        cacheService.invalidate();
        return key;
    }

    @Loggable
    @Override
    public void update(V value) throws IOException {
        dao.update(value);
        cacheService.invalidate();
    }

    @Loggable
    @Override
    public int delete(K key) throws IOException {
        int deletedRowCount = dao.delete(key);
        cacheService.invalidate();
        return deletedRowCount;
    }

    @Loggable
    @Override
    public V get(K key) {
        return cacheService.findByKey(key);
    }

    @Loggable
    @Override
    public Collection<V> getAll() {
        return cacheService.getAll();
    }
}
