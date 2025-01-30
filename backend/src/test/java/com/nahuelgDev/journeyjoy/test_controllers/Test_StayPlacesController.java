package com.nahuelgDev.journeyjoy.test_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.nahuelgDev.journeyjoy.services.StayPlaceService;

@WebMvcTest(StayPlacesController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import(SecurityConfig.class)
public class Test_StayPlacesController {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  @Autowired ApplicationContext applicationContext;

  @SuppressWarnings("removal")
  @MockBean StayPlaceService service;

  @Test
  @WithMockUser
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
    assertEquals(expectedResponse.size(), actualResponse.size());
    assertTrue(actualResponse.stream().allMatch(element -> element instanceof StayPlacesDto));
    assertIterableEquals(expectedResponse, actualResponse);
    verify(service, times(1)).getAll();
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
}

/* 
  @Test
  @WithMockUser
  void update_ReturnsUpdatedStayPlace_WhenAuthenticated() throws Exception {
    // Mockear la entrada y salida
    StayPlacesDto input = new StayPlacesDto("3", "Updated Place");
    StayPlacesDto mockResponse = new StayPlacesDto("3", "Updated Place");
    Mockito.when(stayPlaceService.update(Mockito.any(StayPlacesDto.class))).thenReturn(mockResponse);

    // Ejecutar la solicitud y verificar resultados
    mockMvc.perform(put("/api/stayplaces")
          .contentType(MediaType.APPLICATION_JSON)
          .content(new ObjectMapper().writeValueAsString(input)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value("3"))
      .andExpect(jsonPath("$.name").value("Updated Place"));
  }

  @Test
  @WithMockUser
  void delete_ReturnsSuccessMessage_WhenAuthenticated() throws Exception {
    // Mockear la respuesta del servicio
    String mockResponse = "Deleted Successfully";
    Mockito.when(stayPlaceService.delete("3")).thenReturn(mockResponse);

    // Ejecutar la solicitud y verificar resultados
    mockMvc.perform(delete("/api/stayplaces/3"))
      .andExpect(status().isOk())
      .andExpect(content().string("Deleted Successfully"));
  }
 */