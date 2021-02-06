package com.berkay22demirel.couriertracking.cache;

import com.berkay22demirel.couriertracking.dao.IStoreDao;
import com.berkay22demirel.couriertracking.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StoreCacheService implements ICacheService<Store, String> {

    @Autowired
    private IStoreDao storeDao;

    private static Map<String, Store> stores;

    public StoreCacheService() throws IOException {
        stores = storeDao.findAll().stream().collect(Collectors.toMap(Store::getName, store -> store));
    }

    @Override
    public Set<Store> getAll() {
        return new HashSet<>(stores.values());
    }

    @Override
    public Store findByKey(String key) {
        return stores.get(key);
    }

}
