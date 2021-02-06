package com.berkay22demirel.couriertracking.cache;

import com.berkay22demirel.couriertracking.cache.base.ICacheService;
import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;
import com.berkay22demirel.couriertracking.model.Courier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourierCacheService implements ICacheService<Long, Courier> {

    @Autowired
    private IDaoSupport<Courier, Long> courierDao;

    private static final Map<Long, Courier> couriers = new HashMap<>();


    @Override
    public Collection<Courier> getAll() {
        return couriers.values();
    }

    @Override
    public Courier findByKey(Long key) {
        return couriers.get(key);
    }

    @Override
    public void invalidate() throws IOException {
        couriers.clear();
        couriers.putAll(courierDao.findAll().stream().collect(Collectors.toMap(Courier::getId, courier -> courier)));
    }
}
