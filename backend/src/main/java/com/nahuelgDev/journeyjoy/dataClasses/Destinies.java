package com.nahuelgDev.journeyjoy.dataClasses;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.enums.Transports;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class Destinies {
  private String place;
  private Integer leaveDay;
  private Integer returnDay;
  private Transports transport;

  @DBRef
  private StayPlaces stayPlaceId;
}
