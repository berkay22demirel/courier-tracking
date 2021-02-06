package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.dao.IDaoSupport;
import com.berkay22demirel.couriertracking.model.Store;
import org.springframework.stereotype.Service;

@Service
public class StoreService extends BaseCrudService<Store, String> implements IStoreService, IBaseCrudService<Store, String> {

    public StoreService(IDaoSupport<Store, String> dao) {
        super(dao);
    }
}
