package com.nahuelgDev.journeyjoy.services;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkFieldsHasContent;
import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkStringIsAlphaNumeric;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.repositories.StayPlacesRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.StayPlacesService_I;
import com.nahuelgDev.journeyjoy.utilities.Verifications.Field;

@Service
public class StayPlaceService_Impl implements StayPlacesService_I {
  private final StayPlacesRepository stayPlaceRepo;
  private final ModelMapper modelMapper;

  public StayPlaceService_Impl(StayPlacesRepository repository, ModelMapper mapper) {
    this.stayPlaceRepo = repository;
    this.modelMapper = mapper;
  }

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

  @Override @Transactional(readOnly = true)
  public List<StayPlacesDto> searchByNameAndFrom(String name, String from) throws Exception {
    checkStringIsAlphaNumeric(new Field("nombre del establecimiento", name), new Field("origen", from));

    return stayPlaceRepo.searchByNameAndFromAttr(name, from).stream().map(
      element -> modelMapper.map(element, StayPlacesDto.class)
    ).toList();
  }

  @Override @Transactional
  public StayPlacesDto create(StayPlacesDto placeToCreate) throws Exception{
    checkFieldsHasContent(new Field("lugar de estadía", placeToCreate));
    
    checkStringIsAlphaNumeric(
      new Field("nombre del establecimiento", placeToCreate.getName()),
      new Field("origen", placeToCreate.getFrom()),
      new Field("descripción", placeToCreate.getDescription())
    );

    checkFieldsHasContent(
      new Field("lugar", placeToCreate.getFrom()),
      new Field("nombre del establecimiento", placeToCreate.getName())
    );

    return modelMapper.map(stayPlaceRepo.save(
      modelMapper.map(placeToCreate, StayPlaces.class)
    ), StayPlacesDto.class);
  }

  @Override @Transactional
  
  public StayPlacesDto update(StayPlacesDto updatedPlace) throws Exception {
    checkFieldsHasContent(new Field("lugar de estadía", updatedPlace));

    checkStringIsAlphaNumeric(
      new Field("nombre del establecimiento", updatedPlace.getName()),
      new Field("origen", updatedPlace.getFrom()),
      new Field("descripción", updatedPlace.getDescription())
    );
    
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
