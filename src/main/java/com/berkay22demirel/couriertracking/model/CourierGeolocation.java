package com.berkay22demirel.couriertracking.model;

import com.berkay22demirel.couriertracking.aop.annotation.ID;

import javax.validation.constraints.NotNull;

public class CourierGeolocation {

    @ID
    private Long id;
    @NotNull(message = "courierId cannot be null")
    private Long courierId;
    private double lat;
    private double lng;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
