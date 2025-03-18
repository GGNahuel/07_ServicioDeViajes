package com.nahuelgDev.journeyjoy.test_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.configurations.SecurityConfig;
import com.nahuelgDev.journeyjoy.controllers.TravelsController;
import com.nahuelgDev.journeyjoy.services.interfaces.TravelsService_I;

@WebMvcTest(TravelsController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import(SecurityConfig.class)
public class Test_TravelsController {
  
  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  @SuppressWarnings("removal")
  @MockBean TravelsService_I service;

  private Travels travel1, travel2;

  @BeforeEach
  void setUp() {
    travel1 = Travels.builder()
      .id("1").name("Viaje 1").longInDays(21)
      .maxCapacity(30).currentCapacity(18)
    .build();
    travel2 = Travels.builder()
      .id("2").name("Viaje 2").longInDays(15)
      .maxCapacity(20).currentCapacity(18)
    .build();
  }

  @Test
  void getAll_returnsExpectedList() throws Exception {
    List<Travels> expected = List.of(travel1, travel2);
    when(service.getAll()).thenReturn(expected);
    
    MvcResult response = mockMvc.perform(get("/api/travels")).andExpect(status().isOk()).andReturn();
    String responseString = response.getResponse().getContentAsString();
    List<Travels> actual = objectMapper.readValue(responseString, new TypeReference<List<Travels>>() {});

    assertIterableEquals(expected, actual);
    verify(service).getAll();
  }

  @Test
  void search_passQueryParamsAsExpected() throws Exception {
    List<Travels> expected = List.of(travel1);
    when(service.search(null, 3, null, null, null)).thenReturn(expected);

    MvcResult response = mockMvc.perform(get("/api/travels/search?desiredCapacity=3")).andExpect(status().isOk()).andReturn();
    String responseString = response.getResponse().getContentAsString();
    List<Travels> actual = objectMapper.readValue(responseString, new TypeReference<List<Travels>>() {});

    assertIterableEquals(expected, actual);
    verify(service).search(null, 3, null, null, null);
  }

  @Test
  void getByCapacityLeft_returnsExpectedList() throws Exception {
    List<Travels> expected = List.of(travel1, travel2);
    when(service.getByCapacityLeft(true)).thenReturn(expected);

    MvcResult response = mockMvc.perform(get("/api/travels/capacity?wantCapacity=true")).andExpect(status().isOk()).andReturn();
    String responseString = response.getResponse().getContentAsString();
    List<Travels> actual = objectMapper.readValue(responseString, new TypeReference<List<Travels>>() {});

    assertIterableEquals(expected, actual);
    verify(service).getByCapacityLeft(true);
  }

  @Test
  @WithMockUser
  void create_returnsExpected() throws Exception {
    MockMultipartFile imagePart = new MockMultipartFile(
      "images", "image.jpg", MediaType.IMAGE_JPEG_VALUE ,"bytes".getBytes()
    );
    MockMultipartFile bodyPart = new MockMultipartFile(
      "body", 
      "", 
      MediaType.APPLICATION_JSON_VALUE, 
      objectMapper.writeValueAsBytes(travel1)
    );

    when(service.create(any(Travels.class), any())).thenReturn(travel1);

    MvcResult response = mockMvc.perform(
      multipart("/api/travels")
      .file(bodyPart)
      .file(imagePart)
      .contentType(MediaType.MULTIPART_FORM_DATA)
      .with(csrf())
    ).andExpect(status().isCreated()).andReturn();
    String responseString = response.getResponse().getContentAsString();
    Travels actual = objectMapper.readValue(responseString, Travels.class);

    assertEquals(travel1, actual);
    verify(service).create(any(Travels.class), any());
  }

  @Test
  void create_shouldDenyAccess() throws Exception {
    MockMultipartFile imagePart = new MockMultipartFile(
      "images", "image.jpg", MediaType.IMAGE_JPEG_VALUE ,"bytes".getBytes()
    );
    MockMultipartFile bodyPart = new MockMultipartFile(
      "body", 
      "", 
      MediaType.APPLICATION_JSON_VALUE, 
      objectMapper.writeValueAsBytes(travel1)
    );

    mockMvc.perform(
      multipart("/api/travels")
      .file(bodyPart)
      .file(imagePart)
      .contentType(MediaType.MULTIPART_FORM_DATA)
      .with(csrf())
    ).andExpect(status().isUnauthorized());

    verify(service, times(0)).create(any(Travels.class), any());
  }

  @Test
  @WithMockUser
  void update_returnsExpected() throws Exception {
    MockMultipartFile imagePart = new MockMultipartFile(
      "images", "image.jpg", MediaType.IMAGE_JPEG_VALUE ,"bytes".getBytes()
    );
    MockMultipartFile bodyPart = new MockMultipartFile(
      "body", 
      "", 
      MediaType.APPLICATION_JSON_VALUE, 
      objectMapper.writeValueAsBytes(travel1)
    );
    when(service.update(any(Travels.class), any())).thenReturn(travel1);

    MvcResult response = mockMvc.perform(      
      multipart(HttpMethod.PUT, "/api/travels")
      .file(bodyPart)
      .file(imagePart)
      .contentType(MediaType.MULTIPART_FORM_DATA)
      .with(csrf())
    ).andExpect(status().isOk()).andReturn();
    String responseString = response.getResponse().getContentAsString();
    Travels actual = objectMapper.readValue(responseString, Travels.class);

    assertEquals(travel1, actual);
    verify(service).update(any(Travels.class), any());
  }

  @Test
  void update_shouldDenyAccess() throws Exception {
    MockMultipartFile imagePart = new MockMultipartFile(
      "images", "image.jpg", MediaType.IMAGE_JPEG_VALUE ,"bytes".getBytes()
    );
    MockMultipartFile bodyPart = new MockMultipartFile(
      "body", 
      "", 
      MediaType.APPLICATION_JSON_VALUE, 
      objectMapper.writeValueAsBytes(travel1)
    );

    mockMvc.perform(      
      multipart(HttpMethod.PUT, "/api/travels")
      .file(bodyPart)
      .file(imagePart)
      .contentType(MediaType.MULTIPART_FORM_DATA)
      .with(csrf())
    ).andExpect(status().isUnauthorized());

    verify(service, times(0)).update(any(Travels.class), any());
  }

  @Test
  void addReview_returnsMessage() throws Exception {
    Reviews review = Reviews.builder().userName("author").rating(1.0).build();
    String reviewString = objectMapper.writeValueAsString(review);
    String expected = "asd";
    when(service.addReview("1", review)).thenReturn(expected);

    MvcResult response = mockMvc.perform(
      patch("/api/travels/addReview?travelId=1")
      .contentType(MediaType.APPLICATION_JSON).content(reviewString)
      .with(csrf())
    ).andExpect(status().isOk()).andReturn();
    String actual = response.getResponse().getContentAsString();

    assertEquals(expected, actual);
    verify(service).addReview("1", review);
  }

  @Test
  @WithMockUser
  void delete_returnsString() throws Exception {
    String expected = "asd";
    when(service.delete("1")).thenReturn(expected);

    MvcResult response = mockMvc.perform(delete("/api/travels/1").with(csrf())).andExpect(status().isOk()).andReturn();
    String actual = response.getResponse().getContentAsString();

    assertEquals(expected, actual);
    verify(service).delete("1");
  }

  @Test
  void delete_shouldDenyAccess() throws Exception {
    mockMvc.perform(delete("/api/travels/1").with(csrf())).andExpect(status().isUnauthorized());
    verify(service, times(0)).delete(anyString());
  }
}
