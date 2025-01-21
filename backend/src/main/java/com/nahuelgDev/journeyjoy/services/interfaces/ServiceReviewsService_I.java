package com.nahuelgDev.journeyjoy.services.interfaces;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.ServiceReviews;

public interface ServiceReviewsService_I {
  public List<ServiceReviews> getAll();
  public ServiceReviews getById(String id);
  public ServiceReviews create(ServiceReviews reviewToCreate);
  public ServiceReviews update(ServiceReviews updatedReview);
  public String delete(String id);
}
