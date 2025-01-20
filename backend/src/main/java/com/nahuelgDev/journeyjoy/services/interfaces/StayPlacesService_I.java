package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;

public interface StayPlacesService_I {
  public List<StayPlaces> getAll();
  public StayPlaces getById(String id);
  public StayPlaces create(StayPlaces placeToCreate);
  public StayPlaces update(StayPlaces updatedPlace);
  public String delete(String id);
}
