package com.nahuelgDev.journeyjoy.test_services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.nahuelgDev.journeyjoy.services.StayPlaceService;

@ExtendWith(MockitoExtension.class)
public class Test_StayPlacesService {

  @Mock
  private StayPlacesRepository stayPlaceRepo;
  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private StayPlaceService stayPlaceService;

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
  void getAll_Success() {
    List<StayPlaces> stayPlacesList = List.of(stayPlace);
    when(stayPlaceRepo.findAll()).thenReturn(stayPlacesList);
    when(modelMapper.map(stayPlace, StayPlacesDto.class)).thenReturn(stayPlaceDto);

    List<StayPlacesDto> result = stayPlaceService.getAll();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(stayPlaceDto, result.get(0));
    verify(stayPlaceRepo).findAll();
  }

  @Test
  void getById_Success() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.of(stayPlace));
    when(modelMapper.map(stayPlace, StayPlacesDto.class)).thenReturn(stayPlaceDto);

    StayPlacesDto result = stayPlaceService.getById("1");

    assertNotNull(result);
    assertEquals(stayPlaceDto, result);
    verify(stayPlaceRepo).findById("1");
  }

  @Test
  void getById_ThrowsDocumentNotFoundException() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.empty());

    DocumentNotFoundException exception = assertThrows(DocumentNotFoundException.class, () -> {
      stayPlaceService.getById("1");
    });

    assertEquals("No se ha encontrado ningún/a lugar de estadía con el valor '1' en su id", exception.getMessage());
    verify(stayPlaceRepo).findById("1");
  }

  @Test
  void create_Success() {
    when(modelMapper.map(stayPlaceDto, StayPlaces.class)).thenReturn(stayPlace);
    when(stayPlaceRepo.save(stayPlace)).thenReturn(stayPlace);
    when(modelMapper.map(stayPlace, StayPlacesDto.class)).thenReturn(stayPlaceDto);

    StayPlacesDto result = stayPlaceService.create(stayPlaceDto);

    assertNotNull(result);
    assertEquals(stayPlaceDto, result);
    verify(stayPlaceRepo).save(stayPlace);
  }

  @Test
  void create_ThrowsEmptyFieldException() {
    stayPlaceDto.setName("");
    EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> {
      stayPlaceService.create(stayPlaceDto);
    });

    assertEquals("El dato ingresado para el campo 'nombre del establecimiento' no puede estar vacío o ser nulo.",
        exception.getMessage());
  }

  @Test
  void update_Success() {
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
  void update_ThrowsDocumentNotFoundException() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.empty());

    DocumentNotFoundException exception = assertThrows(DocumentNotFoundException.class, () -> {
      stayPlaceService.update(stayPlaceDto);
    });

    assertEquals("No se ha encontrado ningún/a lugar de estadía con el valor '1' en su id", exception.getMessage());
    verify(stayPlaceRepo).findById("1");
  }

  @Test
  void delete_Success() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.of(stayPlace));

    String result = stayPlaceService.delete("1");

    assertNotNull(result);
    assertEquals("Operación realizada con éxito", result);
    verify(stayPlaceRepo).findById("1");
    verify(stayPlaceRepo).deleteById("1");
  }

  @Test
  void delete_ThrowsDocumentNotFoundException() {
    when(stayPlaceRepo.findById("1")).thenReturn(Optional.empty());

    DocumentNotFoundException exception = assertThrows(DocumentNotFoundException.class, () -> {
      stayPlaceService.delete("1");
    });

    assertEquals("No se ha encontrado ningún/a lugar de estadía con el valor '1' en su id", exception.getMessage());
    verify(stayPlaceRepo).findById("1");
  }
}
