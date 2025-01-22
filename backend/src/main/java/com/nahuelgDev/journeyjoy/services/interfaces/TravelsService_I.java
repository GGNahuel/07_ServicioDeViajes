package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.collections.Travels;

public interface TravelsService_I {
  public List<Travels> getAll();
  public Travels getById(String id);
  public List<Travels> search(Boolean available, Integer desiredCapacity, String place, String minDays, String maxDays);
  public Travels create(Travels travelToCreate);
  public Travels update(Travels updatedTravel);
  public String changeCurrentCapacity(String travelId, Integer relativeCapacity);
  public String addReview(String travelId, Reviews newReview);
  public String delete(String id);
}
