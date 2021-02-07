package com.berkay22demirel.couriertracking.model;

import com.berkay22demirel.couriertracking.aop.annotation.ID;

import javax.validation.constraints.NotNull;

public class CourierGeolocation {

    @ID
    private Long id;
    @NotNull(message = "courierId cannot be null")
    private Long courierId;
    @NotNull(message = "lat cannot be null")
    private Double lat;
    @NotNull(message = "lng cannot be null")
    private Double lng;

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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
