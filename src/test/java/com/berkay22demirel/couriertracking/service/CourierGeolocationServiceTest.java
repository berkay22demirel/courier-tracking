package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.CourierTrackingApplication;
import com.berkay22demirel.couriertracking.dao.impl.CourierGeolocationDao;
import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.service.impl.CourierGeolocationService;
import com.berkay22demirel.couriertracking.service.impl.CourierTraceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourierTrackingApplication.class)
public class CourierGeolocationServiceTest {

    @InjectMocks
    private CourierGeolocationService courierGeolocationService;
    @MockBean
    private CourierGeolocationDao courierGeolocationDao;
    @MockBean
    private CourierTraceService courierTraceService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(expected = Exception.class)
    public void notify_ShouldThrowException_WhenThrewExceptionFromTrace() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();

        doThrow(Exception.class).when(courierTraceService).trace(any());
        when(courierGeolocationDao.persist(any())).thenReturn(1L);
        courierGeolocationService.notify(courierGeolocation);
    }

    @Test(expected = IOException.class)
    public void notify_ShouldThrowAnyException_WhenThrewAnyExceptionFromAdd() throws Exception {

        doThrow(IOException.class).when(courierGeolocationDao).persist(any());
        courierGeolocationService.notify(null);
    }

    @Test
    public void notify_ShouldAddCourierGeolocation() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();

        when(courierGeolocationDao.persist(any())).thenReturn(1L);
        doNothing().when(courierTraceService).trace(any());
        courierGeolocationService.notify(courierGeolocation);

        verify(courierGeolocationDao, times(1)).persist(courierGeolocation);
    }

    @Test
    public void notify_ShouldTrace() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();

        when(courierGeolocationDao.persist(any())).thenReturn(1L);
        doNothing().when(courierTraceService).trace(any());
        courierGeolocationService.notify(courierGeolocation);

        verify(courierTraceService, times(1)).trace(courierGeolocation);
    }

    @Test
    public void getTotalTravelDistance_ShouldTotalDistanceIsZero_WhenNotHadCourierGeolocationData() throws Exception {

        when(courierGeolocationDao.findAll()).thenReturn(new ArrayList<>());
        double totalDistance = courierGeolocationService.getTotalTravelDistance(1L);

        Assert.assertEquals(0.0, totalDistance, 0.001);
    }

    @Test
    public void getTotalTravelDistance_ShouldTotalDistanceIsZero_WhenHadOneCourierGeolocationData() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);

        when(courierGeolocationDao.findAll()).thenReturn(Collections.singletonList(courierGeolocation));
        double totalDistance = courierGeolocationService.getTotalTravelDistance(1L);

        Assert.assertEquals(0.0, totalDistance, 0.001);
    }

    @Test
    public void getTotalTravelDistance_ShouldTotalDistanceIsNotZero_WhenHadMoreCourierGeolocationData() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);
        CourierGeolocation courierGeolocation2 = new CourierGeolocation();
        courierGeolocation2.setId(1L);
        courierGeolocation2.setCourierId(1L);
        courierGeolocation2.setLat(41.9923307);
        courierGeolocation2.setLng(28.1244229);

        when(courierGeolocationDao.findAll()).thenReturn(Arrays.asList(courierGeolocation, courierGeolocation2));
        double totalDistance = courierGeolocationService.getTotalTravelDistance(1L);

        Assert.assertNotEquals(0.0, totalDistance, 0.001);
    }

    @Test(expected = IOException.class)
    public void getTotalTravelDistance_ShouldThrowAnyException_WhenThrewExceptionFromFindAll() throws Exception {

        doThrow(IOException.class).when(courierGeolocationDao).findAll();
        courierGeolocationService.getTotalTravelDistance(1L);
    }

    @Test
    public void getAllByCourierId_ShouldReturnEmptyList_WhenCourierIdWasNull() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);

        when(courierGeolocationDao.findAll()).thenReturn(Collections.singletonList(courierGeolocation));
        List<CourierGeolocation> courierGeolocationList = courierGeolocationService.getAllByCourierId(null);

        Assert.assertEquals(0, courierGeolocationList.size());
    }

    @Test
    public void getAllByCourierId_ShouldReturnEmptyList_WhenHadNotGeolocationData() throws Exception {

        when(courierGeolocationDao.findAll()).thenReturn(new ArrayList<>());
        List<CourierGeolocation> courierGeolocationList = courierGeolocationService.getAllByCourierId(null);

        Assert.assertEquals(0, courierGeolocationList.size());
    }

    @Test
    public void getAllByCourierId_ShouldReturnEmptyList_WhenCourierIdWasNotEqualsAnyData() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);

        when(courierGeolocationDao.findAll()).thenReturn(Collections.singletonList(courierGeolocation));
        List<CourierGeolocation> courierGeolocationList = courierGeolocationService.getAllByCourierId(2L);

        Assert.assertEquals(0, courierGeolocationList.size());
    }

    @Test
    public void getAllByCourierId_ShouldReturnList_WhenCourierIdWasEqualsAnyData() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);

        when(courierGeolocationDao.findAll()).thenReturn(Collections.singletonList(courierGeolocation));
        List<CourierGeolocation> courierGeolocationList = courierGeolocationService.getAllByCourierId(1L);

        Assert.assertEquals(1, courierGeolocationList.size());
    }

    @Test(expected = IOException.class)
    public void getAllByCourierId_ShouldThrowAnyException_WhenThrewExceptionFromFindAll() throws Exception {

        doThrow(IOException.class).when(courierGeolocationDao).findAll();
        courierGeolocationService.getAllByCourierId(1L);
    }

}
