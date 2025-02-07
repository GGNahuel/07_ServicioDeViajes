package com.nahuelgDev.journeyjoy.repositories;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Travels;

public interface Custom_TravelRepository {
  List<Travels> search(Boolean available, Integer desiredCapacity, String place, Integer minDays, Integer maxDays);
}
