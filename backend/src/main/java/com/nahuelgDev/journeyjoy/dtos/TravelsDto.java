package com.nahuelgDev.journeyjoy.dtos;

import java.time.LocalDate;
import java.util.List;

import com.nahuelgDev.journeyjoy.dataClasses.PayPlans;

import lombok.Data;

@Data
public class TravelsDto {
  private String id;
  private Integer longInDays;
  private Integer maxCapacity;
  private Integer currentCapacity;
  private Boolean isAvailable;
  private List<LocalDate> availableDates;

  private List<DestiniesDto> destinies;
  private List<PayPlans> payPlans;
}
