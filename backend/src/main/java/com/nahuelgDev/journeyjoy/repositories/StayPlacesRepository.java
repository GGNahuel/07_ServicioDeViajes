package com.nahuelgDev.journeyjoy.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;

@Repository
public interface StayPlacesRepository extends MongoRepository<StayPlaces, String> {
  @Query("{ $and: [ { name: { $regex: ?0, $options: 'i' } }, { from: { $regex: ?1, $options: 'i' } } ] }")
  List<StayPlaces> searchByNameAndFromAttr(String name, String from);
}
