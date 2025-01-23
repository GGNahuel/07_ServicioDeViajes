package com.nahuelgDev.journeyjoy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.nahuelgDev.journeyjoy.services.RequestsService;

@RestController
@RequestMapping("/api/request")
public class RequestsController {
  @Autowired RequestsService requestsService;

  @GetMapping("")
  public List<Requests> getAll() {
    return requestsService.getAll();
  }

  @GetMapping("/{id}")
  public Requests getById(@PathVariable String id) {
    return requestsService.getById(id);
  }

  @GetMapping("/travel")
  public List<Requests> getByTravelName(@RequestParam String name) {
    return requestsService.getByTravelName(name);
  }

  @PostMapping("")
  public Requests create(@RequestBody Requests requestToCreate) {
    return requestsService.create(requestToCreate);
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
