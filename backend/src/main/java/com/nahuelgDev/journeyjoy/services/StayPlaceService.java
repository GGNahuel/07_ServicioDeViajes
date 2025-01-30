package com.nahuelgDev.journeyjoy.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.repositories.StayPlacesRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.StayPlacesService_I;
import com.nahuelgDev.journeyjoy.utilities.Verifications.Field;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.*;

@Service
public class StayPlaceService implements StayPlacesService_I {
  @Autowired StayPlacesRepository stayPlaceRepo;
  @Autowired ModelMapper modelMapper;

  @Override @Transactional(readOnly = true)
  public List<StayPlacesDto> getAll() {
    return stayPlaceRepo.findAll().stream().map(
      stayPlace -> modelMapper.map(stayPlace, StayPlacesDto.class)
    ).toList();
  }

  @Override @Transactional(readOnly = true)
  public StayPlacesDto getById(String id) {
    checkFieldsHasContent(new Field("id", id));

    StayPlaces result = stayPlaceRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("lugar de estadía", id, "id")
    );

    return modelMapper.map(result, StayPlacesDto.class);
  }

  @Override @Transactional
  public StayPlacesDto create(StayPlacesDto placeToCreate) {
    checkFieldsHasContent(
      new Field("lugar", placeToCreate.getFrom()),
      new Field("nombre del establecimiento", placeToCreate.getName())
    );

    return modelMapper.map(stayPlaceRepo.save(
      modelMapper.map(placeToCreate, StayPlaces.class)
    ), StayPlacesDto.class);
  }

  @Override @Transactional
  public StayPlacesDto update(StayPlacesDto updatedPlace) {
    checkFieldsHasContent(new Field("id", updatedPlace.getId()));

    stayPlaceRepo.findById(updatedPlace.getId()).orElseThrow(
      () -> new DocumentNotFoundException("lugar de estadía", updatedPlace.getId(), "id")
    );

    StayPlaces updated = modelMapper.map(updatedPlace, StayPlaces.class);
    return modelMapper.map(stayPlaceRepo.save(updated), StayPlacesDto.class);
  }

  @Override @Transactional
  public String delete(String id) {
    checkFieldsHasContent(new Field("id", id));

    stayPlaceRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("lugar de estadía", id, "id")
    );

    stayPlaceRepo.deleteById(id);

    return "Operación realizada con éxito";
  }
}
