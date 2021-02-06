package com.berkay22demirel.couriertracking.service.impl;

import com.berkay22demirel.couriertracking.dao.ICourierGeolocationDao;
import com.berkay22demirel.couriertracking.dao.support.IDaoSupport;
import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.service.IBaseCrudService;
import com.berkay22demirel.couriertracking.service.ICourierGeolocationService;
import com.berkay22demirel.couriertracking.service.ICourierTraceService;
import com.berkay22demirel.couriertracking.service.base.BaseCrudService;
import com.berkay22demirel.couriertracking.util.GeolocationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    Logger logger = LogManager.getLogger(CourierGeolocationService.class);

    public CourierGeolocationService(IDaoSupport<CourierGeolocation, Long> dao) {
        super(dao);
    }

    @Override
    public void notify(CourierGeolocation courierGeolocation) throws Exception {
        try {
            super.add(courierGeolocation);
            courierTraceService.trace(courierGeolocation);
        } catch (Exception e) {
            logger.error("threw an unexpected error in com.berkay22demirel.couriertracking.service.impl.CourierGeolocationService.notify", e);
            throw e;
        }
    }

    @Override
    public Double getTotalTravelDistance(Long courierId) throws Exception {
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
        } catch (Exception e) {
            logger.error("threw an unexpected error in com.berkay22demirel.couriertracking.service.impl.CourierGeolocationService.getTotalTravelDistance", e);
            throw e;
        }
    }

    @Override
    public List<CourierGeolocation> getAllByCourierId(Long courierId) throws IOException {
        try {
            return courierGeolocationDao.findAll().stream()
                    .filter(courierGeolocation -> courierGeolocation.getCourierId().equals(courierId))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("threw an unexpected error in com.berkay22demirel.couriertracking.service.impl.CourierGeolocationService.getAllByCourierId", e);
            throw e;
        }
    }
}
