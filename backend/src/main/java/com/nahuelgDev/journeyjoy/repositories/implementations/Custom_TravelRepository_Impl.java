package com.nahuelgDev.journeyjoy.repositories.implementations;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.repositories.Custom_TravelRepository;

@Repository
public class Custom_TravelRepository_Impl implements Custom_TravelRepository {
  @Autowired MongoTemplate mongoTemplate;
  
  @Override
  public List<Travels> search(Boolean available, Integer desiredCapacity, String place, Integer minDays, Integer maxDays) {
    Query query = new Query();

    if (available != null) {
      query.addCriteria(Criteria.where("isAvailable").is(available));
    }
    if (desiredCapacity != null) {
      query.addCriteria(Criteria.expr(() ->
        new Document("$lte", List.of(
          desiredCapacity, 
          new Document("$subtract", List.of("$maxCapacity", "$currentCapacity"))
        ))
      ));
    }
    if (place != null && !place.isBlank()) {
      query.addCriteria(Criteria.where("destinies.place").regex(".*" + place + ".*", "i")); // Búsqueda por lugar
    }
    if (minDays != null && maxDays != null) {
      query.addCriteria(Criteria.where("longInDays").gte(minDays).lte(maxDays));
    } else {
      if (minDays != null) {
        query.addCriteria(Criteria.where("longInDays").gte(minDays));
      }
      if (maxDays != null) {
        query.addCriteria(Criteria.where("longInDays").lte(maxDays));
      }
    }

    return mongoTemplate.find(query, Travels.class);
  }
}
