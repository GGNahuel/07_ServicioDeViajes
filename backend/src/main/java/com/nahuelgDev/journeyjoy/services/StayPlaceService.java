package com.nahuelgDev.journeyjoy.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;
import com.nahuelgDev.journeyjoy.repositories.StayPlacesRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.StayPlacesService_I;

@Service
public class StayPlaceService implements StayPlacesService_I {
  @Autowired StayPlacesRepository stayPlaceRepo;
  @Autowired ModelMapper modelMapper;

  public List<StayPlacesDto> getAll() {
    return stayPlaceRepo.findAll().stream().map(
      stayPlace -> modelMapper.map(stayPlace, StayPlacesDto.class)
    ).toList();
  }

  public StayPlacesDto getById(String id) {
    StayPlaces result = stayPlaceRepo.findById(id).orElse(null);

    return result != null ? modelMapper.map(result, StayPlacesDto.class) : null;
  }

  public StayPlacesDto create(StayPlacesDto placeToCreate) {
    return modelMapper.map(stayPlaceRepo.save(
      modelMapper.map(placeToCreate, StayPlaces.class)
    ), StayPlacesDto.class);
  }

  public StayPlacesDto update(StayPlacesDto updatedPlace) {
    StayPlaces placeToUpdate = stayPlaceRepo.findById(updatedPlace.getId()).orElse(null);

    if (placeToUpdate != null) {
      updatedPlace.setId(placeToUpdate.getId());

      return modelMapper.map(stayPlaceRepo.save(
        modelMapper.map(updatedPlace, StayPlaces.class)
      ), StayPlacesDto.class);
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
