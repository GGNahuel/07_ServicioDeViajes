package com.nahuelgDev.journeyjoy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.services.ReviewsService;

@RestController
@RequestMapping("/api/service_reviews")
public class ReviewsController {
  @Autowired ReviewsService reviewsService;

  @GetMapping("")
  public List<Reviews> getAll() {
    return reviewsService.getAll();
  }

  @PostMapping("")
  public Reviews create(@RequestBody Reviews reviewToCreate) {
    return reviewsService.create(reviewToCreate);
  }

  @PutMapping("")
  public Reviews update(@RequestBody Reviews updatedReview) {
    return reviewsService.update(updatedReview);
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable String id) {
    return reviewsService.delete(id);
  }
}
