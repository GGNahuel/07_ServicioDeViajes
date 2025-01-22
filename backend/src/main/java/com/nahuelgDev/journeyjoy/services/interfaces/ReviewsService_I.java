package com.nahuelgDev.journeyjoy.services.interfaces;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nahuelgDev.journeyjoy.collections.Reviews;

public interface ReviewsService_I {
  public List<Reviews> getAll();
  public Reviews getById(String id);
  public Reviews create(Reviews reviewToCreate, MultipartFile image) throws IOException;
  public Reviews update(Reviews updatedReview, MultipartFile image) throws IOException;
  public String delete(String id);
}
