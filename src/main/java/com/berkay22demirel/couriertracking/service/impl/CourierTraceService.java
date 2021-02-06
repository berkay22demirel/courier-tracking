package com.berkay22demirel.couriertracking.service.impl;

import com.berkay22demirel.couriertracking.aop.annotation.Loggable;
import com.berkay22demirel.couriertracking.dao.impl.TrackingCourierInStoreDao;
import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.model.Store;
import com.berkay22demirel.couriertracking.model.TrackingCourierInStore;
import com.berkay22demirel.couriertracking.service.ICourierTraceService;
import com.berkay22demirel.couriertracking.service.base.IBaseCacheCrudService;
import com.berkay22demirel.couriertracking.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class CourierTraceService implements ICourierTraceService {

    private final static int IGNORED_COURIER_TRACKING_MINUTE = 1;

    @Autowired
    private TrackingCourierInStoreDao trackingCourierInStoreDao;
    @Autowired
    private IBaseCacheCrudService<String, Store> storeCacheCrudService;

    @Loggable
    @Override
    public void trace(CourierGeolocation courierGeolocation) throws Exception {
        storeCacheCrudService.getAll().forEach(store -> {
            try {
                TrackingCourierInStore lastTackingCourierInStoreByStoreName = trackingCourierInStoreDao.findAll().stream().filter(trackingCourierInStore -> trackingCourierInStore.getStoreName().equals(store.getName())).reduce((first, second) -> second)
                        .orElse(null);
                if (lastTackingCourierInStoreByStoreName == null || isNotIgnoredByTracking(lastTackingCourierInStoreByStoreName.getTrackingDate())) {
                    TrackingCourierInStore trackingCourierInStore = new TrackingCourierInStore(courierGeolocation.getCourierId(), store.getName(), new Date());
                    trackingCourierInStoreDao.persist(trackingCourierInStore);
                }
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean isNotIgnoredByTracking(Date lastLogDate) {
        lastLogDate = DateUtil.getInstance().addToDateByMinutes(lastLogDate, IGNORED_COURIER_TRACKING_MINUTE);
        return DateUtil.getInstance().isDateExpired(lastLogDate, new Date());
    }
}
