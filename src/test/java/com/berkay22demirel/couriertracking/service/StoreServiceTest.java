package com.berkay22demirel.couriertracking.service;

import com.berkay22demirel.couriertracking.CourierTrackingApplication;
import com.berkay22demirel.couriertracking.dao.StoreDao;
import com.berkay22demirel.couriertracking.model.Store;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourierTrackingApplication.class)
@WebAppConfiguration
public class StoreServiceTest {

    @Autowired
    private IStoreService storeService;


    @Test
    public void getAll_ShouldNotEmptyStores_WhenAnyCase() {
        Set<Store> stores = storeService.getAll();

        Assert.assertNotNull(stores);
        Assert.assertNotEquals(0,stores.size());
    }



}
