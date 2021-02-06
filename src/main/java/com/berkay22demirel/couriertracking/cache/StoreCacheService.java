package com.berkay22demirel.couriertracking.cache;

import com.berkay22demirel.couriertracking.aop.annotation.Loggable;
import com.berkay22demirel.couriertracking.cache.base.CacheService;
import com.berkay22demirel.couriertracking.cache.base.ICacheService;
import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;
import com.berkay22demirel.couriertracking.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoreCacheService extends CacheService<String, Store> implements ICacheService<String, Store> {

    @Autowired
    private IDaoSupport<Store, String> storeDao;

    private static Map<String, Store> stores;

    public StoreCacheService() throws IOException {
        super(stores);
        invalidate();
    }

    @Loggable
    @Override
    public void invalidate() throws IOException {
        stores = storeDao.findAll().stream().collect(Collectors.toMap(Store::getName, store -> store));
    }

}
