package com.nahuelgDev.journeyjoy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.services.StayPlaceService;

@RestController
@RequestMapping("/api/stayplaces")
public class StayPlacesController {
  @Autowired StayPlaceService stayPlaceService;

  @GetMapping("")
  public List<StayPlaces> getAll() {
    return stayPlaceService.getAll();
  }

  @PostMapping("")
  public StayPlaces create(@RequestBody StayPlaces stayPlace) {
    return stayPlaceService.create(stayPlace);
  }
}
