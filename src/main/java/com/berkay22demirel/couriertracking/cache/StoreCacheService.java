package com.berkay22demirel.couriertracking.cache;

import com.berkay22demirel.couriertracking.cache.base.ICacheService;
import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;
import com.berkay22demirel.couriertracking.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoreCacheService implements ICacheService<String, Store> {

    @Autowired
    private IDaoSupport<Store, String> storeDao;

    private static final Map<String, Store> stores = new HashMap<>();


    @Override
    public Collection<Store> getAll() {
        return stores.values();
    }

    @Override
    public Store findByKey(String key) {
        return stores.get(key);
    }

    @Override
    public void invalidate() throws IOException {
        stores.clear();
        stores.putAll(storeDao.findAll().stream().collect(Collectors.toMap(Store::getName, store -> store)));
    }

}
