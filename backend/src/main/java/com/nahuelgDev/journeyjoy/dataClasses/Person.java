package com.nahuelgDev.journeyjoy.dataClasses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Person {
  private String name;
  private Integer age;
  private long identificationNumber;
  private long contactPhone;
}
