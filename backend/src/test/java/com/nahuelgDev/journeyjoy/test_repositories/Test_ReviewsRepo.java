package com.nahuelgDev.journeyjoy.test_repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.repositories.ReviewsRepository;

@DataMongoTest
public class Test_ReviewsRepo {
  
  @Autowired ReviewsRepository repository;

  private Reviews review1, review2;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
    review1 = Reviews.builder().id("1").userName("userA").comment("goodComment").rating(4.5).build();
    review2 = Reviews.builder().id("2").userName("userB").comment("badComment").rating(2.0).build();

    repository.saveAll(List.of(review1, review2));
  }

  @Test
  void findByUsername_returnsExpected() {
    Optional<Reviews> result = repository.findByUserName("userA");

    assertTrue(result.isPresent());
    assertEquals(review1, result.get());
  }

  @Test void findByUsername_notFound() {
    Optional<Reviews> result = repository.findByUserName("userZ");

    assertTrue(!result.isPresent());
  }
}
