package com.nahuelgDev.journeyjoy.test_repositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dataClasses.Destinies;
import com.nahuelgDev.journeyjoy.repositories.Custom_TravelRepository;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.repositories.implementations.Custom_TravelRepository_Impl;

@DataMongoTest
@Import(Custom_TravelRepository_Impl.class)
public class Test_Custom_TravelsRepo {
  @Autowired Custom_TravelRepository custom_repository;
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
  void search_returnsExpected() {
    List<Travels> onlyAvailable = custom_repository.search(true, null, null, null, null);
    List<Travels> onlyNotAvailable = custom_repository.search(false, null, null, null, null);

    List<Travels> onlyDesiredCapacity = custom_repository.search(null, 3, null, null, null);

    List<Travels> onlyPlace_exact = custom_repository.search(null, null, "roma", null, null);
    List<Travels> onlyPlace_part = custom_repository.search(null, null, "ro", null, null);

    List<Travels> onlyMinDays = custom_repository.search(null, null, null, 20, null);
    List<Travels> onlyMaxDays = custom_repository.search(null, null, null, null, 15);

    assertAll(
      () -> assertIterableEquals(List.of(travel1, travel2), onlyAvailable),
      () -> assertIterableEquals(List.of(), onlyNotAvailable)
    );
    assertIterableEquals(List.of(travel1), onlyDesiredCapacity);
    assertAll(
      () -> assertIterableEquals(List.of(travel1), onlyPlace_exact),
      () -> assertIterableEquals(List.of(travel1, travel2), onlyPlace_part)
    );
    assertIterableEquals(List.of(travel1), onlyMinDays);
    assertIterableEquals(List.of(travel2), onlyMaxDays);

    assertAll(
      () -> assertIterableEquals(List.of(), custom_repository.search(null, 3, "rosario", null, null)),
      () -> assertIterableEquals(List.of(travel2), custom_repository.search(true, null, "", 7, 15))
    );
  }
}
