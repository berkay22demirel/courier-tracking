package com.berkay22demirel.couriertracking.dao;

import com.berkay22demirel.couriertracking.CourierTrackingApplication;
import com.berkay22demirel.couriertracking.dao.impl.CourierDao;
import com.berkay22demirel.couriertracking.model.Courier;
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
public class CourierDaoTest {

    @InjectMocks
    private CourierDao courierDao;

    @Test
    public void a_persist() throws IOException, IllegalAccessException {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(1000.0);

        courierDao.persist(courier);
        Courier insertedCourier = courierDao.get(1L);

        Assert.assertEquals(courier.getId(), insertedCourier.getId());
    }

    @Test
    public void b_get() throws IOException {

        Courier courier = courierDao.get(1L);

        Assert.assertNotNull(courier);
    }

    @Test
    public void c_update() throws IOException {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("berkay2");
        courier.setAge(26);
        courier.setSalary(1002.0);

        courierDao.update(courier);
        Courier updatedCourier = courierDao.get(1L);

        Assert.assertEquals(courier.getName(), updatedCourier.getName());
        Assert.assertEquals(courier.getAge(), updatedCourier.getAge());
        Assert.assertEquals(courier.getSalary(), updatedCourier.getSalary());
    }

    @Test
    public void d_delete() throws IOException {

        int deletedRowCount = courierDao.delete(1L);

        Assert.assertEquals(1, deletedRowCount);
    }
}
