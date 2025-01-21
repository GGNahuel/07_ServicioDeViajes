package com.nahuelgDev.journeyjoy.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.Travels;

@Repository
public interface TravelsRepository extends MongoRepository<Travels, String> {
  @Query("{ $and: [ " +
    "{ $or: [ { :#{#available} : null }, { isAvailable: :#{#available} } ] }, " +
    "{ $or: [ { $expr: { $gte: [ :#{#desiredCapacity}, {$subtract: ['$maxCapacity', '$currentCapacity']} ] } }, { :#{#desiredCapacity} : null } ] }, " +
    "{ $or: [ { 'destinies.place': :#{#place} }, { :#{#place} : '' } ] }, " +
    "{ longInDays: { $lte: :#{#maxDays} } }, " +
    "{ longInDays: { $gte: :#{#minDays} } } " +
  "]}")
  List<Travels> search(
    @Param("available") Boolean available, 
    @Param("desiredCapacity") Integer desiredCapacity,
    @Param("place") String place,
    @Param("maxDays") String maxDays,
    @Param("minDays") String minDays
  );
}
