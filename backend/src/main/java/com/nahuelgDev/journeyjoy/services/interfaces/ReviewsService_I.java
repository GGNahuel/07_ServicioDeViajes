package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Reviews;

public interface ReviewsService_I {
  public List<Reviews> getAll();
  public Reviews getById(String id);
  public Reviews create(Reviews reviewToCreate);
  public Reviews update(Reviews updatedReview);
  public String delete(String id);
}
