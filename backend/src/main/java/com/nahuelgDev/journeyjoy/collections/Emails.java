package com.nahuelgDev.journeyjoy.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Document
public class Emails {
  @Id
  private String id;
  @Indexed(unique = true)
  private String email;
  private String owner;
}
