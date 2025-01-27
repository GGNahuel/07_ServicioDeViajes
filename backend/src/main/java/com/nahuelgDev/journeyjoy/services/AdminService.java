package com.nahuelgDev.journeyjoy.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nahuelgDev.journeyjoy.collections.Admin;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.repositories.AdminRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminService implements UserDetailsService {
  @Autowired AdminRepository adminRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Admin user = adminRepo.findByUsername(username).orElseThrow(
      () -> new DocumentNotFoundException("usuario-admin", username, "nombre de usuario")
    );

    List<GrantedAuthority> permissions = new ArrayList<GrantedAuthority>();

    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession session = attr.getRequest().getSession();
    session.setAttribute("loggedUser", user);

    return new User(user.getUsername(), user.getPassword(), permissions);
  }
}
