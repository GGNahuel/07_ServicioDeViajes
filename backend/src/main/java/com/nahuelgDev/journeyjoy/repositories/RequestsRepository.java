package com.nahuelgDev.journeyjoy.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.enums.RequestState;

@Repository
public interface RequestsRepository extends MongoRepository<Requests, String> {
  @Query("{associatedTravel: ?0}")
  List<Requests> findByAssociatedTravelId(String travelId);

  @Query("{$and: [ {associatedTravel: ?0}, {state: ?1} ]}")
  List<Requests> findByAssociatedTravelIdAndState(String travelId, RequestState state);

  @Query("{'email.email': ?0}")
  List<Requests> findByEmail(String email);
}
