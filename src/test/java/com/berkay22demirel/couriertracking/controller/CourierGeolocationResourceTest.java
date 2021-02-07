package com.berkay22demirel.couriertracking.controller;

import com.berkay22demirel.couriertracking.model.CourierGeolocation;
import com.berkay22demirel.couriertracking.service.ICourierGeolocationService;
import com.berkay22demirel.couriertracking.service.base.IBaseCrudService;
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
import static org.mockito.Mockito.*;

public class CourierGeolocationResourceTest extends AbstractTest {

    private static final String NOTIFY_SERVICE_URL = "http://localhost:8080/courier-geolocation/notify";
    private static final String TOTAL_TRAVEL_DISTANCE_SERVICE_FAIL_URL = "http://localhost:8080/courier-geolocation/total-travel-distance";
    private static final String TOTAL_TRAVEL_DISTANCE_SERVICE_URL = "http://localhost:8080/courier-geolocation/total-travel-distance/1";
    private static final String DELETE_SERVICE_FAIL_URL = "http://localhost:8080/courier-geolocation/delete";
    private static final String DELETE_SERVICE_URL = "http://localhost:8080/courier-geolocation/delete/1";
    private static final String GET_SERVICE_FAIL_URL = "http://localhost:8080/courier-geolocation/get";
    private static final String GET_SERVICE_URL = "http://localhost:8080/courier-geolocation/get/1";
    private static final String GET_ALL_BY_COURIER_ID_FAIL_SERVICE_URL = "http://localhost:8080/courier-geolocation/get-all-by-courier-id";
    private static final String GET_ALL_BY_COURIER_ID_SERVICE_URL = "http://localhost:8080/courier-geolocation/get-all-by-courier-id/1";

    @MockBean
    private ICourierGeolocationService courierGeolocationService;
    @MockBean
    private IBaseCrudService<CourierGeolocation, Long> courierGeolocationCrudService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }


    @Test
    public void notify_ShouldReturnSuccessfulMessage_WhenThrewNotException() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);
        String inputJson = super.mapToJson(courierGeolocation);

        doNothing().when(courierGeolocationService).notify(courierGeolocation);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(NOTIFY_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("notified successfully.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void notify_ShouldReturnErrorMessage_WhenThrewException() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);
        String inputJson = super.mapToJson(courierGeolocation);

        doThrow(Exception.class).when(courierGeolocationService).notify(any());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(NOTIFY_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void notify_ShouldReturnCourierIdNullMessage_WhenCalledWithNullCourierId() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(null);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(29.1244229);
        String inputJson = super.mapToJson(courierGeolocation);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(NOTIFY_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("courierId cannot be null", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void notify_ShouldReturnLatNullMessage_WhenCalledWithNullLat() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(null);
        courierGeolocation.setLng(29.1244229);
        String inputJson = super.mapToJson(courierGeolocation);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(NOTIFY_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("lat cannot be null", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void notify_ShouldReturnLngNullMessage_WhenCalledWithNullLng() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocation.setLat(40.9923307);
        courierGeolocation.setLng(null);
        String inputJson = super.mapToJson(courierGeolocation);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(NOTIFY_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals("lng cannot be null", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getTotalTravelDistance_ShouldReturnNotFoundStatus_WhenCalledWithNullCourierId() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(TOTAL_TRAVEL_DISTANCE_SERVICE_FAIL_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void getTotalTravelDistance_ShouldReturnTotalTravelDistance_WhenThrewNotException() throws Exception {

        when(courierGeolocationService.getTotalTravelDistance(anyLong())).thenReturn(100.0);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(TOTAL_TRAVEL_DISTANCE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(100.0, mapFromJson(mvcResult.getResponse().getContentAsString(), Double.class), 0.001);
    }

    @Test
    public void getTotalTravelDistance_ShouldReturnErrorMessage_WhenThrewException() throws Exception {

        doThrow(Exception.class).when(courierGeolocationService).getTotalTravelDistance(anyLong());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(TOTAL_TRAVEL_DISTANCE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
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
    public void delete_ShouldReturnSuccessMessage_WhenThrewNotException() throws Exception {

        when(courierGeolocationCrudService.delete(anyLong())).thenReturn(anyInt());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(DELETE_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("Courier Geolocation is deleted successfully.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void delete_ShouldReturnErrorMessage_WhenThrewException() throws Exception {

        doThrow(IOException.class).when(courierGeolocationCrudService).delete(anyLong());
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
    public void get_ShouldReturnCourierGeolocation_WhenThrewNotException() throws Exception {
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setId(1L);

        when(courierGeolocationCrudService.get(anyLong())).thenReturn(courierGeolocation);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(courierGeolocation.getId(), mapFromJson(mvcResult.getResponse().getContentAsString(), CourierGeolocation.class).getId());
    }

    @Test
    public void get_ShouldReturnErrorMessage_WhenThrewException() throws Exception {

        doThrow(IOException.class).when(courierGeolocationCrudService).get(anyLong());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getAllByCourierId_ShouldReturnNotFoundStatus_WhenCalledWithNullCourierId() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_ALL_BY_COURIER_ID_FAIL_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void getAllByCourierId_ShouldReturnTotalTravelDistance_WhenThrewNotException() throws Exception {
        List<CourierGeolocation> courierGeolocationList = new ArrayList<>();
        CourierGeolocation courierGeolocation = new CourierGeolocation();
        courierGeolocation.setCourierId(1L);
        courierGeolocationList.add(courierGeolocation);

        when(courierGeolocationService.getAllByCourierId(anyLong())).thenReturn(courierGeolocationList);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_ALL_BY_COURIER_ID_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(courierGeolocationList.size(), mapFromJson(mvcResult.getResponse().getContentAsString(), courierGeolocationList.getClass()).size());
    }

    @Test
    public void getAllByCourierId_ShouldReturnErrorMessage_WhenThrewException() throws Exception {

        doThrow(IOException.class).when(courierGeolocationService).getAllByCourierId(anyLong());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_ALL_BY_COURIER_ID_SERVICE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("An unexpected error has occurred.", mvcResult.getResponse().getContentAsString());
    }

}
