package com.nahuelgDev.journeyjoy.test_services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dataClasses.Destinies;
import com.nahuelgDev.journeyjoy.dataClasses.PayPlans;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.exceptions.EmptyFieldException;
import com.nahuelgDev.journeyjoy.exceptions.InvalidOperationException;
import com.nahuelgDev.journeyjoy.repositories.Custom_TravelRepository;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.services.TravelsService_Impl;

@ExtendWith(MockitoExtension.class)
public class Test_TravelsService {
  
  @Mock TravelsRepository repository;
  @Mock Custom_TravelRepository customRepo;

  @InjectMocks TravelsService_Impl service;

  Travels travel1, travel2;

  @BeforeEach
  void setUp() {
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
  }

  @Test
  void getAll_returnsListFromRepository() {
    List<Travels> expected = List.of(travel1, travel2);
    when(repository.findAll()).thenReturn(List.of(travel1, travel2));

    List<Travels> actual = service.getAll();

    assertIterableEquals(expected, actual);
    verify(repository).findAll();
  }

  @Test
  void getById_returnsExpectedDocument() {
    when(repository.findById("1")).thenReturn(Optional.of(travel1));

    assertEquals(travel1, service.getById("1"));
    verify(repository).findById("1");
  }

  @Test
  void getById_throwsEmptyFieldException() {
    assertThrows(EmptyFieldException.class, () -> service.getById(""));
    assertThrows(EmptyFieldException.class, () -> service.getById(null));
    verify(repository, times(0)).findById(any());
  }

  @Test
  void getById_throwsDocumentNotFoundException() {
    when(repository.findById("4")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> service.getById("4"));
    verify(repository).findById("4");
  }

  @Test
  void getByCapacityLeft_returnsExpected() {
    when(repository.findByHasCapacityLeft()).thenReturn(List.of(travel1, travel2));
    when(repository.findByNoCapacityLeft()).thenReturn(List.of());

    assertIterableEquals(List.of(travel1, travel2), service.getByCapacityLeft(true));
    assertIterableEquals(List.of(), service.getByCapacityLeft(false));
    verify(repository).findByHasCapacityLeft();
    verify(repository).findByNoCapacityLeft();
  }

  @Test
  void getByCapacityLeft_throwsEmptyFieldException() {
    assertThrows(EmptyFieldException.class, () -> service.getByCapacityLeft(null));
    verify(repository, times(0)).findByHasCapacityLeft();
    verify(repository, times(0)).findByNoCapacityLeft();
  }

  @Test
  void search_returnsListFromRepository() throws Exception {
    List<Travels> listOfBoth = List.of(travel1, travel2);
    List<Travels> listOfFirst = List.of(travel1);
    when(customRepo.search(null, null, "ro", null, null)).thenReturn(listOfBoth);
    when(customRepo.search(null, null, "roma", null, null)).thenReturn(listOfFirst);
    when(customRepo.search(null, 3, "", null, null)).thenReturn(listOfFirst);

    assertIterableEquals(listOfBoth, service.search(null, null, "ro", null, null));
    assertIterableEquals(listOfFirst, service.search(null, null, "roma", null, null));
    assertIterableEquals(listOfFirst, service.search(null, 3, "", null, null));
  }

  @Test
  void create_returnsCreatedDocument() throws Exception {
    Travels travelToCreate = Travels.builder()
      .id("3").name("create").maxCapacity(20)
      .destinies(List.of(mock(Destinies.class)))
      .longInDays(15).availableDates(List.of(LocalDate.now()))
      .payPlans(List.of(mock(PayPlans.class)))
    .build();

    Travels createdTravel = Travels.builder()
      .id("3").name("create").maxCapacity(20)
      .destinies(List.of(mock(Destinies.class)))
      .longInDays(15).availableDates(List.of(LocalDate.now()))
      .payPlans(List.of(mock(PayPlans.class)))
      .currentCapacity(0).isAvailable(true)
    .build();

    when(repository.save(any(Travels.class))).thenReturn(createdTravel);

    Travels actual = service.create(travelToCreate);

    assertNotEquals(travelToCreate, actual);
    assertEquals(createdTravel, actual);
    verify(repository).save(any(Travels.class));
  }

