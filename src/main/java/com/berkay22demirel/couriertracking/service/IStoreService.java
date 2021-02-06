package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.model.Store;

import java.io.IOException;
import java.util.Set;

public interface IStoreService {

    Set<Store> getAll() throws IOException;
}
