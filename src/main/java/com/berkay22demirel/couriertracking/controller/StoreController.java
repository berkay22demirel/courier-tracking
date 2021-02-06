package com.berkay22demirel.couriertracking.controller;

import com.berkay22demirel.couriertracking.model.Store;
import com.berkay22demirel.couriertracking.service.base.IBaseCacheCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store")
public class StoreController {

    @Autowired
    private IBaseCacheCrudService<String, Store> storeCacheCrudService;

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Store store) {
        storeCacheCrudService.add(store);
        return new ResponseEntity<>("Store is created successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Store store) {
        storeCacheCrudService.update(store);
        return new ResponseEntity<>("Store is updated successsfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{name}")
    public ResponseEntity<Object> delete(@PathVariable("name") String name) {
        storeCacheCrudService.delete(name);
        return new ResponseEntity<>("Store is deleted successsfully", HttpStatus.OK);
    }

    @GetMapping(value = "/get/{name}")
    public ResponseEntity<Object> get(@PathVariable("name") String name) {
        return new ResponseEntity<>(storeCacheCrudService.get(name), HttpStatus.OK);
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<Object> get() {
        return new ResponseEntity<>(storeCacheCrudService.getAll(), HttpStatus.OK);
    }

}
