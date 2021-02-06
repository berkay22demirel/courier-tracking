package com.berkay22demirel.couriertracking.service.impl;

import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;
import com.berkay22demirel.couriertracking.model.Store;
import com.berkay22demirel.couriertracking.service.IBaseCrudService;
import com.berkay22demirel.couriertracking.service.IStoreService;
import com.berkay22demirel.couriertracking.service.base.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class StoreService extends BaseCrudService<Store, String> implements IStoreService, IBaseCrudService<Store, String> {

    public StoreService(IDaoSupport<Store, String> dao) {
        super(dao);
    }
}
