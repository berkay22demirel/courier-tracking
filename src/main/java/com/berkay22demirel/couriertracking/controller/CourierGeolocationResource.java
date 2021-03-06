package com.berkay22demirel.couriertracking.controller;

import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.service.ICourierGeolocationService;
import com.berkay22demirel.couriertracking.service.base.IBaseCrudService;
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
@RequestMapping(value = "/courier-geolocation")
public class CourierGeolocationResource {

    @Autowired
    private ICourierGeolocationService courierGeolocationService;

    @Autowired
    private IBaseCrudService<CourierGeolocation, Long> courierGeolocationCrudService;

    @GetMapping(value = "/notify")
    public ResponseEntity<Object> notifyGeolocation(@RequestBody @Valid CourierGeolocation courierGeolocation) {
        try {
            courierGeolocationService.notify(courierGeolocation);
            return new ResponseEntity<>("notified successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @GetMapping(value = "/total-travel-distance/{courier-id}")
    public ResponseEntity<Object> getTotalTravelDistance(@PathVariable("courier-id") @NotNull(message = "courierId cannot be null") Long courierId) {
        try {
            return new ResponseEntity<>(courierGeolocationService.getTotalTravelDistance(courierId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") @NotNull(message = "id cannot be null") Long id) {
        try {
            courierGeolocationCrudService.delete(id);
            return new ResponseEntity<>("Courier Geolocation is deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") @NotNull(message = "id cannot be null") Long id) {
        try {
            return new ResponseEntity<>(courierGeolocationCrudService.get(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error has occurred.", HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get-all-by-courier-id/{courier-id}")
    public ResponseEntity<Object> getAllByCourierId(@PathVariable("courier-id") @NotNull(message = "courierId cannot be null") Long courierId) {
        try {
            return new ResponseEntity<>(courierGeolocationService.getAllByCourierId(courierId), HttpStatus.OK);
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
