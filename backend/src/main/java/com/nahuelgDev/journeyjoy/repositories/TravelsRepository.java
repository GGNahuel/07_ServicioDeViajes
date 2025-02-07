package com.nahuelgDev.journeyjoy.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.Travels;

@Repository
public interface TravelsRepository extends MongoRepository<Travels, String> {

  Optional<Travels> findByName(String name);

  @Query("{ $expr: { $lt: ['$currentCapacity', '$maxCapacity'] } }")
  List<Travels> findByHasCapacityLeft();

  @Query("{ currentCapacity: '$maxCapacity' }")
  List<Travels> findByNoCapacityLeft();
}
