package com.nahuelgDev.journeyjoy.test_repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.repositories.StayPlacesRepository;

@DataMongoTest
public class Test_StayPlaceRepo {

  @Autowired StayPlacesRepository stayPlacesRepository;

  private StayPlaces stayPlace1, stayPlace2, stayPlace3;

  @BeforeEach
  void setUp() {
    stayPlacesRepository.deleteAll();
    
    stayPlace1 = new StayPlaces("1");
    stayPlace1.setFrom("Argentina");
    stayPlace1.setName("Hotel A");
    stayPlace1.setDescription("Descripción A");
    stayPlace1.setRating(4.0);
    
    stayPlace2 = new StayPlaces("2");
    stayPlace2.setFrom("Argentina");
    stayPlace2.setName("Hostel");
    stayPlace2.setDescription("Descripción B");
    stayPlace2.setRating(3.5);
    
    stayPlace3 = new StayPlaces("3");
    stayPlace3.setFrom("Brasil");
    stayPlace3.setName("Hotel C");
    stayPlace3.setDescription("Descripción C");
    stayPlace3.setRating(4.5);
    
    stayPlacesRepository.saveAll(List.of(stayPlace1, stayPlace2, stayPlace3));
  }
  
  @Test
  void searchByNameAndFromAttr_returnsExpectedLists() {
    List<StayPlaces> expectedFrom = List.of(stayPlace1, stayPlace2);
    List<StayPlaces> expectedName = List.of(stayPlace1, stayPlace3);
    List<StayPlaces> expectedNameAndFrom = List.of(stayPlace1);
    
    List<StayPlaces> actualFrom = stayPlacesRepository.searchByNameAndFromAttr("", "arg");
    List<StayPlaces> actualName = stayPlacesRepository.searchByNameAndFromAttr("hot", "");
    List<StayPlaces> actualNameAndFrom = stayPlacesRepository.searchByNameAndFromAttr("hotel", "argentina");
    
    assertIterableEquals(expectedName, actualName);
    assertIterableEquals(expectedFrom, actualFrom);
    assertIterableEquals(expectedNameAndFrom, actualNameAndFrom);
  }
  
  // estas pruebas no son necesarios, ya que los métodos de MongoRepository ya están testeados por spring boot
  @Test
  void findAll_ReturnsAllStayPlaces() {
    List<StayPlaces> result = stayPlacesRepository.findAll();

    assertNotNull(result);
    assertEquals(3, result.size());
    assertTrue(result.contains(stayPlace1));
    assertTrue(result.contains(stayPlace2));
    assertTrue(result.contains(stayPlace3));
  }

  @Test
  void findById_ReturnsCorrectStayPlace() {
    Optional<StayPlaces> result = stayPlacesRepository.findById("1");

    assertTrue(result.isPresent());
    assertEquals(stayPlace1, result.get());
  }

  @Test
  void findById_ReturnsEmptyOptionalWhenNotFound() {
    Optional<StayPlaces> result = stayPlacesRepository.findById("999");

    assertTrue(result.isEmpty());
  }

  @Test
  void save_PersistsStayPlace() {
    StayPlaces newStayPlace = new StayPlaces("4");
    newStayPlace.setFrom("Chile");
    newStayPlace.setName("Hotel D");
    newStayPlace.setDescription("Descripción D");
    newStayPlace.setRating(5.0);

    stayPlacesRepository.save(newStayPlace);

    Optional<StayPlaces> result = stayPlacesRepository.findById("4");
    assertTrue(result.isPresent());
    assertEquals(newStayPlace, result.get());
  }

  @Test
  void delete_RemovesStayPlace() {
    stayPlacesRepository.deleteById("1");

    Optional<StayPlaces> result = stayPlacesRepository.findById("1");
    assertTrue(result.isEmpty());
  }
}
