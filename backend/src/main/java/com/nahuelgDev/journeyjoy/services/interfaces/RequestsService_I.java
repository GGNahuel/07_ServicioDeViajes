package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.dtos.RequestsUpdateDto;

public interface RequestsService_I {
  public List<Requests> getAll();
  public Requests getById(String id);
  public List<Requests> getByTravelName(String travelName);
  public Requests create(Requests requestToCreate);
  public Requests update(RequestsUpdateDto updatedRequest);
  public String addPayment(String id, Double amount);
  public String cancelRequest(String id);
}
