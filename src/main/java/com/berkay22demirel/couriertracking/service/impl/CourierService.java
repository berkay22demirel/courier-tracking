package com.berkay22demirel.couriertracking.service.impl;

import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;
import com.berkay22demirel.couriertracking.model.Courier;
import com.berkay22demirel.couriertracking.service.IBaseCrudService;
import com.berkay22demirel.couriertracking.service.ICourierService;
import com.berkay22demirel.couriertracking.service.base.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class CourierService extends BaseCrudService<Courier, Long> implements ICourierService, IBaseCrudService<Courier, Long> {

    public CourierService(IDaoSupport<Courier, Long> dao) {
        super(dao);
    }
}
