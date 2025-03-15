package com.nahuelgDev.journeyjoy.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;
import com.nahuelgDev.journeyjoy.services.interfaces.StayPlacesService_I;


@RestController
@RequestMapping("/api/stayplaces")
public class StayPlacesController {
  private final StayPlacesService_I stayPlaceService;

  public StayPlacesController(StayPlacesService_I stayPlaceService) {
    this.stayPlaceService = stayPlaceService;
  }

  @GetMapping("")
  public List<StayPlacesDto> getAll() {
    return stayPlaceService.getAll();
  }

  @GetMapping("/search")
  public List<StayPlacesDto> searchByNameAndFrom(
    @RequestParam(required = false, defaultValue = "") String name, 
    @RequestParam(required = false, defaultValue = "") String from
  ) throws Exception {
    return stayPlaceService.searchByNameAndFrom(name, from);
  }
  

  @PostMapping("")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<StayPlacesDto> create(@RequestBody StayPlacesDto stayPlace) throws Exception {
    return new ResponseEntity<>(stayPlaceService.create(stayPlace), HttpStatus.CREATED);
  }

  @PutMapping("")
  @PreAuthorize("isAuthenticated()")
  public StayPlacesDto update(@RequestBody StayPlacesDto updatedPlace) throws Exception {
    return stayPlaceService.update(updatedPlace);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public String delete(@PathVariable String id) {
    return stayPlaceService.delete(id);
  }
}
