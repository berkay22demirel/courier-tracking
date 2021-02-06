package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.model.CourierGeolocation;

import java.util.List;

public interface ICourierGeolocationService {

    void notify(CourierGeolocation courierGeolocation);

    Double getTotalTravelDistance(Long courierId);

    List<CourierGeolocation> getAllByCourierId(Long courierId);

}
