package com.berkay22demirel.couriertracking.dao;

import com.berkay22demirel.couriertracking.CourierTrackingApplication;
import com.berkay22demirel.couriertracking.dao.impl.TrackingCourierInStoreDao;
import com.berkay22demirel.couriertracking.model.TrackingCourierInStore;
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
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourierTrackingApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrackingCourierInStoreDaoTest {

    @InjectMocks
    private TrackingCourierInStoreDao trackingCourierInStoreDao;

    @Test
    public void a_persist() throws IOException, IllegalAccessException {
        TrackingCourierInStore trackingCourierInStore = new TrackingCourierInStore();
        trackingCourierInStore.setId(1L);
        trackingCourierInStore.setCourierId(1L);
        trackingCourierInStore.setStoreName("deneme");
        trackingCourierInStore.setTrackingDate(new Date());

        trackingCourierInStoreDao.persist(trackingCourierInStore);
        TrackingCourierInStore insertedTrackingCourierInStore = trackingCourierInStoreDao.get(1L);

        Assert.assertEquals(trackingCourierInStore.getId(), insertedTrackingCourierInStore.getId());
    }

    @Test
    public void b_get() throws IOException {

        TrackingCourierInStore trackingCourierInStore = trackingCourierInStoreDao.get(1L);

        Assert.assertNotNull(trackingCourierInStore);
    }

    @Test
    public void c_update() throws IOException {
        TrackingCourierInStore trackingCourierInStore = new TrackingCourierInStore();
        trackingCourierInStore.setId(1L);
        trackingCourierInStore.setCourierId(2L);
        trackingCourierInStore.setStoreName("deneme2");
        trackingCourierInStore.setTrackingDate(new Date());

        trackingCourierInStoreDao.update(trackingCourierInStore);
        TrackingCourierInStore updatedTrackingCourierInStore = trackingCourierInStoreDao.get(1L);

        Assert.assertEquals(trackingCourierInStore.getCourierId(), updatedTrackingCourierInStore.getCourierId());
        Assert.assertEquals(trackingCourierInStore.getStoreName(), updatedTrackingCourierInStore.getStoreName());
    }

    @Test
    public void d_delete() throws IOException {

        int deletedRowCount = trackingCourierInStoreDao.delete(1L);

        Assert.assertEquals(1, deletedRowCount);
    }
}
