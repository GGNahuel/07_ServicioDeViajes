package com.nahuelgDev.journeyjoy.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
  public Reviews create(@RequestBody Reviews reviewToCreate, @RequestParam("file") MultipartFile image) throws IOException {
    return reviewsService.create(reviewToCreate, image);
  }

  @PutMapping("")
  public Reviews update(@RequestBody Reviews updatedReview, @RequestParam("file") MultipartFile image) throws IOException {
    return reviewsService.update(updatedReview, image);
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable String id) {
    return reviewsService.delete(id);
  }
}
