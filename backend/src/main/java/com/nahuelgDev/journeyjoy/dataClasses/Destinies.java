package com.nahuelgDev.journeyjoy.dataClasses;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.enums.Transports;

import lombok.Data;

@Data
public class Destinies {
  private String place;
  private Integer leaveDay;
  private Integer returnDay;
  private Transports transport;
  private StayPlaces stayPlace;
}
