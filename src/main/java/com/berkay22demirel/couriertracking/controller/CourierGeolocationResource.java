package com.berkay22demirel.couriertracking.controller;

import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.service.ICourierGeolocationService;
import com.berkay22demirel.couriertracking.service.base.IBaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/courier-geolocation")
public class CourierGeolocationResource {

    @Autowired
    private ICourierGeolocationService courierGeolocationService;

    @Autowired
    private IBaseCrudService<CourierGeolocation, Long> courierGeolocationCrudService;

    @GetMapping(value = "/notify")
    public ResponseEntity<Object> notifyGeolocation(@RequestBody CourierGeolocation courierGeolocation) {
        return new ResponseEntity<>("notified successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/total-travel-distance/{courier-id}")
    public ResponseEntity<Object> getTotalTravelDistance(@PathVariable("courier-id") Long courierId) {
        try {
            return new ResponseEntity<>(courierGeolocationService.getAllByCourierId(courierId), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        courierGeolocationCrudService.delete(id);
        return new ResponseEntity<>("Courier Geolocation is deleted successsfully", HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courierGeolocationCrudService.get(id), HttpStatus.OK);
    }

    @GetMapping(value = "/get-all-by-courier-id/{courier-id}")
    public ResponseEntity<Object> getAllByCourierId(@PathVariable("courier-id") Long courierId) {
        try {
            return new ResponseEntity<>(courierGeolocationService.getTotalTravelDistance(courierId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
