package com.nahuelgDev.journeyjoy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuelgDev.journeyjoy.collections.Admin;
import com.nahuelgDev.journeyjoy.services.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserController {
  private final AdminService adminService;

  public UserController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/csrf-token")
  public CsrfToken csrf(HttpServletRequest servlet) {
    CsrfToken token = (CsrfToken) servlet.getAttribute("_csrf");
    System.out.println("CSRF Token: " + token.getToken());
    return token;
  }

  @GetMapping("/session")
  public ResponseEntity<Admin> session(HttpSession session) throws Exception {
    Admin user = (Admin) session.getAttribute("loggedUser");
    if (user == null) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}
