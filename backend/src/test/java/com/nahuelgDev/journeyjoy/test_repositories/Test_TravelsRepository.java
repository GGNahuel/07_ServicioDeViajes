package com.nahuelgDev.journeyjoy.test_repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dataClasses.Destinies;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;

@DataMongoTest
public class Test_TravelsRepository {
  
  @Autowired TravelsRepository repository;

  private Travels travel1, travel2;

  @BeforeEach
  void setUp() {
    repository.deleteAll();

    travel1 = Travels.builder()
      .id("1").name("Viaje 1").longInDays(21)
      .maxCapacity(30).currentCapacity(18).isAvailable(true)
      .availableDates(List.of(LocalDate.of(2025, 3, 15), LocalDate.of(2025, 7, 8)))
      .destinies(List.of(
        Destinies.builder().place("Roma").build(),
        Destinies.builder().place("Venecia").build()
      ))
    .build();
    travel2 = Travels.builder()
      .id("2").name("Viaje 2").longInDays(15)
      .maxCapacity(20).currentCapacity(18).isAvailable(true)
      .availableDates(List.of(LocalDate.of(2025, 3, 15), LocalDate.of(2025, 7, 8)))
      .destinies(List.of(
        Destinies.builder().place("Buenos Aires").build(),
        Destinies.builder().place("Rosario").build()
      ))
    .build();

    repository.saveAll(List.of(travel1, travel2));
  }

  @Test
  void findByName_returnsExpected() {
    assertEquals(Optional.of(travel1), repository.findByName("Viaje 1"));
    assertEquals(Optional.empty(), repository.findByName("Viaje 3"));
    assertEquals(Optional.empty(), repository.findByName("Viaje"));
  }

  @Test
  void findByHasCapacityLeft_returnsExpected() {
    assertIterableEquals(List.of(travel1, travel2), repository.findByHasCapacityLeft());
  }

  @Test
  void findByNoCapacityLeft_returnsExpected() {
    assertIterableEquals(List.of(), repository.findByNoCapacityLeft());
  }
}
