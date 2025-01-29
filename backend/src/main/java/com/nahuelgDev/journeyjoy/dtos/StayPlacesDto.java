package com.nahuelgDev.journeyjoy.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StayPlacesDto {
  private String id;
  private String from;
  private String name;
  private String description;
  private Double rating;
}
