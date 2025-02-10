package com.nahuelgDev.journeyjoy.test_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nahuelgDev.journeyjoy.configurations.SecurityConfig;
import com.nahuelgDev.journeyjoy.controllers.StayPlacesController;
import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;
import com.nahuelgDev.journeyjoy.services.interfaces.StayPlacesService_I;

@WebMvcTest(StayPlacesController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import(SecurityConfig.class)
public class Test_StayPlacesController {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  @Autowired ApplicationContext applicationContext;

  @SuppressWarnings("removal")
  @MockBean StayPlacesService_I service;

  @Test
  void securityConfigIsActive() {
    assertNotNull(applicationContext.getBean(SecurityFilterChain.class));
  }

  @Test
  void getAll_returnsExpectedList() throws Exception {
    // setting 
    List<StayPlacesDto> expectedResponse = List.of(
      StayPlacesDto.builder().id("1").from("Place 1").name("StayPlace 1").build(),
      StayPlacesDto.builder().id("2").from("Place 2").name("StayPlace 2").build()
    );
    when(service.getAll()).thenReturn(expectedResponse);

    // running
    MvcResult response = mockMvc.perform(get("/api/stayplaces"))
      .andExpect(status().isOk()).andReturn();

    // transforming response
    String responseInJson = response.getResponse().getContentAsString();
    List<StayPlacesDto> actualResponse = objectMapper.readValue(responseInJson, new TypeReference<List<StayPlacesDto>>() {});

    // verifying
    assertNotNull(actualResponse);
    assertIterableEquals(expectedResponse, actualResponse);
    verify(service, times(1)).getAll();
  }

  @Test
  void searchByNameAndFrom_returnsDtoList() throws Exception {
    List<StayPlacesDto> expected = List.of(
      StayPlacesDto.builder().id("1").from("Place 1").name("StayPlace 1").build(),
      StayPlacesDto.builder().id("2").from("Place 2").name("StayPlace 2").build()
    );
    when(service.searchByNameAndFrom("place", "")).thenReturn(expected);

    MvcResult response = mockMvc.perform(
      get("/api/stayplaces/search").param("name", "place").param("from", "")
    ).andExpect(status().isOk()).andReturn();

    String responseInJson = response.getResponse().getContentAsString();
    List<StayPlacesDto> actual = objectMapper.readValue(responseInJson, new TypeReference<List<StayPlacesDto>>() {});

    assertIterableEquals(expected, actual);
    verify(service).searchByNameAndFrom("place", "");
  }

  @Test
  @WithMockUser
  void create_returnsStayPlaceDtoWithId() throws Exception {
    StayPlacesDto input = StayPlacesDto.builder().from("from").name("name").build();
    String inputInJson = objectMapper.writeValueAsString(input);
    StayPlacesDto expected = StayPlacesDto.builder().id("1").from("from").name("name").build();
    when(service.create(input)).thenReturn(expected);

    MvcResult response = mockMvc.perform(post("/api/stayplaces")
      .contentType(MediaType.APPLICATION_JSON)
      .content(inputInJson))
      .andExpect(status().isOk()).andReturn();

    String responseInJson = response.getResponse().getContentAsString();
    StayPlacesDto actual = objectMapper.readValue(responseInJson, StayPlacesDto.class);

    assertEquals(expected, actual);
  }

  @Test
  void create_shouldDenyAccess() throws Exception {
    StayPlacesDto input = StayPlacesDto.builder().from("from").name("name").build();
    String inputInJson = objectMapper.writeValueAsString(input);

    mockMvc.perform(post("/api/stayplaces")
      .contentType(MediaType.APPLICATION_JSON)
      .content(inputInJson)
    ).andExpect(status().isUnauthorized());

    verify(service, times(0)).create(input);
  }

  @Test
  @WithMockUser
  void update_returnsAStayPlaceDto() throws Exception {
    StayPlacesDto input = new StayPlacesDto();
    String inputInJson = objectMapper.writeValueAsString(input);
    when(service.update(input)).thenReturn(input);

    MvcResult response = mockMvc.perform(put("/api/stayplaces")
      .contentType(MediaType.APPLICATION_JSON)
      .content(inputInJson))
      .andExpect(status().isOk()).andReturn();

    String responseInJson = response.getResponse().getContentAsString();
    StayPlacesDto actual = objectMapper.readValue(responseInJson, StayPlacesDto.class);

    assertEquals(input, actual);
  }

  @Test
  void update_shouldDenyAccess() throws Exception {
    StayPlacesDto input = new StayPlacesDto();
    String inputInJson = objectMapper.writeValueAsString(input);

    mockMvc.perform(put("/api/stayplaces")
      .contentType(MediaType.APPLICATION_JSON)
      .content(inputInJson)
    ).andExpect(status().isUnauthorized());

    verify(service, times(0)).update(input);
  }

  @Test
  @WithMockUser
  void delete_returnString() throws Exception {
    when(service.delete("1")).thenReturn("Operación realizada con éxito");

    mockMvc.perform(delete("/api/stayplaces/1")).andExpect(status().isOk());
  }

  @Test
  void delete_shouldDenyAccess() throws Exception {
    mockMvc.perform(delete("/api/stayplaces/1")).andExpect(status().isUnauthorized());

    verify(service, times(0)).delete("1");
  }
}
