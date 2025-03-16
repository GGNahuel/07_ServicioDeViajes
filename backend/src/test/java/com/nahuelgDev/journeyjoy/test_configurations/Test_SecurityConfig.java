package com.nahuelgDev.journeyjoy.test_configurations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

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
  void passwordIsEncoded() {
    String rawPassword = "mySecret";
    String encodedPassword = passwordEncoder.encode(rawPassword);
    
    assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
  }

  @Test
  void login_success() throws Exception {
    Admin admin = new Admin();
    admin.setUsername("adminUser");
    admin.setPassword(new BCryptPasswordEncoder().encode("password"));

    when(adminRepo.findByUsername("adminUser")).thenReturn(Optional.of(admin));
    when(adminService.loadUserByUsername("adminUser")).thenReturn(new User(admin.getUsername(), admin.getPassword(), new ArrayList<>()));

    mockMvc.perform(post("/logincheck")
      .param("username", "adminUser")
      .param("password", "password")
      .with(csrf())
    ).andExpect(status().isOk());
  }

  @Test
  void login_userNotFound() throws Exception {
    when(adminRepo.findByUsername("invalidUser")).thenReturn(Optional.empty());
    when(adminService.loadUserByUsername("invalidUser")).thenThrow(UsernameNotFoundException.class);

    mockMvc.perform(post("/logincheck")
      .param("username", "invalidUser")
      .param("password", "password")
      .with(csrf())
    ).andExpect(status().isBadRequest());
  }

  @Test
  void logout_return200() throws Exception {
    mockMvc.perform(post("/logout")
      .with(csrf())
    ).andExpect(status().isOk());
  }

  @Test
  void permitAnyRoute() throws Exception {
    mockMvc.perform(get("/api/travels")).andExpect(status().isOk());
  }
}