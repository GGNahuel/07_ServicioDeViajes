package com.nahuelgDev.journeyjoy.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.services.interfaces.TravelsService_I;

@RestController
@RequestMapping("/api/travels")
public class TravelsController {
  private final TravelsService_I travelsService;

  public TravelsController(TravelsService_I travelsService) {
    this.travelsService = travelsService;
  }

  @GetMapping("")
  public List<Travels> getAll() {
    return travelsService.getAll();
  }

  @GetMapping("/search")
  public List<Travels> search(
    @RequestParam(required = false) Boolean available, @RequestParam(required = false) Integer desiredCapacity, 
    @RequestParam(required = false) String place, 
    @RequestParam(required = false) Integer minDays, @RequestParam(required = false) Integer maxDays
  ) throws Exception {
    return travelsService.search(available, desiredCapacity, place, minDays, maxDays);
  }

  @GetMapping("/capacity")
  public List<Travels> getByCapacityLeft(@RequestParam boolean wantCapacity) {
    return travelsService.getByCapacityLeft(wantCapacity);
  }

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Travels> create(@RequestPart("body") Travels travel, @RequestPart("images") MultipartFile[] images) throws Exception {
    return new ResponseEntity<>(travelsService.create(travel, images), HttpStatus.CREATED);
  }

  @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public Travels update(@RequestPart("body") Travels updatedTravel, @RequestPart("images") MultipartFile[] images) throws Exception {
    return travelsService.update(updatedTravel, images);
  }

  @PatchMapping("/addReview")
  public String addReview(@RequestParam String travelId, @RequestBody Reviews newReview) throws Exception {
    return travelsService.addReview(travelId, newReview);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public String delete(@PathVariable String id) {
    return travelsService.delete(id);
  }
}
