package com.berkay22demirel.couriertracking.dao.impl;

import com.berkay22demirel.couriertracking.dao.ICourierDao;
import com.berkay22demirel.couriertracking.dao.support.JsonDaoSupport;
import com.berkay22demirel.couriertracking.model.Courier;
import org.springframework.stereotype.Repository;

@Repository
public class CourierDao extends JsonDaoSupport<Courier, Long> implements ICourierDao {

    public CourierDao() {
        super("couriers");
    }
}
