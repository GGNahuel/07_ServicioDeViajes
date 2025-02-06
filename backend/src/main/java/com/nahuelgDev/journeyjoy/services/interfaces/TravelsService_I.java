package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.collections.Travels;

public interface TravelsService_I {
  List<Travels> getAll();
  Travels getById(String id);
  List<Travels> search(Boolean available, Integer desiredCapacity, String place, Integer minDays, Integer maxDays);
  List<Travels> getByCapacityLeft(boolean wantCapacity);
  Travels create(Travels travelToCreate);
  Travels update(Travels updatedTravel);
  String changeCurrentCapacity(String travelId, Integer relativeCapacity);
  String addReview(String travelId, Reviews newReview);
  String delete(String id);
}
