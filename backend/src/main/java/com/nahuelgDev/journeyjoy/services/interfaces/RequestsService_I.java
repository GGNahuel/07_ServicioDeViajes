package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.dtos.RequestsUpdateDto;

public interface RequestsService_I {
  List<Requests> getAll();
  Requests getById(String id);
  List<Requests> getByTravelName(String travelName);
  List<Requests> getByEmail(String email);
  Requests create(Requests requestToCreate);
  Requests update(RequestsUpdateDto updatedRequest);
  String addPayment(String id, Double amount);
  String cancelRequest(String id);
}
