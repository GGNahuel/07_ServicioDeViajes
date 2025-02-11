package com.nahuelgDev.journeyjoy.test_services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.nahuelgDev.journeyjoy.collections.Emails;
import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dataClasses.PayPlans;
import com.nahuelgDev.journeyjoy.dataClasses.Person;
import com.nahuelgDev.journeyjoy.dtos.RequestsUpdateDto;
import com.nahuelgDev.journeyjoy.enums.EmailSubjects;
import com.nahuelgDev.journeyjoy.enums.PayPlansType;
import com.nahuelgDev.journeyjoy.enums.RequestState;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.exceptions.EmptyFieldException;
import com.nahuelgDev.journeyjoy.exceptions.InvalidFieldValueException;
import com.nahuelgDev.journeyjoy.repositories.EmailsRepository;
import com.nahuelgDev.journeyjoy.repositories.RequestsRepository;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.services.EmailsService;
import com.nahuelgDev.journeyjoy.services.RequestsService_Impl;
import com.nahuelgDev.journeyjoy.utilities.constants.EmailContents;

@ExtendWith(MockitoExtension.class)
public class Test_RequestsService {
  
  @Mock RequestsRepository repository;
  @Mock TravelsRepository travelsRepo;
  @Mock EmailsRepository emailsRepo;
  @Mock EmailsService emailsService;
  @Mock ModelMapper modelMapper;

  @InjectMocks RequestsService_Impl service;

  private Requests request1, request2;
  private Travels travel1, travel2;

