package com.berkay22demirel.couriertracking.controller;

import com.berkay22demirel.couriertracking.model.Courier;
import com.berkay22demirel.couriertracking.service.base.IBaseCacheCrudService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class CourierControllerTest extends AbstractTest {

    private static final String CREATE_SERVICE_URL = "http://localhost:8080/courier/create";
    private static final String UPDATE_SERVICE_FAIL_URL = "http://localhost:8080/courier/update";
    private static final String UPDATE_SERVICE_URL = "http://localhost:8080/courier/update/1";
    private static final String DELETE_SERVICE_URL = "http://localhost:8080/courier/delete/1";
    private static final String DELETE_SERVICE_FAIL_URL = "http://localhost:8080/courier/delete";
    private static final String GET_SERVICE_URL = "http://localhost:8080/courier/get/1";
    private static final String GET_SERVICE_FAIL_URL = "http://localhost:8080/courier/get";
    private static final String GET_ALL_SERVICE_URL = "http://localhost:8080/courier/get-all";

    @MockBean
    private IBaseCacheCrudService<Long, Courier> courierCacheCrudService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void create_ShouldReturnSuccessfulMessage_WhenThrewNotException() throws Exception {
        Courier courier = new Courier();
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        when(courierCacheCrudService.add(courier)).thenReturn(1L);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        assertEquals("Courier is created successfully.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void create_ShouldReturnErrorMessage_WhenThrewException() throws Exception {
        Courier courier = new Courier();
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        when(courierCacheCrudService.add(any())).thenThrow(IOException.class);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void create_ShouldReturnNameEmptyMessage_WhenCalledWithEmptyName() throws Exception {
        Courier courier = new Courier();
        courier.setName("");
        courier.setAge(25);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("name cannot be empty", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void create_ShouldReturnAgeMinMessage_WhenCalledWithLessThanMinAge() throws Exception {
        Courier courier = new Courier();
        courier.setName("berkay");
        courier.setAge(17);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("age must be min 18", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void create_ShouldReturnAgeMaxMessage_WhenCalledWithOlderThanMaxAge() throws Exception {
        Courier courier = new Courier();
        courier.setName("berkay");
        courier.setAge(66);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("age must be max 65", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void create_ShouldReturnSalaryMinMessage_WhenCalledWithLessThanMinSalary() throws Exception {
        Courier courier = new Courier();
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(2199.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("salary must be min 2200", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void create_ShouldReturnSalaryMaxMessage_WhenCalledWithOlderThanMaxSalary() throws Exception {
        Courier courier = new Courier();
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(100001.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("salary must be max 100000", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void update_ShouldReturnNotFoundStatus_WhenCalledWithNullId() throws Exception {
        Courier courier = new Courier();
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(UPDATE_SERVICE_FAIL_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void update_ShouldReturnSuccessfulMessage_WhenThrewNotException() throws Exception {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("Courier is updated successfully.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void update_ShouldReturnErrorMessage_WhenThrewException() throws Exception {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        doThrow(IOException.class).when(courierCacheCrudService).update(any());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void update_ShouldReturnNameEmptyMessage_WhenCalledWithEmptyName() throws Exception {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("");
        courier.setAge(25);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("name cannot be empty", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void update_ShouldReturnAgeMinMessage_WhenCalledWithLessThanMinAge() throws Exception {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("berkay");
        courier.setAge(17);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("age must be min 18", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void update_ShouldReturnAgeMaxMessage_WhenCalledWithOlderThanMaxAge() throws Exception {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("berkay");
        courier.setAge(66);
        courier.setSalary(2500.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("age must be max 65", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void update_ShouldReturnSalaryMinMessage_WhenCalledWithLessThanMinSalary() throws Exception {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(2199.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("salary must be min 2200", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void update_ShouldReturnSalaryMaxMessage_WhenCalledWithOlderThanMaxSalary() throws Exception {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(100001.0);
        String inputJson = super.mapToJson(courier);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("salary must be max 100000", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void delete_ShouldReturnNotFoundStatus_WhenCalledWithNullId() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(DELETE_SERVICE_FAIL_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void delete_ShouldReturnSuccessfulMessage_WhenThrewNotException() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(DELETE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("Courier is deleted successfully.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void delete_ShouldReturnErrorMessage_WhenThrewException() throws Exception {

        doThrow(IOException.class).when(courierCacheCrudService).delete(any());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(DELETE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void get_ShouldReturnNotFoundStatus_WhenCalledWithNullId() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_SERVICE_FAIL_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void get_ShouldReturnCourier_WhenThrewNotException() throws Exception {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(2500.0);

        when(courierCacheCrudService.get(any())).thenReturn(courier);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(courier.getId(), mapFromJson(mvcResult.getResponse().getContentAsString(), Courier.class).getId());
    }

    @Test
    public void get_ShouldReturnErrorMessage_WhenThrewException() throws Exception {

        doThrow(IOException.class).when(courierCacheCrudService).get(any());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getAll_ShouldReturnCouriers_WhenThrewNotException() throws Exception {
        List<Courier> courierList = new ArrayList<>();
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setName("berkay");
        courier.setAge(25);
        courier.setSalary(2500.0);
        courierList.add(courier);


        when(courierCacheCrudService.getAll()).thenReturn(courierList);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_ALL_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(courierList.size(), mapFromJson(mvcResult.getResponse().getContentAsString(), courierList.getClass()).size());
    }

    @Test
    public void getAll_ShouldReturnErrorMessage_WhenThrewException() throws Exception {

        doThrow(IOException.class).when(courierCacheCrudService).getAll();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_ALL_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

}
