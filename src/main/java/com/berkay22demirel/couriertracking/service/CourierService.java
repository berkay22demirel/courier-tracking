package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.dao.IDaoSupport;
import com.berkay22demirel.couriertracking.model.Courier;
import org.springframework.stereotype.Service;

@Service
public class CourierService extends BaseCrudService<Courier, Long> implements ICourierService, IBaseCrudService<Courier, Long> {

    public CourierService(IDaoSupport<Courier, Long> dao) {
        super(dao);
    }
}
