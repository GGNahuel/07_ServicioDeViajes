package com.nahuelgDev.journeyjoy.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;

@Repository
public interface StayPlacesRepository extends MongoRepository<StayPlaces, String> {
  
}
