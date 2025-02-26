package com.nahuelgDev.journeyjoy.test_configurations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nahuelgDev.journeyjoy.collections.Admin;
import com.nahuelgDev.journeyjoy.configurations.SecurityConfig;
import com.nahuelgDev.journeyjoy.repositories.AdminRepository;
import com.nahuelgDev.journeyjoy.services.AdminService;

@SpringBootTest
@AutoConfigureMockMvc
@Import({ SecurityConfig.class, AdminService.class }) // Importamos la seguridad y UserDetailsService
public class Test_SecurityConfig {

  @Autowired MockMvc mockMvc;
  @Autowired BCryptPasswordEncoder passwordEncoder;

  @Mock AdminRepository adminRepo;

  @SuppressWarnings("removal")
  @MockBean AdminService adminService;
  
  @Test
  void givenSecurityConfig_whenPasswordEncoder_thenBCryptIsUsed() {
    String rawPassword = "mySecret";
    String encodedPassword = passwordEncoder.encode(rawPassword);
    
    assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
  }

  @Test
  void givenValidUser_whenLogin_thenReturn200() throws Exception {
    Admin admin = new Admin();
    admin.setUsername("adminUser");
    admin.setPassword(new BCryptPasswordEncoder().encode("securePassword"));

    when(adminRepo.findByUsername("adminUser")).thenReturn(Optional.of(admin));
    when(adminService.loadUserByUsername("adminUser")).thenAnswer(invocation -> {
      MockHttpServletRequest request = new MockHttpServletRequest();
      RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
      
      return new User(admin.getUsername(), admin.getPassword(), new ArrayList<>());
    });

    mockMvc.perform(post("/logincheck")
      .param("username", "adminUser")
      .param("password", "securePassword")
    ).andExpect(status().isOk());
  }

  @Test
  void givenInvalidUser_whenLogin_thenReturn401() throws Exception {
    when(adminRepo.findByUsername("invalidUser")).thenReturn(Optional.empty());

    mockMvc.perform(post("/logincheck")
      .param("username", "adminUser")
      .param("password", "securePassword")
    ).andExpect(status().isUnauthorized());
  }

  @Test
  void givenSecurityConfig_whenLogout_thenReturn200() throws Exception {
    mockMvc.perform(post("/logout"))
        .andExpect(status().isOk());
  }

  @Test
  void givenSecurityConfig_whenAccessingAnyRoute_thenPermitAll() throws Exception {
    mockMvc.perform(get("/any-route"))
        .andExpect(status().isOk());
  }

}