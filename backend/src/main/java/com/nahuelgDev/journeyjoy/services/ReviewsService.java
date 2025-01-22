package com.nahuelgDev.journeyjoy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.repositories.ReviewsRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.ReviewsService_I;

@Service
public class ReviewsService implements ReviewsService_I {
  @Autowired ReviewsRepository reviewsRepo;

  public List<Reviews> getAll() {
    return reviewsRepo.findAll();
  }

  public Reviews getById(String id) {
    return reviewsRepo.findById(id).orElse(null);
  }

  public Reviews create(Reviews reviewToCreate) {
    return reviewsRepo.save(reviewToCreate);
  }

  public Reviews update(Reviews updatedReview) {
    Reviews reviewToUpdate = reviewsRepo.findById(updatedReview.getId()).orElse(null);
    
    return reviewToUpdate != null ? reviewsRepo.save(updatedReview) : null;
  }

  public String delete(String id) {
    Reviews reviewToDelete = reviewsRepo.findById(id).orElse(null);

    if (reviewToDelete != null) {
      reviewsRepo.deleteById(id);

      return "Operación realizada con éxito";
    }

    return "No se pudo completar la operación, la entidad a borrar no existe";
  }
}
