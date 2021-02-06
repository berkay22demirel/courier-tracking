package com.berkay22demirel.couriertracking.service.base;

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

    @Override
    public K add(V value) {
        try {
            K key = dao.persist(value);
            cacheService.invalidate();
            return key;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void update(V value) {
        try {
            dao.update(value);
            cacheService.invalidate();
        } catch (Exception e) {

        }
    }

    @Override
    public int delete(K key) {
        try {
            int deletedRowCount = dao.delete(key);
            cacheService.invalidate();
            return deletedRowCount;
        } catch (IOException e) {
            return 0;
        }
    }

    @Override
    public V get(K key) {
        try {
            return cacheService.findByKey(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<V> getAll() {
        try {
            return cacheService.getAll();
        } catch (Exception e) {
            return null;
        }
    }
}
