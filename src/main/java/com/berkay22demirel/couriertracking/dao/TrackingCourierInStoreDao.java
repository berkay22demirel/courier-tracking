package com.berkay22demirel.couriertracking.dao;

import com.berkay22demirel.couriertracking.model.TrackingCourierInStore;
import org.springframework.stereotype.Repository;

@Repository
public class TrackingCourierInStoreDao extends JsonDaoSupport<TrackingCourierInStore, Long> implements ITrackingCourierInStoreDao {

    TrackingCourierInStoreDao() {
        super("tracking-courier-in-store");
    }
}
