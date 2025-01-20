package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Travels;

public interface TravelsService_I {
  public List<Travels> getAll();
  public Travels getById(String id);
  public Travels create(Travels travelToCreate);
  public Travels update(Travels updatedTravel);
  public String delete(String id);
}
