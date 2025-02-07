package com.nahuelgDev.journeyjoy.test_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.configurations.SecurityConfig;
import com.nahuelgDev.journeyjoy.controllers.ReviewsController;
import com.nahuelgDev.journeyjoy.services.interfaces.ReviewsService_I;

@WebMvcTest(ReviewsController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import(SecurityConfig.class)
public class Test_ReviewsController {
  
  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  @SuppressWarnings("removal")
  @MockBean ReviewsService_I service;

  @Test
  void getAll_returnsExpectedList() throws Exception {
    List<Reviews> expected = List.of(
      Reviews.builder().id("1").userName("juan").build(),
      Reviews.builder().id("2").userName("camila").build()
    );
    when(service.getAll()).thenReturn(expected);

    MvcResult request = mockMvc.perform(get("/api/reviews"))
      .andExpect(status().isOk()).andReturn();

    String responseInJson = request.getResponse().getContentAsString();
    List<Reviews> actual = objectMapper.readValue(responseInJson, new TypeReference<List<Reviews>>() {});

    assertIterableEquals(expected, actual);
    assertEquals(2, actual.size());
    verify(service).getAll();
  }

  @Test
  void create_returnsReview() throws IOException, Exception {
    Reviews input = Reviews.builder().id("1").userName("fio").rating(4.5).build();
    MockMultipartFile file = new MockMultipartFile(
      "file", 
      "test.jpg", 
      MediaType.IMAGE_JPEG_VALUE, 
      "contenido de prueba".getBytes()
    );
    MockMultipartFile reviewPart = new MockMultipartFile(
      "review",
      "",
      MediaType.APPLICATION_JSON_VALUE,
      objectMapper.writeValueAsBytes(input)
    );

    when(service.create(input, file)).thenReturn(input);

    MvcResult request = mockMvc.perform(
      multipart("/api/reviews/add")
        .file(file)
        .file(reviewPart) 
        .contentType(MediaType.MULTIPART_FORM_DATA)
    ).andExpect(status().isOk()).andReturn();

    String response = request.getResponse().getContentAsString();
    Reviews actual = objectMapper.readValue(response, Reviews.class);
    
    assertEquals(input, actual);
    verify(service).create(input, file);
  }

  @Test
  void update_returnsReview() throws IOException, Exception {
    Reviews input = Reviews.builder().id("1").userName("fio").rating(4.5).build();
    MockMultipartFile file = new MockMultipartFile(
      "file", 
      "test.jpg", 
      MediaType.IMAGE_JPEG_VALUE, 
      "contenido de prueba".getBytes()
    );
    MockMultipartFile reviewPart = new MockMultipartFile(
      "review",
      "",
      MediaType.APPLICATION_JSON_VALUE,
      objectMapper.writeValueAsBytes(input)
    );

    when(service.update(input, file)).thenReturn(input);

    MvcResult request = mockMvc.perform(
      multipart(HttpMethod.PUT, "/api/reviews/update")
        .file(file)
        .file(reviewPart) 
        .contentType(MediaType.MULTIPART_FORM_DATA)
    ).andExpect(status().isOk()).andReturn();

    String response = request.getResponse().getContentAsString();
    Reviews actual = objectMapper.readValue(response, Reviews.class);
    
    assertEquals(input, actual);
    verify(service).update(any(Reviews.class), any(MultipartFile.class));
  }

  @Test
  @WithMockUser
  void delete_returnsExpected() throws Exception {
    String expected = "Operación realizada con éxito";
    when(service.delete("1")).thenReturn(expected);

    MvcResult request = mockMvc.perform(delete("/api/reviews/1")).andExpect(status().isOk()).andReturn();
    String actual = request.getResponse().getContentAsString();

    assertEquals(expected, actual);
    verify(service).delete("1");
  }

  @Test
  void delete_shouldDenyAccess() throws Exception {
    mockMvc.perform(delete("/api/reviews/1")).andExpect(status().isUnauthorized());
    verify(service, times(0)).delete("1");
  }
}