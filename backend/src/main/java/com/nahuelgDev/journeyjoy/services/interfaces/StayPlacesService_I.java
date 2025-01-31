package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;

public interface StayPlacesService_I {
  List<StayPlacesDto> getAll();
  StayPlacesDto getById(String id);
  List<StayPlacesDto> searchByNameAndFrom(String name, String from);
  StayPlacesDto create(StayPlacesDto placeToCreate);
  StayPlacesDto update(StayPlacesDto updatedPlace);
  String delete(String id);
}
