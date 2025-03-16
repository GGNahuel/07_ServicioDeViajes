package com.nahuelgDev.journeyjoy.test_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.configurations.SecurityConfig;
import com.nahuelgDev.journeyjoy.controllers.RequestsController;
import com.nahuelgDev.journeyjoy.dtos.RequestsUpdateDto;
import com.nahuelgDev.journeyjoy.services.interfaces.RequestsService_I;

@WebMvcTest(RequestsController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import(SecurityConfig.class)
public class Test_RequestsController {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;
  
  @SuppressWarnings("removal")
  @MockBean RequestsService_I service;

  private Requests request1, request2;

  @BeforeEach
  void setUp() {
    request1 = Requests.builder().id("1").build();
    request2 = Requests.builder().id("2").build();
  }

  @Test @WithMockUser
  void getAll_returnsExpectedList() throws Exception {
    List<Requests> expected = List.of(request1, request2);
    when(service.getAll()).thenReturn(expected);

    MvcResult response = mockMvc.perform(get("/api/request")).andExpect(status().isOk()).andReturn();
    String reponseJson = response.getResponse().getContentAsString();
    List<Requests> actual = objectMapper.readValue(reponseJson, new TypeReference<List<Requests>>() {});

    assertIterableEquals(expected, actual);
    verify(service).getAll();
  }

  @Test
  void getAll_shouldDenyAccess() throws Exception {
    mockMvc.perform(get("/api/request")).andExpect(status().isUnauthorized());
    verify(service, times(0)).getAll();
  }

  @Test @WithMockUser
  void getById_returnsExpected() throws Exception {
    when(service.getById("1")).thenReturn(request1);

    MvcResult response = mockMvc.perform(get("/api/request/1")).andExpect(status().isOk()).andReturn();
    String responseJson = response.getResponse().getContentAsString();
    Requests actual = objectMapper.readValue(responseJson, Requests.class);

    assertEquals(request1, actual);
    verify(service).getById("1");
  }

  @Test
  void getById_shouldDenyAccess() throws Exception {
    mockMvc.perform(get("/api/request/1")).andExpect(status().isUnauthorized());
    verify(service, times(0)).getById(anyString());
  }

  @Test @WithMockUser
  void getByTravelId_returnsRequests() throws Exception {
    List<Requests> expected = List.of(request2);
    when(service.getByTravelId("1")).thenReturn(expected);

    MvcResult response = mockMvc.perform(get("/api/request/travel?id=1")).andExpect(status().isOk()).andReturn();
    String responseJson = response.getResponse().getContentAsString();
    List<Requests> actual = objectMapper.readValue(responseJson, new TypeReference<List<Requests>>() {});

    assertIterableEquals(expected, actual);
    verify(service).getByTravelId("1");
  }

  @Test
  void getByTravelId_shouldDenyAccess() throws Exception {
    mockMvc.perform(get("/api/request/travel?id=1")).andExpect(status().isUnauthorized());
    verify(service, times(0)).getByTravelId("1");
  }

  @Test
  void create_returnsRequest() throws Exception {
    String inputJson = objectMapper.writeValueAsString(request1);
    when(service.create(request1)).thenReturn(request1);

    MvcResult response = mockMvc.perform(post("/api/request")
      .contentType(MediaType.APPLICATION_JSON).content(inputJson)
      .with(csrf())
    ).andExpect(status().isCreated()).andReturn();
    String responseJson = response.getResponse().getContentAsString();
    Requests actual = objectMapper.readValue(responseJson, Requests.class);

    assertEquals(request1, actual);
    verify(service).create(request1);
  }

  @Test
  void update_returnsRequest() throws Exception {
    RequestsUpdateDto input = RequestsUpdateDto.builder().id("1").build();
    String inputJson = objectMapper.writeValueAsString(input);
    when(service.update(input)).thenReturn(request1);

    MvcResult response = mockMvc.perform(put("/api/request")
      .contentType(MediaType.APPLICATION_JSON).content(inputJson)
      .with(csrf())
    ).andExpect(status().isOk()).andReturn();
    String responseJson = response.getResponse().getContentAsString();
    Requests actual = objectMapper.readValue(responseJson, Requests.class);

    assertEquals(request1, actual);
    verify(service).update(input);
  }

  @Test
  void addPayment_passRightArgumentsToService() throws Exception {
    when(service.addPayment("1", 100.0)).thenReturn("Success");

    MvcResult response = mockMvc.perform(patch("/api/request/update_pay")
      .param("id", "1")
      .param("amount", "100.0")
      .with(csrf())
    ).andExpect(status().isOk()).andReturn();
    
    assertEquals("Success", response.getResponse().getContentAsString());
    verify(service).addPayment("1", 100.0);
  }

  @Test
  void cancel_passRightArgsToService() throws Exception {
    when(service.cancelRequest("1")).thenReturn("Success");

    MvcResult response = mockMvc.perform(patch("/api/request/cancel/1").with(csrf())).andExpect(status().isOk()).andReturn();

    assertEquals("Success", response.getResponse().getContentAsString());
    verify(service).cancelRequest("1");
  }
}
