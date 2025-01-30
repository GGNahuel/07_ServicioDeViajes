package com.nahuelgDev.journeyjoy.test_repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

  // estas pruebas no son necesarios, ya que los métodos de MongoRepository ya están testeados por spring boot
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
    stayPlace2.setName("Hotel B");
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
  public void findAll_ReturnsAllStayPlaces() {
    List<StayPlaces> result = stayPlacesRepository.findAll();

    assertNotNull(result);
    assertEquals(3, result.size());
    assertTrue(result.contains(stayPlace1));
    assertTrue(result.contains(stayPlace2));
    assertTrue(result.contains(stayPlace3));
  }

  @Test
  public void findById_ReturnsCorrectStayPlace() {
    Optional<StayPlaces> result = stayPlacesRepository.findById("1");

    assertTrue(result.isPresent());
    assertEquals(stayPlace1, result.get());
  }

  @Test
  public void findById_ReturnsEmptyOptionalWhenNotFound() {
    Optional<StayPlaces> result = stayPlacesRepository.findById("999");

    assertTrue(result.isEmpty());
  }

  /* @Test
  public void findByFrom_ReturnsMatchingStayPlaces() {
    List<StayPlaces> result = stayPlacesRepository.findByFrom("Argentina");

    assertNotNull(result);
    assertEquals(2, result.size());
    assertTrue(result.contains(stayPlace1));
    assertTrue(result.contains(stayPlace2));
  } */

  @Test
  public void save_PersistsStayPlace() {
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
  public void delete_RemovesStayPlace() {
    stayPlacesRepository.deleteById("1");

    Optional<StayPlaces> result = stayPlacesRepository.findById("1");
    assertTrue(result.isEmpty());
  }
}
