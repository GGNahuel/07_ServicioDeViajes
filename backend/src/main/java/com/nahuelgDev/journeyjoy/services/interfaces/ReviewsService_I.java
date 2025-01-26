package com.nahuelgDev.journeyjoy.services.interfaces;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nahuelgDev.journeyjoy.collections.Reviews;

public interface ReviewsService_I {
  List<Reviews> getAll();
  Reviews getById(String id);
  Reviews create(Reviews reviewToCreate, MultipartFile image) throws IOException;
  Reviews update(Reviews updatedReview, MultipartFile image) throws IOException;
  String delete(String id);
}
