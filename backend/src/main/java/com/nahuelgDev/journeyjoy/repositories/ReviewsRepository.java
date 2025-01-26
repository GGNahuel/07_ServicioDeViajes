package com.nahuelgDev.journeyjoy.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.Reviews;

@Repository
public interface ReviewsRepository extends MongoRepository<Reviews, String> {
  Optional<Reviews> findByUserName(String username);
}
