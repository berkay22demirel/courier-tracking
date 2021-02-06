package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.model.CourierGeolocation;

import java.io.IOException;
import java.util.List;

public interface ICourierGeolocationService {

    void notify(CourierGeolocation courierGeolocation) throws Exception;

    Double getTotalTravelDistance(Long courierId) throws Exception;

    List<CourierGeolocation> getAllByCourierId(Long courierId) throws IOException;

}
