package com.nahuelgDev.journeyjoy.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nahuelgDev.journeyjoy.utilities.Generators;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  @Bean
  SecurityFilterChain setFilterChainProps(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .authorizeHttpRequests(request -> request
        .anyRequest().permitAll()
      )
      .formLogin(form -> form
        .loginProcessingUrl("/logincheck")
        .usernameParameter("username")
        .passwordParameter("password")
        .successHandler((request, response, auth) -> {
          response.setStatus(HttpServletResponse.SC_OK);
        })
        .failureHandler((request, response, authEx) -> {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // cambiar status
        })
        .permitAll()
      )
      .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessHandler((request, response, auth) -> {
          response.setStatus(HttpServletResponse.SC_OK);
        })
        .permitAll()
      )
      .csrf(csrf -> csrf.disable())
      .rememberMe(remember -> remember
        .key(Generators.generateKey(16))
        .tokenValiditySeconds(60 * 60)
      )
      .exceptionHandling(exceptionHandling -> exceptionHandling
        .authenticationEntryPoint((request, response, authEx) -> {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        })
      );

    return httpSecurity.build();
  }

  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
