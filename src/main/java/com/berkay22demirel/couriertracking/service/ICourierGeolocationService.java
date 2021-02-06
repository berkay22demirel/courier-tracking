package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.model.CourierGeolocation;

import java.util.List;

public interface ICourierGeolocationService {

    List<CourierGeolocation> getAllByCourierId(Long courierId);


}
