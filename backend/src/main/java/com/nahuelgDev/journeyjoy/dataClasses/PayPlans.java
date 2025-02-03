package com.nahuelgDev.journeyjoy.dataClasses;

import com.nahuelgDev.journeyjoy.enums.PayPlansType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class PayPlans {
  private Double price;
  private PayPlansType planFor;
}
