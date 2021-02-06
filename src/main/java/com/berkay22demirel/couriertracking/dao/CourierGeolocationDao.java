package com.berkay22demirel.couriertracking.dao;

import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import org.springframework.stereotype.Service;

@Service
public class CourierGeolocationDao extends JsonDaoSupport<CourierGeolocation, Long> implements ICourierGeolocationDao {

    CourierGeolocationDao() {
        super("courier-geolocations");
    }
}
