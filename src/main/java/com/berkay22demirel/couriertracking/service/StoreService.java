package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.dao.StoreDao;
import com.berkay22demirel.couriertracking.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StoreService implements IStoreService {

    @Autowired
    private StoreDao storeDao;

    @Override
    public Set<Store> getAll() {
        return storeDao.getAll();
    }
}
