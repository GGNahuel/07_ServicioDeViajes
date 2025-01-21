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

import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;
import com.nahuelgDev.journeyjoy.services.StayPlaceService;

@RestController
@RequestMapping("/api/stayplaces")
public class StayPlacesController {
  @Autowired StayPlaceService stayPlaceService;

  @GetMapping("")
  public List<StayPlacesDto> getAll() {
    return stayPlaceService.getAll();
  }

  @PostMapping("")
  public StayPlacesDto create(@RequestBody StayPlacesDto stayPlace) {
    return stayPlaceService.create(stayPlace);
  }

  @PutMapping("")
  public StayPlacesDto update(@RequestBody StayPlacesDto updatedPlace) {
    return stayPlaceService.update(updatedPlace);
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable String id) {
    return stayPlaceService.delete(id);
  }
}
