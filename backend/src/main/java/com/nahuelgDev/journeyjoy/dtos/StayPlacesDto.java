package com.nahuelgDev.journeyjoy.dtos;

import lombok.Data;

@Data
public class StayPlacesDto {
  private String id;
  private String from;
  private String name;
  private String description;
  private Integer rating;
}
