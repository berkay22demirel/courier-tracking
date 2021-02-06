package com.berkay22demirel.couriertracking.dao;

import com.berkay22demirel.couriertracking.CourierTrackingApplication;
import com.berkay22demirel.couriertracking.dao.impl.StoreDao;
import com.berkay22demirel.couriertracking.model.Store;
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
public class StoreDaoTest {

    @InjectMocks
    private StoreDao storeDao;

    @Test
    public void a_persist() throws IOException, IllegalAccessException {
        Store store = new Store();
        store.setName("berkay mağaza");
        store.setLat(124324);
        store.setLng(23423);

        storeDao.persist(store);
        Store insertedStore = storeDao.get("berkay mağaza");

        Assert.assertEquals(store.getName(), insertedStore.getName());
    }

    @Test
    public void b_get() throws IOException {

        Store store = storeDao.get("Ortaköy MMM Migros");

        Assert.assertNotNull(store);
    }

    @Test
    public void c_update() throws IOException {
        Store store = new Store();
        store.setName("berkay mağaza");
        store.setLat(1);
        store.setLng(2);

        storeDao.update(store);
        Store insertedStore = storeDao.get("berkay mağaza");

        Assert.assertEquals(store.getLat(), insertedStore.getLat(), 0.001);
        Assert.assertEquals(store.getLng(), insertedStore.getLng(), 0.001);
    }

    @Test
    public void d_delete() throws IOException {

        int deletedRowCount = storeDao.delete("berkay mağaza");

        Assert.assertEquals(1, deletedRowCount);
    }
}
