package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.model.CourierGeolocation;

public interface ICourierTraceService {

    void trace(CourierGeolocation courierGeolocation) throws Exception;
}
