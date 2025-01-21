package com.nahuelgDev.journeyjoy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.services.TravelsService;

@RestController
@RequestMapping("/api/travels")
public class TravelsController {
  @Autowired TravelsService travelsService;

  @GetMapping("")
  public List<Travels> getAll() {
    return travelsService.getAll();
  }

  @PostMapping("")
  public Travels create(@RequestBody Travels travel) {
    return travelsService.create(travel);
  }

  @PutMapping("")
  public Travels update(@RequestBody Travels updatedTravel) {
    return travelsService.update(updatedTravel);
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable String id) {
    return travelsService.delete(id);
  }
}
