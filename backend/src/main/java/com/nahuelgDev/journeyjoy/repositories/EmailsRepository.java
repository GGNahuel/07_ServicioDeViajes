package com.nahuelgDev.journeyjoy.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.Emails;

@Repository
public interface EmailsRepository extends MongoRepository<Emails, String> {
  Optional<Emails> findByEmail(String email);
}
