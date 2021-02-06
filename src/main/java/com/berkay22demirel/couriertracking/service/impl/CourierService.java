package com.berkay22demirel.couriertracking.service.impl;

import com.berkay22demirel.couriertracking.cache.base.ICacheService;
import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;
import com.berkay22demirel.couriertracking.model.Courier;
import com.berkay22demirel.couriertracking.service.ICourierService;
import com.berkay22demirel.couriertracking.service.base.BaseCacheCrudService;
import com.berkay22demirel.couriertracking.service.base.IBaseCacheCrudService;
import org.springframework.stereotype.Service;

@Service
public class CourierService extends BaseCacheCrudService<Long, Courier> implements ICourierService, IBaseCacheCrudService<Long, Courier> {

    public CourierService(ICacheService<Long, Courier> cacheService, IDaoSupport<Courier, Long> dao) {
        super(cacheService, dao);
    }
}
