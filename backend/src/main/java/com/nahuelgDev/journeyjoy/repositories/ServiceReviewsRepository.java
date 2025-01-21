package com.nahuelgDev.journeyjoy.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.ServiceReviews;

@Repository
public interface ServiceReviewsRepository extends MongoRepository<ServiceReviews, String> {
  
}
