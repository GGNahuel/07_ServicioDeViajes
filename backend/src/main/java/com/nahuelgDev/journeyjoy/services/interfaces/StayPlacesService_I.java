package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;

public interface StayPlacesService_I {
  List<StayPlacesDto> getAll();
  StayPlacesDto getById(String id);
  List<StayPlacesDto> searchByNameAndFrom(String name, String from) throws Exception;
  StayPlacesDto create(StayPlacesDto placeToCreate) throws Exception;
  StayPlacesDto update(StayPlacesDto updatedPlace) throws Exception;
  String delete(String id);
}
