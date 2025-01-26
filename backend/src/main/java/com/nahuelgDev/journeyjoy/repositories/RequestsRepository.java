package com.nahuelgDev.journeyjoy.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.enums.RequestState;

@Repository
public interface RequestsRepository extends MongoRepository<Requests, String> {
  @Query("{associatedTravel.name: :#{travelName}}")
  List<Requests> findByAssociatedTravelName(@Param("travelName") String travelName);

  @Query("{$and: [ {associatedTravel.name: :#{travelName}}, {state: :#{state}} ]}")
  List<Requests> findByAssociatedTravelNameAndState(@Param("travelName") String travelName,@Param("state") RequestState state);

  List<Requests> findByEmail(String email);
}
