package com.nahuelgDev.journeyjoy.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.services.interfaces.ReviewsService_I;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {
  @Autowired ReviewsService_I reviewsService;

  @GetMapping("")
  public List<Reviews> getAll() {
    return reviewsService.getAll();
  }

  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Reviews create(@RequestPart("review") Reviews reviewToCreate, @RequestPart("file") MultipartFile image) throws IOException {
    return reviewsService.create(reviewToCreate, image);
  }

  @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Reviews update(@RequestPart("review") Reviews updatedReview, @RequestPart("file") MultipartFile image) throws IOException {
    return reviewsService.update(updatedReview, image);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public String delete(@PathVariable String id) {
    return reviewsService.delete(id);
  }
}
