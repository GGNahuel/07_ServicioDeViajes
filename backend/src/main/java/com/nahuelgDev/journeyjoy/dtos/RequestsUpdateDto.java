package com.nahuelgDev.journeyjoy.dtos;

import java.time.LocalDate;
import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dataClasses.PayPlans;
import com.nahuelgDev.journeyjoy.dataClasses.Persons;

import lombok.Data;

@Data
public class RequestsUpdateDto {
  private String id;
  private PayPlans selectedPlan;
  private List<Persons> persons;
  
  // en el modelMapper hacer que skipee esta propiedad en el mapeo
  private Travels associatedTravel;
  private LocalDate selectedDate;
}
