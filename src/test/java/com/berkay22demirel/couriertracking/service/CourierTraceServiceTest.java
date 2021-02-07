package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.CourierTrackingApplication;
import com.berkay22demirel.couriertracking.dao.impl.TrackingCourierInStoreDao;
import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.model.Store;
import com.berkay22demirel.couriertracking.model.TrackingCourierInStore;
import com.berkay22demirel.couriertracking.service.base.IBaseCacheCrudService;
import com.berkay22demirel.couriertracking.service.impl.CourierTraceService;
import com.berkay22demirel.couriertracking.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourierTrackingApplication.class)
@WebAppConfiguration
public class CourierTraceServiceTest {

    @InjectMocks
    private CourierTraceService courierTraceService;
    @MockBean
    private TrackingCourierInStoreDao trackingCourierInStoreDao;
    @MockBean
    private IBaseCacheCrudService<String, Store> storeCacheCrudService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(expected = Exception.class)
    public void trace_ShouldThrowException_WhenCourierGeolocationWasNull() throws Exception {
        Store store = new Store();
        store.setName("berkay mağaza");
        store.setLat(124324);
        store.setLng(23423);

        when(storeCacheCrudService.getAll()).thenReturn(Collections.singletonList(store));
        courierTraceService.trace(null);
    }

    @Test(expected = Exception.class)
    public void trace_ShouldThrowException_WhenCourierGeolocationLatWasNull() throws Exception {
        Store store = new Store();
        store.setName("berkay mağaza");
        store.setLat(124324);
        store.setLng(23423);
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLng(0.1);

        when(storeCacheCrudService.getAll()).thenReturn(Collections.singletonList(store));
        courierTraceService.trace(null);
    }

    @Test(expected = Exception.class)
    public void trace_ShouldThrowException_WhenCourierGeolocationLngWasNull() throws Exception {
        Store store = new Store();
        store.setName("berkay mağaza");
        store.setLat(124324);
        store.setLng(23423);
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(0.1);

        when(storeCacheCrudService.getAll()).thenReturn(Collections.singletonList(store));
        courierTraceService.trace(null);
    }

    @Test
    public void trace_ShouldPersistTrackingCourierInStoreData_WhenCourierInStore() throws Exception {
        Store store = new Store();
        store.setName("berkay mağaza");
        store.setLat(40.9923307);
        store.setLng(29.1244229);
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9924307);
        courierGeolocation.setLng(29.1254229);

        when(storeCacheCrudService.getAll()).thenReturn(Collections.singletonList(store));
        when(trackingCourierInStoreDao.persist(any())).thenReturn(1L);
        courierTraceService.trace(courierGeolocation);

        verify(trackingCourierInStoreDao, times(1)).persist(any());
    }

    @Test
    public void trace_ShouldNotPersistTrackingCourierInStoreData_WhenCourierNotInStore() throws Exception {
        Store store = new Store();
        store.setName("berkay mağaza");
        store.setLat(40.9923307);
        store.setLng(29.1244229);
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9934307);
        courierGeolocation.setLng(29.1284229);

        when(storeCacheCrudService.getAll()).thenReturn(Collections.singletonList(store));
        courierTraceService.trace(courierGeolocation);

        verify(trackingCourierInStoreDao, times(0)).persist(any());
    }

    @Test
    public void trace_ShouldPersistTrackingCourierInStoreData_WhenCourierInStoreAndLastTrackingWasNull() throws Exception {
        Store store = new Store();
        store.setName("berkay mağaza");
        store.setLat(40.9923307);
        store.setLng(29.1244229);
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9924307);
        courierGeolocation.setLng(29.1254229);

        when(storeCacheCrudService.getAll()).thenReturn(Collections.singletonList(store));
        when(trackingCourierInStoreDao.findAll()).thenReturn(new ArrayList<>());
        when(trackingCourierInStoreDao.persist(any())).thenReturn(1L);
        courierTraceService.trace(courierGeolocation);

        verify(trackingCourierInStoreDao, times(1)).persist(any());
    }

    @Test
    public void trace_ShouldPersistTrackingCourierInStoreData_WhenCourierInStoreAndLastTrackingWasNotInIgnoredMinute() throws Exception {
        Store store = new Store();
        store.setName("berkay mağaza");
        store.setLat(40.9923307);
        store.setLng(29.1244229);
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9924307);
        courierGeolocation.setLng(29.1254229);
        TrackingCourierInStore trackingCourierInStore = new TrackingCourierInStore();
        trackingCourierInStore.setStoreName(store.getName());
        trackingCourierInStore.setTrackingDate(DateUtil.getInstance().addToDateByMinutes(new Date(), -2));

        when(storeCacheCrudService.getAll()).thenReturn(Collections.singletonList(store));
        when(trackingCourierInStoreDao.findAll()).thenReturn(Collections.singletonList(trackingCourierInStore));
        when(trackingCourierInStoreDao.persist(any())).thenReturn(1L);
        courierTraceService.trace(courierGeolocation);

        verify(trackingCourierInStoreDao, times(1)).persist(any());
    }

    @Test
    public void trace_ShouldNotPersistTrackingCourierInStoreData_WhenCourierInStoreAndLastTrackingWasInIgnoredMinute() throws Exception {
        Store store = new Store();
        store.setName("berkay mağaza");
        store.setLat(40.9923307);
        store.setLng(29.1244229);
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9924307);
        courierGeolocation.setLng(29.1254229);
        TrackingCourierInStore trackingCourierInStore = new TrackingCourierInStore();
        trackingCourierInStore.setStoreName(store.getName());
        trackingCourierInStore.setTrackingDate(new Date());

        when(storeCacheCrudService.getAll()).thenReturn(Collections.singletonList(store));
        when(trackingCourierInStoreDao.findAll()).thenReturn(Collections.singletonList(trackingCourierInStore));
        when(trackingCourierInStoreDao.persist(any())).thenReturn(1L);
        courierTraceService.trace(courierGeolocation);

        verify(trackingCourierInStoreDao, times(0)).persist(any());
    }

}
