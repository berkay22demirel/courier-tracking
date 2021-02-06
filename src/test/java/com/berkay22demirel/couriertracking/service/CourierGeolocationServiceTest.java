package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.CourierTrackingApplication;
import com.berkay22demirel.couriertracking.dao.impl.CourierGeolocationDao;
import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.service.impl.CourierGeolocationService;
import com.berkay22demirel.couriertracking.service.impl.CourierTraceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void notify_ShouldThrowException_WhenTraceThrewException() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);

        doThrow(Exception.class).when(courierTraceService).trace(courierGeolocation);
        when(courierGeolocationDao.persist(courierGeolocation)).thenReturn(1L);
        courierGeolocationService.notify(courierGeolocation);
    }

    @Test
    public void notify_ShouldCallPersist_WhenCalledNotify() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);
        
        courierGeolocationService.notify(courierGeolocation);

        verify(courierGeolocationDao, times(1)).persist(courierGeolocation);
    }

    @Test
    public void notify_ShouldCallTrace_WhenCalledNotify() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);

        courierGeolocationService.notify(courierGeolocation);

        verify(courierGeolocationDao, times(1)).persist(courierGeolocation);
    }

}
