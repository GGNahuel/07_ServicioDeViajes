package com.nahuelgDev.journeyjoy.test_repositories;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.nahuelgDev.journeyjoy.collections.Emails;
import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dataClasses.PayPlans;
import com.nahuelgDev.journeyjoy.enums.PayPlansType;
import com.nahuelgDev.journeyjoy.enums.RequestState;
import com.nahuelgDev.journeyjoy.repositories.RequestsRepository;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;

@DataMongoTest
public class Test_RequestsRepo {
  
  @Autowired RequestsRepository repository;
  @Autowired TravelsRepository travelsRepository;

  private Requests request1, request2;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
    
    travelsRepository.deleteAll();
    Travels travel1 = Travels.builder().id("1").name("travel1").build();
    Travels travel2 = Travels.builder().id("2").name("travel2").build();
    travelsRepository.saveAll(List.of(travel1, travel2));

    request1 = Requests.builder()
      .id("1").amountPaid(200.0)
      .selectedPlan(PayPlans.builder().price(200.0).planFor(PayPlansType.individual).build())
      .associatedTravel(travel1)
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

    repository.saveAll(List.of(request1, request2));
  }

  @Test
  void findByAssociatedTravelName_returnsExpectedList() {
    List<Requests> expected = List.of(request1);
    
    assertIterableEquals(expected, repository.findByAssociatedTravelId("1"));
    assertIterableEquals(List.of(), repository.findByAssociatedTravelId("4"));
  }

  @Test
  void findByAssociatedTravelNameAndState_returnsExpectedList() {
    List<Requests> expected = List.of(request1);

    assertIterableEquals(expected, repository.findByAssociatedTravelIdAndState("1", RequestState.completePayment));
    assertIterableEquals(List.of(), repository.findByAssociatedTravelIdAndState("1", RequestState.inWaitList));
    assertIterableEquals(List.of(), repository.findByAssociatedTravelIdAndState("2", RequestState.completePayment));
  }

  @Test
  void findByEmail_returnsExpectedList() {
    List<Requests> expected = List.of(request1, request2);

    assertIterableEquals(expected, repository.findByEmail("email@gmail.com"));
    assertIterableEquals(List.of(), repository.findByEmail("email@gmail"));
  }
}
