package com.berkay22demirel.couriertracking.dao.impl;

import com.berkay22demirel.couriertracking.dao.IStoreDao;
import com.berkay22demirel.couriertracking.dao.support.JsonDaoSupport;
import com.berkay22demirel.couriertracking.model.Store;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDao extends JsonDaoSupport<Store, String> implements IStoreDao {

    StoreDao() {
        super("stores");
    }
}
