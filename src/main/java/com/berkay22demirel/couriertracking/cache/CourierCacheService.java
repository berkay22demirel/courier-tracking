package com.berkay22demirel.couriertracking.cache;

import com.berkay22demirel.couriertracking.cache.base.CacheService;
import com.berkay22demirel.couriertracking.cache.base.ICacheService;
import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;
import com.berkay22demirel.couriertracking.model.Courier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourierCacheService extends CacheService<Long, Courier> implements ICacheService<Long, Courier> {

    @Autowired
    private IDaoSupport<Courier, Long> courierDao;

    private static Map<Long, Courier> couriers;

    public CourierCacheService() throws IOException {
        super(couriers);
        invalidate();
    }

    @Override
    public void invalidate() throws IOException {
        couriers = courierDao.findAll().stream().collect(Collectors.toMap(Courier::getId, courier -> courier));

    }
}
