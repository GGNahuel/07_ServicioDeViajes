package com.nahuelgDev.journeyjoy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nahuelgDev.journeyjoy.collections.Reviews;
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

  @GetMapping("/search")
  public List<Travels> search(
    @RequestParam(required = false) Boolean available, @RequestParam(required = false) Integer desiredCapacity, 
    @RequestParam(required = false) String place, 
    @RequestParam(required = false) Integer minDays, @RequestParam(required = false) Integer maxDays
  ) {
    return travelsService.search(available, desiredCapacity, place, minDays, maxDays);
  }

  @GetMapping("/capacity")
  public List<Travels> getByCapacityLeft(@RequestParam boolean wantCapacity) {
    return travelsService.getByCapacityLeft(wantCapacity);
  }

  @PostMapping("")
  // @PreAuthorize("authenticated()")
  public Travels create(@RequestBody Travels travel) {
    return travelsService.create(travel);
  }

  @PutMapping("")
  // @PreAuthorize("authenticated()")
  public Travels update(@RequestBody Travels updatedTravel) {
    return travelsService.update(updatedTravel);
  }

  @PatchMapping("/addReview")
  public String addReview(@RequestParam String travelId, @RequestParam Reviews newReview) {
    return travelsService.addReview(travelId, newReview);
  }

  @DeleteMapping("/{id}")
  // @PreAuthorize("authenticated()")
  public String delete(@PathVariable String id) {
    return travelsService.delete(id);
  }
}
