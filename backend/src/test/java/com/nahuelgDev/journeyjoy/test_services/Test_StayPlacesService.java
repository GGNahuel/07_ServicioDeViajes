package com.nahuelgDev.journeyjoy.test_services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.exceptions.EmptyFieldException;
import com.nahuelgDev.journeyjoy.repositories.StayPlacesRepository;
import com.nahuelgDev.journeyjoy.services.StayPlaceService_Impl;

@ExtendWith(MockitoExtension.class)
public class Test_StayPlacesService {

  @Mock
  private StayPlacesRepository stayPlaceRepo;
  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private StayPlaceService_Impl stayPlaceService;

  private StayPlaces stayPlace;
  private StayPlacesDto stayPlaceDto;

  @BeforeEach
  void setUp() {
    stayPlace = new StayPlaces("1");
    stayPlace.setFrom("Argentina");
    stayPlace.setName("Hotel Test");
    stayPlace.setDescription("Descripción");
    stayPlace.setRating(4.5);

    stayPlaceDto = new StayPlacesDto();
    stayPlaceDto.setId("1");
    stayPlaceDto.setFrom("Argentina");
    stayPlaceDto.setName("Hotel Test");
    stayPlaceDto.setDescription("Descripción");
    stayPlaceDto.setRating(4.5);
  }

  @Test
  void getAll_returnsTheMappedListFromRepository() {
    List<StayPlaces> stayPlacesList = List.of(stayPlace);
    List<StayPlacesDto> expected = List.of(stayPlaceDto);
    when(stayPlaceRepo.findAll()).thenReturn(stayPlacesList);
    when(modelMapper.map(stayPlace, StayPlacesDto.class)).thenReturn(stayPlaceDto);

    List<StayPlacesDto> actual = stayPlaceService.getAll();

    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertIterableEquals(expected, actual);
    verify(stayPlaceRepo).findAll();
  }

  @Test
  void getById_returnsMappedDto() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.of(stayPlace));
    when(modelMapper.map(stayPlace, StayPlacesDto.class)).thenReturn(stayPlaceDto);

    StayPlacesDto result = stayPlaceService.getById("1");

    assertNotNull(result);
    assertEquals(stayPlaceDto, result);
    verify(stayPlaceRepo).findById("1");
  }

  @Test
  void getById_throwsDocumentNotFoundException() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.empty());

    DocumentNotFoundException exception = assertThrows(DocumentNotFoundException.class, () -> {
      stayPlaceService.getById("1");
    });

    assertEquals("No se ha encontrado ningún/a lugar de estadía con el valor '1' en su id", exception.getMessage());
    verify(stayPlaceRepo).findById("1");
  }

  @Test
  void getById_throwsEmptyFieldException() {
    assertThrows(EmptyFieldException.class, () -> stayPlaceService.getById(""));
    verify(stayPlaceRepo, times(0)).findById(anyString());
  }

  @Test
  void searchByNameAndFrom_returnsMappedList() {
    when(stayPlaceRepo.searchByNameAndFromAttr(anyString(), anyString())).thenReturn(List.of(stayPlace));
    when(modelMapper.map(stayPlace, StayPlacesDto.class)).thenReturn(stayPlaceDto);

    List<StayPlacesDto> actual = stayPlaceService.searchByNameAndFrom(anyString(), anyString());

    assertIterableEquals(List.of(stayPlaceDto), actual);
    verify(stayPlaceRepo).searchByNameAndFromAttr(anyString(), anyString());
    verify(modelMapper).map(stayPlace, StayPlacesDto.class);
  }

  @Test
  void create_returnsTheCreatedDto() {
    when(modelMapper.map(stayPlaceDto, StayPlaces.class)).thenReturn(stayPlace);
    when(stayPlaceRepo.save(stayPlace)).thenReturn(stayPlace);
    when(modelMapper.map(stayPlace, StayPlacesDto.class)).thenReturn(stayPlaceDto);

    StayPlacesDto result = stayPlaceService.create(stayPlaceDto);

    assertNotNull(result);
    assertEquals(stayPlaceDto, result);
    verify(stayPlaceRepo).save(stayPlace);
  }

  @Test
  void create_throwsEmptyFieldExceptionForNameAndFrom() {
    StayPlacesDto emptyName = StayPlacesDto.builder().name("").from("from").build();
    StayPlacesDto emptyFrom = StayPlacesDto.builder().name("name").from("").build();
    
    StayPlacesDto successCase = StayPlacesDto.builder().name("name").from("from").build();
    when(modelMapper.map(successCase, StayPlaces.class)).thenReturn(new StayPlaces());

    assertThrows(EmptyFieldException.class, () -> stayPlaceService.create(emptyName));
    assertThrows(EmptyFieldException.class, () -> stayPlaceService.create(emptyFrom));
    assertDoesNotThrow(() -> stayPlaceService.create(successCase));
    verify(stayPlaceRepo, times(1)).save(any());
  }

  @Test
  void update_success() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.of(stayPlace));
    when(modelMapper.map(stayPlaceDto, StayPlaces.class)).thenReturn(stayPlace);
    when(stayPlaceRepo.save(stayPlace)).thenReturn(stayPlace);
    when(modelMapper.map(stayPlace, StayPlacesDto.class)).thenReturn(stayPlaceDto);

    StayPlacesDto result = stayPlaceService.update(stayPlaceDto);

    assertNotNull(result);
    assertEquals(stayPlaceDto, result);
    verify(stayPlaceRepo).findById("1");
    verify(stayPlaceRepo).save(stayPlace);
  }

  @Test
  void update_throwsDocumentNotFoundException() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> stayPlaceService.update(stayPlaceDto));
    verify(stayPlaceRepo).findById("1");
    verify(stayPlaceRepo, times(0)).save(any());
  }

  @Test
  void update_throwsEmptyFieldExceptionForId() {
    assertThrows(EmptyFieldException.class, () -> stayPlaceService.delete(""));
    assertThrows(EmptyFieldException.class, () -> stayPlaceService.delete(null));
    verify(stayPlaceRepo, times(0)).save(any());
  }

  @Test
  void delete_returnsSuccessString() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.of(stayPlace));

    String result = stayPlaceService.delete("1");

    assertNotNull(result);
    assertEquals("Operación realizada con éxito", result);
    verify(stayPlaceRepo).findById("1");
    verify(stayPlaceRepo).deleteById("1");
  }

  @Test
  void delete_throwsDocumentNotFoundException() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> stayPlaceService.delete("1"));
    verify(stayPlaceRepo).findById("1");
    verify(stayPlaceRepo, times(0)).deleteById(anyString());
  }

  @Test
  void delete_throwsEmptyFieldExceptionForId() {
    assertThrows(EmptyFieldException.class, () -> stayPlaceService.delete(null));
    assertThrows(EmptyFieldException.class, () -> stayPlaceService.delete(""));
    verify(stayPlaceRepo, times(0)).deleteById(anyString());
  }
}
