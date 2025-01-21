package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;

public interface StayPlacesService_I {
  public List<StayPlacesDto> getAll();
  public StayPlacesDto getById(String id);
  public StayPlacesDto create(StayPlacesDto placeToCreate);
  public StayPlacesDto update(StayPlacesDto updatedPlace);
  public String delete(String id);
}
