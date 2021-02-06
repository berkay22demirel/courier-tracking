package com.berkay22demirel.couriertracking.controller;

import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.service.IBaseCrudService;
import com.berkay22demirel.couriertracking.service.ICourierGeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/courier-geolocation")
public class CourierGeolocationResource {

    @Autowired
    private ICourierGeolocationService courierGeolocationService;

    @Autowired
    private IBaseCrudService<CourierGeolocation, Long> courierGeolocationCrudService;

    @GetMapping(value = "/notify")
    public ResponseEntity<Object> notifyGeolocation() {
        return new ResponseEntity<>("notified successfully", HttpStatus.OK);
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
        return new ResponseEntity<>(courierGeolocationService.getAllByCourierId(courierId), HttpStatus.OK);
    }
}
