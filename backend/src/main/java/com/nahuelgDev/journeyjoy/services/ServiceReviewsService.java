package com.nahuelgDev.journeyjoy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.collections.ServiceReviews;
import com.nahuelgDev.journeyjoy.repositories.ServiceReviewsRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.ServiceReviewsService_I;

@Service
public class ServiceReviewsService implements ServiceReviewsService_I {
  @Autowired ServiceReviewsRepository reviewsRepo;

  public List<ServiceReviews> getAll() {
    return reviewsRepo.findAll();
  }

  public ServiceReviews getById(String id) {
    return reviewsRepo.findById(id).orElse(null);
  }

  public ServiceReviews create(ServiceReviews reviewToCreate) {
    return reviewsRepo.save(reviewToCreate);
  }

  public ServiceReviews update(ServiceReviews updatedReview) {
    ServiceReviews reviewToUpdate = reviewsRepo.findById(updatedReview.getId()).orElse(null);
    
    return reviewToUpdate != null ? reviewsRepo.save(updatedReview) : null;
  }

  public String delete(String id) {
    ServiceReviews reviewToDelete = reviewsRepo.findById(id).orElse(null);

    if (reviewToDelete != null) {
      reviewsRepo.deleteById(id);

      return "Operación realizada con éxito";
    }

    return "No se pudo completar la operación, la entidad a borrar no existe";
  }
}
