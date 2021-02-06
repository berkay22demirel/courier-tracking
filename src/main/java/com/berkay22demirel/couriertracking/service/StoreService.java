package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.dao.IStoreDao;
import com.berkay22demirel.couriertracking.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class StoreService implements IStoreService {

    @Autowired
    private IStoreDao storeDao;

    @Override
    public Set<Store> getAll() throws IOException {
        return new HashSet<>(storeDao.findAll());
    }
}
