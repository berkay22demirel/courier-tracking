package com.berkay22demirel.couriertracking.model;

import com.berkay22demirel.couriertracking.aop.annotation.ID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TrackingCourierInStore {

    @ID
    private Long id;
    @NotNull(message = "courierId cannot be null")
    private Long courierId;
    @NotEmpty(message = "storeName cannot be empty")
    private String storeName;
    @NotNull(message = "trackingDate cannot be null")
    private Date trackingDate;

    public TrackingCourierInStore() {
    }

    public TrackingCourierInStore(Long courierId, String storeName, Date trackingDate) {
        this.courierId = courierId;
        this.storeName = storeName;
        this.trackingDate = trackingDate;
    }

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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getTrackingDate() {
        return trackingDate;
    }

    public void setTrackingDate(Date trackingDate) {
        this.trackingDate = trackingDate;
    }
}
