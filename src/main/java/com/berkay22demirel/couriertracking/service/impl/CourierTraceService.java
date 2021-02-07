package com.berkay22demirel.couriertracking.service.impl;

import com.berkay22demirel.couriertracking.aop.annotation.Loggable;
import com.berkay22demirel.couriertracking.dao.ITrackingCourierInStoreDao;
import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.model.Store;
import com.berkay22demirel.couriertracking.model.TrackingCourierInStore;
import com.berkay22demirel.couriertracking.service.ICourierTraceService;
import com.berkay22demirel.couriertracking.service.base.IBaseCacheCrudService;
import com.berkay22demirel.couriertracking.util.DateUtil;
import com.berkay22demirel.couriertracking.util.GeolocationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class CourierTraceService implements ICourierTraceService {

    private final static int IGNORED_COURIER_TRACKING_MINUTE = 1;
    private final static double TRACKING_KILOMETER_COURIER_IN_STORE = 0.1;

    @Autowired
    private ITrackingCourierInStoreDao trackingCourierInStoreDao;
    @Autowired
    private IBaseCacheCrudService<String, Store> storeCacheCrudService;

    private Logger logger = LogManager.getLogger(CourierTraceService.class);

    @Loggable
    @Override
    public void trace(CourierGeolocation courierGeolocation) throws Exception {
        storeCacheCrudService.getAll().stream()
                .filter(store -> isCourierInStore(store, courierGeolocation))
                .forEach(store -> {
                    try {
                        TrackingCourierInStore lastTackingCourierInStoreByStoreName = trackingCourierInStoreDao.findAll().stream().filter(trackingCourierInStore -> trackingCourierInStore.getStoreName().equals(store.getName())).reduce((first, second) -> second)
                                .orElse(null);
                        if (lastTackingCourierInStoreByStoreName == null || !isIgnoredByTracking(lastTackingCourierInStoreByStoreName.getTrackingDate())) {
                            TrackingCourierInStore trackingCourierInStore = new TrackingCourierInStore(courierGeolocation.getCourierId(), store.getName(), new Date());
                            trackingCourierInStoreDao.persist(trackingCourierInStore);
                        }
                    } catch (IOException | IllegalAccessException e) {
                        logger.error("threw an unexpected error in com.berkay22demirel.couriertracking.service.impl.CourierTraceService.trace", e);
                    }
                });
    }

    private boolean isCourierInStore(Store store, CourierGeolocation courierGeolocation) {
        return GeolocationUtil.getInstance().calculateDistance(store.getLat(), store.getLng(), courierGeolocation.getLat(), courierGeolocation.getLng()) <= TRACKING_KILOMETER_COURIER_IN_STORE;
    }

    private boolean isIgnoredByTracking(Date lastLogDate) {
        lastLogDate = DateUtil.getInstance().addToDateByMinutes(lastLogDate, IGNORED_COURIER_TRACKING_MINUTE);
        return DateUtil.getInstance().isDateExpired(lastLogDate, new Date());
    }
}
