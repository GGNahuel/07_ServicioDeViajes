package com.nahuelgDev.journeyjoy.collections;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nahuelgDev.journeyjoy.dataClasses.PayPlans;
import com.nahuelgDev.journeyjoy.dataClasses.Person;
import com.nahuelgDev.journeyjoy.enums.RequestState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Document
public class Requests {
  @Id
  private String id;
  private PayPlans selectedPlan;
  private List<Person> persons;
  private Emails email;
  private RequestState state;
  private Double amountPaid;
  private Double totalPrice;
  
  @DBRef
  private Travels associatedTravel;
  private LocalDate selectedDate;
}
