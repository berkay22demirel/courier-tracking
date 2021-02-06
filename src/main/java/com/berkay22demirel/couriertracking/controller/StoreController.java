package com.berkay22demirel.couriertracking.controller;

import com.berkay22demirel.couriertracking.model.Store;
import com.berkay22demirel.couriertracking.service.base.IBaseCacheCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping(value = "/store")
public class StoreController {

    @Autowired
    private IBaseCacheCrudService<String, Store> storeCacheCrudService;

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody @Valid Store store) {
        try {
            storeCacheCrudService.add(store);
            return new ResponseEntity<>("Store is created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @PutMapping(value = "/update/{name}")
    public ResponseEntity<Object> update(@PathVariable("name") @NotNull(message = "name cannot be empty") String name, @RequestBody @Valid Store store) {
        try {
            store.setName(name);
            storeCacheCrudService.update(store);
            return new ResponseEntity<>("Store is updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/delete/{name}")
    public ResponseEntity<Object> delete(@PathVariable("name") @NotNull(message = "name cannot be empty") String name) {
        try {
            storeCacheCrudService.delete(name);
            return new ResponseEntity<>("Store is deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/{name}")
    public ResponseEntity<Object> get(@PathVariable("name") @NotNull(message = "name cannot be empty") String name) {
        try {
            return new ResponseEntity<>(storeCacheCrudService.get(name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<Object> get() {
        try {
            return new ResponseEntity<>(storeCacheCrudService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getBindingResult()
                .getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst().orElse("Bad Request."), HttpStatus.BAD_REQUEST);
    }

}
