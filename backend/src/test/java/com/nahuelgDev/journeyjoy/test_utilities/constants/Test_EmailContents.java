package com.nahuelgDev.journeyjoy.test_utilities.constants;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nahuelgDev.journeyjoy.collections.Emails;
import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dataClasses.PayPlans;
import com.nahuelgDev.journeyjoy.dataClasses.Person;
import com.nahuelgDev.journeyjoy.enums.PayPlansType;
import com.nahuelgDev.journeyjoy.enums.RequestState;
import com.nahuelgDev.journeyjoy.utilities.constants.EmailContents;

public class Test_EmailContents {
  private Requests request;

  @BeforeEach
  void setUp() {
    Person person1 = Person.builder().name("person1").age(20).identificationNumber(12345678).build();
    Person person2 = Person.builder().name("person2").age(25).identificationNumber(87654321).build();
    List<Person> people = List.of(person1, person2);

    request = Requests.builder()
      .id("1")
      .selectedPlan(PayPlans.builder().price(500.0).planFor(PayPlansType.couple).build())
      .persons(people)
      .email(Emails.builder().id("email1").email("example@g.com").owner("person1").build())
      .state(RequestState.parcialPayment)
      .amountPaid(150.0)
      .totalPrice(500.0)
      .associatedTravel(
        Travels.builder().id("travel1")
          .name("test_viaje")
          .longInDays(10)
          .maxCapacity(36)
          .currentCapacity(15)
          .isAvailable(true)
          .availableDates(List.of(LocalDate.of(2025, 3, 5)))
          .destinies(List.of())
        .build()
      )
      .selectedDate(LocalDate.of(2025, 3, 5))
    .build();
  }

  @Test
  void getPeopleData() {
    
  }

  @Test
  void requestNotification_ShouldContainRequestDetails() {
    String result = EmailContents.requestNotification(request);
    assertTrue(result.contains("Gracias por utilizar nuestro servicio de viajes"));
    assertTrue(result.contains("Viaje: test_viaje"));
    assertTrue(result.contains("Cantidad de personas: 2"));
  }

  @Test
  void paymentNotification_ShouldContainPaymentDetails() {
    String result = EmailContents.paymentNotification(100.0, request);
    assertTrue(result.contains("Se ha recibido un pago de $100.0"));
    assertTrue(result.contains("Viaje: test_viaje"));
  }

  @Test
  void updatedRequestNotification_ShouldContainUpdatedMessage() {
    String result = EmailContents.updatedRequestNotification(request);
    assertTrue(result.contains("Se ha actualizado la solicitud de su viaje."));
    assertTrue(result.contains("Viaje: test_viaje"));
  }

  @Test
  void cancelRequest_ShouldContainCancellationMessage() {
    String result = EmailContents.cancelRequest(request);
    assertTrue(result.contains("Su solicitud del viaje test_viaje, para la fecha 2025-03-05, ha sido cancelada con éxito."));
  }

  @Test
  void travelNowHasCapacityNotification_ShouldContainCapacityUpdateMessage() {
    String result = EmailContents.travelNowHasCapacityNotification(request);
    assertTrue(result.contains("La capacidad del viaje de su solicitud en lista de espera se ha reducido."));
    assertTrue(result.contains("Viaje: test_viaje"));
  }
}
