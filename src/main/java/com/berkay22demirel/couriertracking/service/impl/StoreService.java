package com.berkay22demirel.couriertracking.service.impl;

import com.berkay22demirel.couriertracking.cache.base.ICacheService;
import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;
import com.berkay22demirel.couriertracking.model.Store;
import com.berkay22demirel.couriertracking.service.IStoreService;
import com.berkay22demirel.couriertracking.service.base.BaseCacheCrudService;
import com.berkay22demirel.couriertracking.service.base.IBaseCacheCrudService;
import org.springframework.stereotype.Service;

@Service
public class StoreService extends BaseCacheCrudService<String, Store> implements IStoreService, IBaseCacheCrudService<String, Store> {

    public StoreService(ICacheService<String, Store> cacheService, IDaoSupport<Store, String> dao) {
        super(cacheService, dao);
    }
}
