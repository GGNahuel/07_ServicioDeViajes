package com.nahuelgDev.journeyjoy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;
import com.nahuelgDev.journeyjoy.services.interfaces.StayPlacesService_I;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/stayplaces")
public class StayPlacesController {
  @Autowired StayPlacesService_I stayPlaceService;

  @GetMapping("")
  public List<StayPlacesDto> getAll() {
    return stayPlaceService.getAll();
  }

  @GetMapping("/search")
  public List<StayPlacesDto> searchByNameAndFrom(
    @RequestParam(required = false, defaultValue = "") String name, 
    @RequestParam(required = false, defaultValue = "") String from
  ) {
    return stayPlaceService.searchByNameAndFrom(name, from);
  }
  

  @PostMapping("")
  @PreAuthorize("isAuthenticated()")
  public StayPlacesDto create(@RequestBody StayPlacesDto stayPlace) {
    return stayPlaceService.create(stayPlace);
  }

  @PutMapping("")
  @PreAuthorize("isAuthenticated()")
  public StayPlacesDto update(@RequestBody StayPlacesDto updatedPlace) {
    return stayPlaceService.update(updatedPlace);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public String delete(@PathVariable String id) {
    return stayPlaceService.delete(id);
  }
}
