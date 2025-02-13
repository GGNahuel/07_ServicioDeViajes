package com.nahuelgDev.journeyjoy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.dtos.RequestsUpdateDto;
import com.nahuelgDev.journeyjoy.services.interfaces.RequestsService_I;

@RestController
@RequestMapping("/api/request")
public class RequestsController {

  @Autowired RequestsService_I requestsService;

  @GetMapping("")
  @PreAuthorize("isAuthenticated()")
  public List<Requests> getAll() {
    return requestsService.getAll();
  }

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public Requests getById(@PathVariable String id) {
    return requestsService.getById(id);
  }

  @GetMapping("/travel")
  @PreAuthorize("isAuthenticated()")
  public List<Requests> getByTravelId(@RequestParam String id) {
    return requestsService.getByTravelId(id);
  }

  @GetMapping("/email")
  public List<Requests> getByEmail(@RequestParam String email) {
    return requestsService.getByEmail(email);
  }

  @PostMapping("")
  public ResponseEntity<Requests> create(@RequestBody Requests requestToCreate) {
    return new ResponseEntity<>(requestsService.create(requestToCreate), HttpStatus.CREATED);
  }

  @PutMapping("")
  public Requests update(@RequestBody RequestsUpdateDto updatedRequest) {
    return requestsService.update(updatedRequest);
  }

  @PatchMapping("/update_pay")
  public String addPayment(@RequestParam String id, @RequestParam Double amount) {
    return requestsService.addPayment(id, amount);
  }

  @PatchMapping("/cancel/{id}")
  public String cancel(@PathVariable String id) {
    return requestsService.cancelRequest(id);
  }
}
