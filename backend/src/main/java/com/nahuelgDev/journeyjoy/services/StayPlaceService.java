package com.nahuelgDev.journeyjoy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.repositories.StayPlacesRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.StayPlacesService_I;

@Service
public class StayPlaceService implements StayPlacesService_I {
  @Autowired StayPlacesRepository stayPlaceRepo;

  public List<StayPlaces> getAll() {
    return stayPlaceRepo.findAll();
  }

  public StayPlaces getById(String id) {
    return stayPlaceRepo.findById(id).orElse(null);
  }

  public StayPlaces create(StayPlaces placeToCreate) {
    return stayPlaceRepo.save(placeToCreate);
  }

  public StayPlaces update(StayPlaces updatedPlace) {
    StayPlaces placeToUpdate = stayPlaceRepo.findById(updatedPlace.getId()).orElse(null);

    if (placeToUpdate != null) {
      updatedPlace.setId(placeToUpdate.getId());

      return stayPlaceRepo.save(updatedPlace);
    }

    return null;
  }

  public String delete(String id) {
    StayPlaces placeToDelete = stayPlaceRepo.findById(id).orElse(null);

    if (placeToDelete != null) {
      stayPlaceRepo.deleteById(id);

      return "Operación realizada con éxito";
    }

    return "No se pudo completar la operación";
  }
}
