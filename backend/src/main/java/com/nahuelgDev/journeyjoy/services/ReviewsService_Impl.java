package com.nahuelgDev.journeyjoy.services;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.*;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.repositories.ReviewsRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.ReviewsService_I;

@Service
public class ReviewsService_Impl implements ReviewsService_I {
  @Autowired ReviewsRepository reviewsRepo;
  @Autowired ImagesService_Impl imageService;

  public List<Reviews> getAll() {
    return reviewsRepo.findAll();
  }

  public Reviews getById(String id) {
    checkFieldsHasContent(new Field("id", id));

    return reviewsRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("valoración", id, "id")
    );
  }

  public Reviews create(Reviews reviewToCreate, MultipartFile image) throws IOException {
    checkFieldsHasContent(new Field("reseña", reviewToCreate));
    checkFieldsHasContent(
      new Field("autor", reviewToCreate.getUserName()),
      new Field("puntaje", reviewToCreate.getRating()),
      new Field("foto", image)
    );
    
    reviewToCreate.setUserImage(imageService.add(image));
    
    return reviewsRepo.save(reviewToCreate);
  }
  
  public Reviews update(Reviews updatedReview, MultipartFile image) throws IOException {
    checkFieldsHasContent(new Field("reseña", updatedReview));
    checkFieldsHasContent(
      new Field("id", updatedReview.getId()),
      new Field("foto previa", updatedReview.getUserImage()),
      new Field("foto en archivo", image)
    );
    
    reviewsRepo.findById(updatedReview.getId()).orElseThrow(
      () -> new DocumentNotFoundException("valoración", updatedReview.getId(), "id")
    );
    
    updatedReview.setUserImage(imageService.update(updatedReview.getUserImage().getId(), image));
    
    return reviewsRepo.save(updatedReview);
  }

  public String delete(String id) {
    checkFieldsHasContent(new Field("id", id));

    reviewsRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("valoración", id, "id")
    );

    reviewsRepo.deleteById(id);

    return "Operación realizada con éxito";
  }
}