  @BeforeEach
  void setUp() {
    travel1 = Travels.builder()
      .id("1").name("travel1")
      .payPlans(List.of(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build()))
      .availableDates(List.of(LocalDate.of(2025, 3, 15)))
      .maxCapacity(20).currentCapacity(15)
    .build();
    travel2 = Travels.builder().id("2").name("travel2").build();

    request1 = Requests.builder()
      .id("1").amountPaid(200.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .state(RequestState.completePayment)
      .email(Emails.builder().email("email@gmail.com").build())
    .build();
    request2 = Requests.builder()
      .id("2").amountPaid(0.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel2)
      .state(RequestState.inWaitList)
      .email(Emails.builder().email("email@gmail.com").build())
    .build();
  }

  @Test
  void getAll_returnsAListOfDocuments() {
    List<Requests> expected = List.of(request1, request2);
    when(repository.findAll()).thenReturn(expected);

    assertIterableEquals(expected, service.getAll());
    verify(repository).findAll();
  }

  @Test
  void getById_returnsExpected() {
    when(repository.findById("1")).thenReturn(Optional.of(request1));

    assertEquals(request1, service.getById("1"));
    verify(repository).findById("1");
  }

  @Test
  void getById_throwsEmptyFieldException() {
    assertThrows(EmptyFieldException.class, () -> service.getById(null));
    verify(repository, times(0)).findById(anyString());
  }

  @Test
  void getById_throwsDocumentNotFoundException() {
    when(repository.findById("4")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> service.getById("4"));
    verify(repository).findById("4");
  }

  @Test
  void getByTravelId_returnsExpected() {
    List<Requests> expected = List.of(request1);
    when(repository.findByAssociatedTravelId("1")).thenReturn(expected);

    assertIterableEquals(expected, service.getByTravelId("1"));
    verify(repository).findByAssociatedTravelId("1");
  }

  @Test
  void getByTravelId_throwsEmptyFieldException() {
    assertThrows(EmptyFieldException.class, () -> service.getById(""));
    verify(repository, times(0)).findById(anyString());
  }

  @Test
  void getByEmail_returnsExpected() {
    List<Requests> expected = List.of(request1, request2);
    when(repository.findByEmail("email@gmail.com")).thenReturn(expected);

    assertIterableEquals(expected, service.getByEmail("email@gmail.com"));
    verify(repository).findByEmail("email@gmail.com");
  }

  @Test
  void getByEmail_throwsEmptyFieldException() {
    assertThrows(EmptyFieldException.class, () -> service.getByEmail(""));
    verify(repository, times(0)).findByEmail(anyString());
  }

  @Test
  void create_returnsExpectedRequestWithRightChanges() {
    Requests input = Requests.builder()
      .id("1").amountPaid(200.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email("email@gmail.com").build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
    .build();
    Requests expected = Requests.builder()
      .id("1").amountPaid(200.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email("email@gmail.com").build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
      .totalPrice(200.0).state(RequestState.completePayment)
    .build();

    when(travelsRepo.findByName("travel1")).thenReturn(Optional.of(travel1));
    when(emailsRepo.findByEmail("email@gmail.com")).thenReturn(Optional.of(mock(Emails.class)));

    Requests actual = service.create(input);

    assertEquals(expected, actual);
    verify(travelsRepo).findByName("travel1");
    verify(emailsRepo).findByEmail("email@gmail.com");
    verify(emailsRepo, times(0)).save(any(Emails.class));
    verify(emailsService).sendEmail("email@gmail.com", "Solicitud de viaje realizada - Journey Joy", EmailContents.requestNotification(expected));
    verify(repository).save(expected);

    ArgumentCaptor<Travels> travelSaved = ArgumentCaptor.forClass(Travels.class);
    verify(travelsRepo).save(travelSaved.capture());
    assertEquals(16, travelSaved.getValue().getCurrentCapacity());
  }

  @Test
  void create_setRequestStateInWaitList() {
    travel1.setCurrentCapacity(20);
    Requests input = Requests.builder()
      .id("1").amountPaid(0.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email("email@gmail.com").build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
    .build();

    when(travelsRepo.findByName("travel1")).thenReturn(Optional.of(travel1));
    when(emailsRepo.findByEmail("email@gmail.com")).thenReturn(Optional.of(mock(Emails.class)));

    Requests actual = service.create(input);

    assertEquals(RequestState.inWaitList, actual.getState());
    verify(travelsRepo).findByName("travel1");
    verify(travelsRepo, times(0)).save(any(Travels.class));
    verify(emailsRepo).findByEmail("email@gmail.com");
    verify(emailsRepo, times(0)).save(any(Emails.class));
    verify(repository).save(input);
    verify(emailsService).sendEmail("email@gmail.com", EmailContents.setSubject(EmailSubjects.CreatedRequest), EmailContents.requestNotification(actual));
  }

  @Test
  void create_saveEmailsDocIfNotExists() {
    String email = "email@gmail.com";
    Requests input = Requests.builder()
      .id("1").amountPaid(0.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email(email).build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
    .build();

    when(travelsRepo.findByName("travel1")).thenReturn(Optional.of(travel1));
    when(emailsRepo.findByEmail(email)).thenReturn(Optional.empty());

    service.create(input);

    ArgumentCaptor<Emails> emailCreatedCaptor = ArgumentCaptor.forClass(Emails.class);
    verify(emailsRepo).save(emailCreatedCaptor.capture());

    assertEquals(email, emailCreatedCaptor.getValue().getEmail());

    verify(travelsRepo).findByName("travel1");
    verify(emailsRepo).findByEmail(email);
    verify(emailsService).sendEmail(anyString(), anyString(), anyString());
    verify(repository).save(input);
  }

  @Test
  void create_throwsEmptyFieldException() {
    Requests withoutSelectedPlan = Requests.builder()
      .amountPaid(0.0)
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email("email").build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
    .build();
    Requests withoutPeople = Requests.builder()
      .amountPaid(0.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email("email").build())
    .build();
    Requests withoutAssociatedTravel = Requests.builder()
      .amountPaid(0.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .email(Emails.builder().email("email").build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
    .build();
    Requests withoutAmountPaid = Requests.builder()
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email("email").build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
    .build();
    Requests withoutEmail = Requests.builder()
      .amountPaid(0.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
    .build();

    assertAll(
      () -> assertThrows(EmptyFieldException.class, () -> service.create(null)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutSelectedPlan)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutPeople)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutEmail)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutAssociatedTravel)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutAmountPaid))
    );
    verify(travelsRepo, times(0)).findByName(anyString());
    verify(emailsRepo, times(0)).findByEmail(anyString());
    verify(emailsService, times(0)).sendEmail(anyString(), anyString(), anyString());
    verify(repository, times(0)).save(any(Requests.class));
    verify(travelsRepo, times(0)).save(any(Travels.class));
    verify(emailsRepo, times(0)).save(any(Emails.class));
  }

  @Test
  void create_throwsDocumentNotFoundExceptionForTravel() {
    Requests input = Requests.builder()
      .id("1").amountPaid(0.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email("email").build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
    .build();

    when(travelsRepo.findByName("travel1")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> service.create(input));
    verify(travelsRepo, times(0)).save(any(Travels.class));
    verify(emailsService,times(0)).sendEmail(anyString(), anyString(), anyString());
    verify(repository, times(0)).save(any(Requests.class));
  }

  @Test
  void create_throwsInvalidFieldValueException() {
    Requests invalidPlan = Requests.builder()
      .id("1").amountPaid(0.0)
      .selectedPlan(PayPlans.builder().price(50.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email("email").build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
    .build();
    Requests invalidAmountPaid = Requests.builder()
      .id("1").amountPaid(200.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email("email").build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
    .build();

    when(travelsRepo.findByName("travel1")).thenReturn(Optional.of(travel1));

    assertAll(
      () -> assertThrows(InvalidFieldValueException.class, () -> service.create(invalidPlan)),
      () -> assertThrows(InvalidFieldValueException.class, () -> {
        travel1.setCurrentCapacity(20);
        service.create(invalidAmountPaid);
      })
    );
    verify(repository, times(0)).save(any(Requests.class));
    verify(emailsService, times(0)).sendEmail(anyString(), anyString(), anyString());
  }

  @Test
  void update_returnsExpectedAndSaveNewTravelCapacity() {
    RequestsUpdateDto input = RequestsUpdateDto.builder()
      .id("1").associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .persons(List.of(Person.builder().name("asd").age(23).contactPhone(342).build()))
    .build();
    Requests saved = Requests.builder()
      .id("1").amountPaid(200.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .email(Emails.builder().email("email@gmail.com").build())
      .persons(List.of(Person.builder().name("person").age(25).contactPhone(140).build()))
      .totalPrice(200.0).state(RequestState.completePayment)
    .build();
    when(repository.findById("1")).thenReturn(Optional.of(saved));
    when(repository.save(any(Requests.class))).thenReturn(saved);
    when(modelMapper.map(input, Requests.class)).thenReturn(saved);
    when(travelsRepo.findByName("travel1")).thenReturn(Optional.of(travel1));

    Requests actual = service.update(input);

    assertEquals(saved, actual);
    verify(repository, times(2)).findById("1");
    verify(travelsRepo).findByName("travel1");

    ArgumentCaptor<Travels> travelSaved = ArgumentCaptor.forClass(Travels.class);
    verify(travelsRepo).save(travelSaved.capture());
    assertEquals(travel1.getCurrentCapacity() + input.getPersons().size() - saved.getPersons().size(), travelSaved.getValue().getCurrentCapacity());
  }

  @Test
  void update_throwsEmptyFieldException() {    
    assertAll(
      () -> assertThrows(EmptyFieldException.class, () -> service.update(null)),
      () -> assertThrows(EmptyFieldException.class, () -> service.update(new RequestsUpdateDto()))
    );
    verify(repository, times(0)).save(any(Requests.class));
    verify(travelsRepo, times(0)).save(any(Travels.class));
    verify(emailsService, times(0)).sendEmail(anyString(), anyString(), anyString());
  }

  @Test 
  void update_throwsDocumentNotFoundException() {
    RequestsUpdateDto input = RequestsUpdateDto.builder()
      .id("4").associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .persons(List.of(Person.builder().name("asd").age(23).contactPhone(342).build()))
    .build();
    RequestsUpdateDto input2 = RequestsUpdateDto.builder()
      .id("1").associatedTravel(travel1).selectedDate(LocalDate.of(2025, 3, 15))
      .persons(List.of(Person.builder().name("asd").age(23).contactPhone(342).build()))
    .build();
    when(repository.findById("4")).thenReturn(Optional.empty());
    when(repository.findById("1")).thenReturn(Optional.of(request1));
    when(travelsRepo.findByName("travel1")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> service.update(input));
    assertThrows(DocumentNotFoundException.class, () -> service.update(input2));
    verify(repository).findById("4");
    verify(repository).findById("1");
    verify(travelsRepo).findByName("travel1");
    verify(repository, times(0)).save(any(Requests.class));
    verify(travelsRepo, times(0)).save(any(Travels.class));
    verify(emailsService, times(0)).sendEmail(anyString(), anyString(), anyString());
  }

  @Test
  void update_throwsInvalidOperationException() {
    
  }
}
