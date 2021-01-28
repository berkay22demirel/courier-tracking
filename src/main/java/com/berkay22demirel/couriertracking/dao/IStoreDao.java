package com.berkay22demirel.couriertracking.dao;

import com.berkay22demirel.couriertracking.model.Store;

import java.util.Set;

public interface IStoreDao {

    Set<Store> getAll();
}
