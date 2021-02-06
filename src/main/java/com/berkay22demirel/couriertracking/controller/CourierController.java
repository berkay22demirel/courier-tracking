package com.berkay22demirel.couriertracking.controller;

import com.berkay22demirel.couriertracking.model.Courier;
import com.berkay22demirel.couriertracking.service.IBaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/courier")
public class CourierController {

    @Autowired
    private IBaseCrudService<Courier, Long> courierCrudService;

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Courier courier) {
        courierCrudService.add(courier);
        return new ResponseEntity<>("Courier is created successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Courier courier) {
        courierCrudService.update(courier);
        return new ResponseEntity<>("Courier is updated successsfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        courierCrudService.delete(id);
        return new ResponseEntity<>("Courier is deleted successsfully", HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courierCrudService.get(id), HttpStatus.OK);
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<Object> get() {
        return new ResponseEntity<>(courierCrudService.getAll(), HttpStatus.OK);
    }

}
