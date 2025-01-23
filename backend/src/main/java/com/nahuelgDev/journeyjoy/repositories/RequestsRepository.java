package com.nahuelgDev.journeyjoy.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.Requests;

@Repository
public interface RequestsRepository extends MongoRepository<Requests, String> {
  @Query("{associatedTravel.name: :#{travelName}}")
  List<Requests> findByAssociatedTravelName(@Param("travelName") String travelName);
}
