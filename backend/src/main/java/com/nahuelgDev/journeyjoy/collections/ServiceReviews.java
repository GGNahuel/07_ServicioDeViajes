package com.nahuelgDev.journeyjoy.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class ServiceReviews {
  @Id
  private String id;
  private String userName;
  private String comment;
  private Integer rating;
}
