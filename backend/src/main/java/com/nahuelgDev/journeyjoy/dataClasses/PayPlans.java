package com.nahuelgDev.journeyjoy.dataClasses;

import com.nahuelgDev.journeyjoy.enums.PayPlansType;

import lombok.Data;

@Data
public class PayPlans {
  private Double price;
  private PayPlansType planFor;
}
