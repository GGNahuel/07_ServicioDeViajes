package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.collections.Travels;

public interface TravelsService_I {
  List<Travels> getAll();
  Travels getById(String id);
  List<Travels> search(Boolean available, Integer desiredCapacity, String place, Integer minDays, Integer maxDays) throws Exception;
  List<Travels> getByCapacityLeft(Boolean wantCapacity);
  Travels create(Travels travelToCreate) throws Exception;
  Travels update(Travels updatedTravel) throws Exception;
  String addReview(String travelId, Reviews newReview) throws Exception;
  String delete(String id);
}
