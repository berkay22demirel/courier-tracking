package com.berkay22demirel.couriertracking.dao;

import com.berkay22demirel.couriertracking.CourierTrackingApplication;
import com.berkay22demirel.couriertracking.dao.impl.CourierGeolocationDao;
import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourierTrackingApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CourierGeolocationDaoTest {

    @InjectMocks
    private CourierGeolocationDao courierGeolocationDao;

    @Test
    public void a_persist() throws IOException, IllegalAccessException {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);

        courierGeolocationDao.persist(courierGeolocation);
        CourierGeolocation insertedCourierGeolocation = courierGeolocationDao.get(1L);

        Assert.assertEquals(courierGeolocation.getId(), insertedCourierGeolocation.getId());
    }

    @Test
    public void b_get() throws IOException {

        CourierGeolocation courierGeolocation = courierGeolocationDao.get(1L);

        Assert.assertNotNull(courierGeolocation);
    }

    @Test
    public void c_update() throws IOException {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);
        courierGeolocation.setCourierId(2L);
        courierGeolocation.setLat(40.9923308);
        courierGeolocation.setLng(29.1244220);

        courierGeolocationDao.update(courierGeolocation);
        CourierGeolocation updatedCourierGeolocation = courierGeolocationDao.get(1L);

        Assert.assertEquals(courierGeolocation.getCourierId(), updatedCourierGeolocation.getCourierId());
        Assert.assertEquals(courierGeolocation.getLat(), updatedCourierGeolocation.getLat(), 0.001);
        Assert.assertEquals(courierGeolocation.getLng(), updatedCourierGeolocation.getLng(), 0.001);
    }

    @Test
    public void d_delete() throws IOException {

        int deletedRowCount = courierGeolocationDao.delete(1L);

        Assert.assertEquals(1, deletedRowCount);
    }
}
