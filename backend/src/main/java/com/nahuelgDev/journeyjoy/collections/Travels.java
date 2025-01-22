package com.nahuelgDev.journeyjoy.collections;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nahuelgDev.journeyjoy.dataClasses.Destinies;
import com.nahuelgDev.journeyjoy.dataClasses.PayPlans;

import lombok.Data;

@Data
@Document
public class Travels {
  @Id
  private String id;
  @Indexed(unique = true)
  private String name;
  private Integer longInDays;
  private Integer maxCapacity;
  private Integer currentCapacity;
  private Boolean isAvailable;
  private List<LocalDate> availableDates;

  private List<Destinies> destinies;
  private List<PayPlans> payPlans;
  private List<Reviews> reviews;
}