  @Test
  void create_throwsEmptyFieldException() {
    Travels withoutName = Travels.builder()
      .id("3").maxCapacity(20)
      .destinies(List.of(mock(Destinies.class)))
      .longInDays(15).availableDates(List.of(LocalDate.now()))
      .payPlans(List.of(mock(PayPlans.class)))
    .build();
    Travels withoutMaxCapacity = Travels.builder()
      .id("3").name("create")
      .destinies(List.of(mock(Destinies.class)))
      .longInDays(15).availableDates(List.of(LocalDate.now()))
      .payPlans(List.of(mock(PayPlans.class)))
    .build();
    Travels withEmptyDestinies = Travels.builder()
      .id("3").name("create").maxCapacity(20)
      .destinies(List.of())
      .longInDays(15).availableDates(List.of(LocalDate.now()))
      .payPlans(List.of(mock(PayPlans.class)))
    .build();
    Travels withoutLongInDays = Travels.builder()
      .id("3").name("create").maxCapacity(20)
      .destinies(List.of(mock(Destinies.class)))
      .availableDates(List.of(LocalDate.now()))
      .payPlans(List.of(mock(PayPlans.class)))
    .build();
    Travels withoutDates = Travels.builder()
      .id("3").name("create").maxCapacity(20)
      .destinies(List.of(mock(Destinies.class)))
      .longInDays(15)
      .payPlans(List.of(mock(PayPlans.class)))
    .build();
    Travels withEmptyPlans = Travels.builder()
      .id("3").name("create").maxCapacity(20)
      .destinies(List.of(mock(Destinies.class)))
      .longInDays(15).availableDates(List.of(LocalDate.now()))
      .payPlans(List.of())
    .build();

    assertAll(
      () -> assertThrows(EmptyFieldException.class, () -> service.create(null)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutName)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutMaxCapacity)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withEmptyDestinies)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutLongInDays)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutDates)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withEmptyPlans))
    );
    verify(repository, times(0)).save(any(Travels.class));
  }

  @Test
  void update_returnsUpdatedDocument() throws Exception {
    Travels newTravel1 = Travels.builder()
      .id("1").name("Viaje 1").longInDays(21)
      .maxCapacity(20).currentCapacity(18).isAvailable(true)
      .availableDates(List.of(LocalDate.of(2025, 3, 15), LocalDate.of(2025, 7, 8)))
      .destinies(List.of(
        Destinies.builder().place("Roma").build(),
        Destinies.builder().place("Venecia").build()
      ))
    .build();
    when(repository.save(newTravel1)).thenReturn(newTravel1);
    when(repository.findById("1")).thenReturn(Optional.of(travel1));
    
    Travels actual = service.update(newTravel1);

    assertEquals(newTravel1, actual);
    verify(repository).findById("1");
    verify(repository).save(newTravel1);
  }

  @Test
  void update_throwsEmptyFieldException() {
    Travels newTravel1 = Travels.builder()
      .name("Viaje 1").longInDays(21)
      .maxCapacity(20).currentCapacity(18).isAvailable(true)
      .availableDates(List.of(LocalDate.of(2025, 3, 15), LocalDate.of(2025, 7, 8)))
      .destinies(List.of(
        Destinies.builder().place("Roma").build(),
        Destinies.builder().place("Venecia").build()
      ))
    .build();

    assertThrows(EmptyFieldException.class, () -> service.update(null));
    assertThrows(EmptyFieldException.class, () -> service.update(newTravel1));
    verify(repository, times(0)).findById("1");
    verify(repository, times(0)).save(newTravel1);
  }

  @Test
  void update_throwsDocumentNotFoundException() {
    Travels newTravel1 = Travels.builder()
      .id("4").name("Viaje 1").longInDays(21)
      .maxCapacity(20).currentCapacity(18).isAvailable(true)
      .availableDates(List.of(LocalDate.of(2025, 3, 15), LocalDate.of(2025, 7, 8)))
      .destinies(List.of(
        Destinies.builder().place("Roma").build(),
        Destinies.builder().place("Venecia").build()
      ))
    .build();

    when(repository.findById("4")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> service.update(newTravel1));
    verify(repository).findById("4");
    verify(repository, times(0)).save(newTravel1);
  }

  @Test
  void addReview_addReviewToListOfReviews() throws Exception {
    travel1.setReviews(new ArrayList<>());
    when(repository.findById("1")).thenReturn(Optional.of(travel1));
    Reviews review = Reviews.builder().userName("username").rating(2.5).build();

    service.addReview("1", review);
    assertTrue(travel1.getReviews().contains(review));
    verify(repository).findById("1");
    verify(repository).save(any(Travels.class));
  }

  @Test
  void addReview_throwsEmptyFieldException() {
    assertAll(
      () -> assertThrows(EmptyFieldException.class, () -> service.addReview("", Reviews.builder().userName("asd").rating(1.0).build())),
      () -> assertThrows(EmptyFieldException.class, () -> service.addReview("1", null)),
      () -> assertThrows(EmptyFieldException.class, () -> service.addReview("1", Reviews.builder().rating(2.0).build())),
      () -> assertThrows(EmptyFieldException.class, () -> service.addReview("1", Reviews.builder().userName("ads").build()))
    );
    verify(repository, times(0)).findById(anyString());
    verify(repository, times(0)).save(any(Travels.class));
  }

  @Test
  void addReview_throwsDocumentNotFoundException() {
    Reviews review = Reviews.builder().userName("username").rating(2.5).build(); 
    when(repository.findById("4")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> service.addReview("4", review));
    verify(repository).findById("4");
    verify(repository, times(0)).save(any(Travels.class));
  }

  @Test
  void addReview_throwsInvalidOperationException() {
    Reviews review = Reviews.builder().userName("username").rating(2.5).build(); 
    travel1.setReviews(List.of(review));
    when(repository.findById("1")).thenReturn(Optional.of(travel1));

    assertThrows(InvalidOperationException.class, () -> service.addReview("1", review));
    verify(repository).findById("1");
    verify(repository, times(0)).save(any(Travels.class));
  }

  @Test
  void delete_callDeleteMethodFromRepository() {
    when(repository.findById("1")).thenReturn(Optional.of(travel1));

    assertDoesNotThrow(() -> service.delete("1"));
    verify(repository).findById("1");
    verify(repository).deleteById("1");
  }

  @Test
  void delete_throwsEmptyFieldException() {
    assertThrows(EmptyFieldException.class, () -> service.delete(null));
    verify(repository, times(0)).findById(anyString());
    verify(repository, times(0)).deleteById(anyString());
  }

  @Test
  void delete_throwsDocumentNotFoundException() {
    when(repository.findById("4")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> service.delete("4"));
    verify(repository).findById("4");
    verify(repository, times(0)).deleteById(anyString());
  }
}
