package com.nahuelgDev.journeyjoy.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nahuelgDev.journeyjoy.collections.Admin;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
  Optional<Admin> findByUsername(String username);
}
