package com.berkay22demirel.couriertracking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/courier")
public class CourierController {

    @GetMapping(value = "/notify-geolocation")
    public ResponseEntity<Object> notifyGeolocation() {
        return new ResponseEntity<>("notified successfully", HttpStatus.OK);
    }

}
