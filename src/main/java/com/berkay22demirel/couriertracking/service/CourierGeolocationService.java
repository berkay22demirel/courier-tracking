package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.dao.ICourierGeolocationDao;
import com.berkay22demirel.couriertracking.dao.IDaoSupport;
import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourierGeolocationService extends BaseCrudService<CourierGeolocation, Long> implements ICourierGeolocationService, IBaseCrudService<CourierGeolocation, Long> {

    @Autowired
    private ICourierGeolocationDao courierGeolocationDao;

    public CourierGeolocationService(IDaoSupport<CourierGeolocation, Long> dao) {
        super(dao);
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
