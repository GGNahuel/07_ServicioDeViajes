package com.nahuelgDev.journeyjoy.test_repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.nahuelgDev.journeyjoy.collections.Emails;
import com.nahuelgDev.journeyjoy.repositories.EmailsRepository;

@DataMongoTest
public class Test_EmailsRepo {
  
  @Autowired EmailsRepository repository;

  private Emails email1, email2;

  @BeforeEach
  void setUp() {
    email1 = Emails.builder().id("1").email("example1@gmail.com").owner("owner1").build();
    email2 = Emails.builder().id("2").email("example2@gmail.com").owner("owner2").build();

    repository.saveAll(List.of(email1, email2));
  }

  @Test
  void findByEmail_returnsExpected() {
    Optional<Emails> expected = Optional.of(email1);

    Optional<Emails> actual = repository.findByEmail("example1@gmail.com");

    assertEquals(expected, actual);
  }
}
