package com.nahuelgDev.journeyjoy.test_services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nahuelgDev.journeyjoy.collections.Admin;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.repositories.AdminRepository;
import com.nahuelgDev.journeyjoy.services.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class Test_AdminService {
  
  @Mock
  private AdminRepository adminRepo;

  @InjectMocks
  private AdminService adminService;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpSession session;

  @Test
  void givenValidUsername_whenLoadUserByUsername_thenReturnUserDetails() {
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    when(request.getSession()).thenReturn(session);

    Admin admin = new Admin();
    admin.setUsername("adminUser");
    admin.setPassword("securePassword");

    when(adminRepo.findByUsername("adminUser")).thenReturn(Optional.of(admin));

    UserDetails userDetails = adminService.loadUserByUsername("adminUser");

    assertEquals("adminUser", userDetails.getUsername());
    assertEquals("securePassword", userDetails.getPassword());
    verify(session).setAttribute("loggedUser", admin);
  }

  @Test
  void loadUserByUsername_throwsException() {
    when(adminRepo.findByUsername("notFoundUser")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> adminService.loadUserByUsername("notFoundUser"));
    verify(session, times(0)).setAttribute(anyString(), any(Admin.class));
  }
}
