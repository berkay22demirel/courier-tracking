package com.berkay22demirel.couriertracking.controller;


import com.berkay22demirel.couriertracking.model.Store;
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

public class StoreControllerTest extends AbstractTest {

    private static final String CREATE_SERVICE_URL = "http://localhost:8080/store/create";
    private static final String UPDATE_SERVICE_FAIL_URL = "http://localhost:8080/store/update/";
    private static final String UPDATE_SERVICE_URL = "http://localhost:8080/store/update/berkaymm";
    private static final String DELETE_SERVICE_URL = "http://localhost:8080/store/delete/berkaymm";
    private static final String DELETE_SERVICE_FAIL_URL = "http://localhost:8080/store/delete/";
    private static final String GET_SERVICE_URL = "http://localhost:8080/store/get/berkaymm";
    private static final String GET_SERVICE_FAIL_URL = "http://localhost:8080/store/get/";
    private static final String GET_ALL_SERVICE_URL = "http://localhost:8080/store/get-all";

    @MockBean
    private IBaseCacheCrudService<String, Store> storeCacheCrudService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void create_ShouldReturnSuccessfulMessage_WhenThrewNotException() throws Exception {
        Store store = new Store();
        store.setName("berkaymm");
        store.setLat(124324);
        store.setLng(23423);
        String inputJson = super.mapToJson(store);

        when(storeCacheCrudService.add(store)).thenReturn(store.getName());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        assertEquals("Store is created successfully.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void create_ShouldReturnErrorMessage_WhenThrewException() throws Exception {
        Store store = new Store();
        store.setName("berkaymm");
        store.setLat(124324);
        store.setLng(23423);
        String inputJson = super.mapToJson(store);

        when(storeCacheCrudService.add(any())).thenThrow(IOException.class);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void update_ShouldReturnNotFoundStatus_WhenCalledWithNullName() throws Exception {
        Store store = new Store();
        store.setName("berkaymm");
        store.setLat(124324);
        store.setLng(23423);
        String inputJson = super.mapToJson(store);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(UPDATE_SERVICE_FAIL_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void update_ShouldReturnSuccessfulMessage_WhenThrewNotException() throws Exception {
        Store store = new Store();
        store.setName("berkaymm");
        store.setLat(124324);
        store.setLng(23423);
        String inputJson = super.mapToJson(store);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("Store is updated successfully.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void update_ShouldReturnErrorMessage_WhenThrewException() throws Exception {
        Store store = new Store();
        store.setName("berkaymm");
        store.setLat(124324);
        store.setLng(23423);
        String inputJson = super.mapToJson(store);

        doThrow(IOException.class).when(storeCacheCrudService).update(any());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void delete_ShouldReturnNotFoundStatus_WhenCalledWithNullName() throws Exception {

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
        assertEquals("Store is deleted successfully.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void delete_ShouldReturnErrorMessage_WhenThrewException() throws Exception {

        doThrow(IOException.class).when(storeCacheCrudService).delete(any());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(DELETE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void get_ShouldReturnNotFoundStatus_WhenCalledWithNullName() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_SERVICE_FAIL_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void get_ShouldReturnStore_WhenThrewNotException() throws Exception {
        Store store = new Store();
        store.setName("berkaymm");
        store.setLat(124324);
        store.setLng(23423);

        when(storeCacheCrudService.get(any())).thenReturn(store);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(store.getName(), mapFromJson(mvcResult.getResponse().getContentAsString(), Store.class).getName());
    }

    @Test
    public void get_ShouldReturnErrorMessage_WhenThrewException() throws Exception {

        doThrow(IOException.class).when(storeCacheCrudService).get(any());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getAll_ShouldReturnStores_WhenThrewNotException() throws Exception {
        List<Store> storeList = new ArrayList<>();
        Store store = new Store();
        store.setName("berkaymm");
        store.setLat(124324);
        store.setLng(23423);
        storeList.add(store);

        when(storeCacheCrudService.getAll()).thenReturn(storeList);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_ALL_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(storeList.size(), mapFromJson(mvcResult.getResponse().getContentAsString(), storeList.getClass()).size());
    }

    @Test
    public void getAll_ShouldReturnErrorMessage_WhenThrewException() throws Exception {

        doThrow(IOException.class).when(storeCacheCrudService).getAll();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_ALL_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }


}
