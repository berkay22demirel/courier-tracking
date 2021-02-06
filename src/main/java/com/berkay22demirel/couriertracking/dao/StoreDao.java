package com.berkay22demirel.couriertracking.dao;

import com.berkay22demirel.couriertracking.model.Store;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDao extends JsonDaoSupport<Store, String> implements IStoreDao {
    
    StoreDao() {
        super("stores");
    }
}
