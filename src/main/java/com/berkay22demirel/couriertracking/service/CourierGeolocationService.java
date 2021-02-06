package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.dao.ICourierGeolocationDao;
import com.berkay22demirel.couriertracking.dao.IDaoSupport;
import com.berkay22demirel.couriertracking.model.CourierGeolocation;
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

    @Override
    public void notify(CourierGeolocation courierGeolocation) {
        super.add(courierGeolocation);
        courierTraceService.trace(courierGeolocation);
    }

    @Override
    public Double getTotalTravelDistance(Long courierId) {
        try {
            Iterator<CourierGeolocation> courierGeolocationIterator = courierGeolocationDao.findAll().stream()
                    .filter(courierGeolocation -> courierGeolocation.getCourierId().equals(courierId))
                    .iterator();
            double totalDistance = 0.0;
            CourierGeolocation startCourierGeolocation = courierGeolocationIterator.next();
            while (startCourierGeolocation != null && courierGeolocationIterator.hasNext()) {
                CourierGeolocation endCourierGeolocation = courierGeolocationIterator.next();
                double distance = GeolocationUtil.getInstance().calculateDistance(startCourierGeolocation.getLat(), startCourierGeolocation.getLng(), endCourierGeolocation.getLat(), endCourierGeolocation.getLng());
                totalDistance += distance;
            }
            return totalDistance;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CourierGeolocation> getAllByCourierId(Long courierId) {
        try {
            return courierGeolocationDao.findAll().stream()
                    .filter(courierGeolocation -> courierGeolocation.getCourierId().equals(courierId))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }
    }
}
