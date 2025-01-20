package com.nahuelgDev.journeyjoy.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.Travels;

@Repository
public interface TravelsRepository extends MongoRepository<Travels, String> {
  
}
