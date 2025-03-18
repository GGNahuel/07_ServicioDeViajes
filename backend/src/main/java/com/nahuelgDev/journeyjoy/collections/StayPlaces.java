package com.nahuelgDev.journeyjoy.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Document
public class StayPlaces {
  @Id
  private String id;
  private String from;
  private String name;
  private String description;
  private Double rating;

  public StayPlaces(String id) {
    this.id = id;
  }
}
