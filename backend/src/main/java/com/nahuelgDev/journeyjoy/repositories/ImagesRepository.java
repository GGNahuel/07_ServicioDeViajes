package com.nahuelgDev.journeyjoy.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nahuelgDev.journeyjoy.collections.Images;

public interface ImagesRepository extends MongoRepository<Images, String> {
  
}
