package com.berkay22demirel.couriertracking.dao;

import com.berkay22demirel.couriertracking.model.Store;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Repository
public class StoreDao implements IStoreDao {

    // simulated cache
    private static Set<Store> stores;

    public StoreDao() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("static/stores.json").getInputStream();
            TypeReference<Set<Store>> typeReference = new TypeReference<Set<Store>>() {
            };
            stores = objectMapper.readValue(inputStream, typeReference);
        } catch (Exception e) {
            //FIXME
            stores = new HashSet<>();
        }
    }


    @Override
    public Set<Store> getAll() {
        return stores;
    }
}
