package com.nahuelgDev.journeyjoy.dataClasses;

import com.nahuelgDev.journeyjoy.enums.PayPlansFor;

import lombok.Data;

@Data
public class PayPlans {
  private Double price;
  private PayPlansFor planFor;
}
