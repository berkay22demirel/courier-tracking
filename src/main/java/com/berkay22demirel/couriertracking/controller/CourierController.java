package com.berkay22demirel.couriertracking.controller;

import com.berkay22demirel.couriertracking.model.Courier;
import com.berkay22demirel.couriertracking.service.base.IBaseCacheCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/courier")
public class CourierController {

    @Autowired
    private IBaseCacheCrudService<Long, Courier> courierCacheCrudService;

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Courier courier) {
        try {
            courierCacheCrudService.add(courier);
            return new ResponseEntity<>("Courier is created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Courier courier) {
        try {
            courierCacheCrudService.update(courier);
            return new ResponseEntity<>("Courier is updated successsfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            courierCacheCrudService.delete(id);
            return new ResponseEntity<>("Courier is deleted successsfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(courierCacheCrudService.get(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<Object> get() {
        try {
            return new ResponseEntity<>(courierCacheCrudService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

}
