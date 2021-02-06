package com.berkay22demirel.couriertracking.dao;

import com.berkay22demirel.couriertracking.model.Courier;
import org.springframework.stereotype.Repository;

@Repository
public class CourierDao extends JsonDaoSupport<Courier, Long> implements ICourierDao {

    public CourierDao() {
        super("couriers");
    }
}
