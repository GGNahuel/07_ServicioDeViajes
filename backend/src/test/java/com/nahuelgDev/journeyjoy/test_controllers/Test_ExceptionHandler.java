package com.nahuelgDev.journeyjoy.test_controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.nahuelgDev.journeyjoy.configurations.SecurityConfig;
import com.nahuelgDev.journeyjoy.controllers.StayPlacesController;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.exceptions.EmptyFieldException;
import com.nahuelgDev.journeyjoy.exceptions.InvalidFieldValueException;
import com.nahuelgDev.journeyjoy.exceptions.InvalidOperationException;
import com.nahuelgDev.journeyjoy.services.interfaces.StayPlacesService_I;

@WebMvcTest(StayPlacesController.class)
@Import(SecurityConfig.class)
public class Test_ExceptionHandler {
  
  @Autowired MockMvc mockMvc;

  @SuppressWarnings("removal")
  @MockBean StayPlacesService_I serviceExample;

  @Test
  void documentNotFoundExcpetion_returns404statusCode() throws Exception {
    when(serviceExample.getAll()).thenThrow(DocumentNotFoundException.class);

    mockMvc.perform(get("/api/stayplaces")).andExpect(status().isNotFound());
  }

  @Test
  void emptyFieldException_returns406statusCode() throws Exception {
    when(serviceExample.getAll()).thenThrow(EmptyFieldException.class);

    mockMvc.perform(get("/api/stayplaces")).andExpect(status().isNotAcceptable());
  }

  @Test
  void invalidOperationException_returns409statusCode() throws Exception {
    when(serviceExample.getAll()).thenThrow(InvalidOperationException.class);

    mockMvc.perform(get("/api/stayplaces")).andExpect(status().isConflict());
  }

  @Test
  void invalidFieldValueExcpetion_returns404statusCode() throws Exception {
    when(serviceExample.getAll()).thenThrow(InvalidFieldValueException.class);

    mockMvc.perform(get("/api/stayplaces")).andExpect(status().isNotAcceptable());
  }

  @Test
  void otherExceptions_returns500statusCode() throws Exception {
    when(serviceExample.getAll()).thenAnswer(invocation -> {
      throw new Exception("Error genérico");
    });

    mockMvc.perform(get("/api/stayplaces")).andExpect(status().isInternalServerError());
  }
}
