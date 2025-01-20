package com.nahuelgDev.journeyjoy.dtos;

import com.nahuelgDev.journeyjoy.enums.Transports;

import lombok.Data;

@Data
public class DestiniesDto {
  private String place;
  private Integer leaveDay;
  private Integer returnDay;
  private Transports transport;

  private StayPlacesDto stayPlace;
}
