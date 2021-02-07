package com.berkay22demirel.couriertracking.service.impl;

import com.berkay22demirel.couriertracking.aop.annotation.Loggable;
import com.berkay22demirel.couriertracking.dao.ICourierGeolocationDao;
import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;
import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.service.ICourierGeolocationService;
import com.berkay22demirel.couriertracking.service.ICourierTraceService;
import com.berkay22demirel.couriertracking.service.base.BaseCrudService;
import com.berkay22demirel.couriertracking.service.base.IBaseCrudService;
import com.berkay22demirel.couriertracking.util.GeolocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourierGeolocationService extends BaseCrudService<CourierGeolocation, Long> implements ICourierGeolocationService, IBaseCrudService<CourierGeolocation, Long> {

    @Autowired
    private ICourierGeolocationDao courierGeolocationDao;
    @Autowired
    private ICourierTraceService courierTraceService;

    public CourierGeolocationService(IDaoSupport<CourierGeolocation, Long> dao) {
        super(dao);
    }

    @Loggable
    @Override
    public void notify(CourierGeolocation courierGeolocation) throws Exception {
        super.add(courierGeolocation);
        courierTraceService.trace(courierGeolocation);
    }

    @Loggable
    @Override
    public double getTotalTravelDistance(Long courierId) throws Exception {
        Iterator<CourierGeolocation> courierGeolocationIterator = courierGeolocationDao.findAll().stream()
                .filter(courierGeolocation -> courierGeolocation.getCourierId().equals(courierId))
                .iterator();
        double totalDistance = 0.0;
        CourierGeolocation startCourierGeolocation = courierGeolocationIterator.hasNext() ? courierGeolocationIterator.next() : null;
        while (startCourierGeolocation != null && courierGeolocationIterator.hasNext()) {
            CourierGeolocation endCourierGeolocation = courierGeolocationIterator.next();
            double distance = GeolocationUtil.getInstance().calculateDistance(startCourierGeolocation.getLat(), startCourierGeolocation.getLng(), endCourierGeolocation.getLat(), endCourierGeolocation.getLng());
            totalDistance += distance;
        }
        return totalDistance;
    }

    @Loggable
    @Override
    public List<CourierGeolocation> getAllByCourierId(Long courierId) throws IOException {
        return courierGeolocationDao.findAll().stream()
                .filter(courierGeolocation -> courierGeolocation.getCourierId().equals(courierId))
                .collect(Collectors.toList());
    }
}
