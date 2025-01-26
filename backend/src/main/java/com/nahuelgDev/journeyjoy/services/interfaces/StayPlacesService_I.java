package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;

public interface StayPlacesService_I {
  List<StayPlacesDto> getAll();
  StayPlacesDto getById(String id);
  StayPlacesDto create(StayPlacesDto placeToCreate);
  StayPlacesDto update(StayPlacesDto updatedPlace);
  String delete(String id);
}
