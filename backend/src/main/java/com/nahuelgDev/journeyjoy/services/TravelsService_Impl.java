package com.nahuelgDev.journeyjoy.services;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkFieldsHasContent;
import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkStringIsAlphaNumeric;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nahuelgDev.journeyjoy.collections.Images;
import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dataClasses.Destinies;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.exceptions.InvalidOperationException;
import com.nahuelgDev.journeyjoy.repositories.Custom_TravelRepository;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.TravelsService_I;
import com.nahuelgDev.journeyjoy.utilities.Verifications.Field;

@Service
public class TravelsService_Impl implements TravelsService_I{
  private final TravelsRepository travelsRepo;
  private final Custom_TravelRepository customRepo;
  private final ImagesService_Impl imagesService;

  public TravelsService_Impl(TravelsRepository repository, Custom_TravelRepository customRepo, ImagesService_Impl imagesService_Impl) {
    this.travelsRepo = repository;
    this.customRepo = customRepo;
    this.imagesService = imagesService_Impl;
  }
  
  @Override
  public List<Travels> getAll() {
    return travelsRepo.findAll();
  }
  
  @Override
  public Travels getById(String id) {
    checkFieldsHasContent(new Field("id", id));

    return travelsRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("plan de viaje", id, "id")
    );
  }

  @Override
  public List<Travels> getByCapacityLeft(Boolean wantCapacity) {
    checkFieldsHasContent(new Field("valor del filtro de capacidad", wantCapacity));
    return wantCapacity ? travelsRepo.findByHasCapacityLeft() : travelsRepo.findByNoCapacityLeft();
  }

  @Override
  public List<Travels> search(Boolean available, Integer desiredCapacity, String place, Integer minDays, Integer maxDays) throws Exception {
    checkStringIsAlphaNumeric(new Field("lugar", place));

    return customRepo.search(available, desiredCapacity, place, minDays, maxDays);
  }

  @Override
  public Travels create(Travels travelToCreate, MultipartFile[] images) throws Exception {
    checkFieldsHasContent(new Field("viaje", travelToCreate));
    checkFieldsHasContent(new Field("lugar/es", travelToCreate.getDestinies()), new Field("imagen/es", images));

    checkStringIsAlphaNumeric(
      new Field("nombre", travelToCreate.getName())
    );
    for (Destinies destiny : travelToCreate.getDestinies()) {
      checkStringIsAlphaNumeric(new Field("nombre del lugar", destiny.getPlace()));
      checkFieldsHasContent(new Field("lugar de hospedaje", destiny.getStayPlaceId()));
    }

    checkFieldsHasContent(
      new Field("nombre", travelToCreate.getName()),
      new Field("capacidad máxima", travelToCreate.getMaxCapacity()),
      new Field("duración en días", travelToCreate.getLongInDays()),
      new Field("fechas disponibles", travelToCreate.getAvailableDates()),
      new Field("planes de pago", travelToCreate.getPayPlans())
    );

    List<Images> imagesToSave = new ArrayList<>();
    for (MultipartFile image : images) {
      imagesToSave.add(imagesService.add(image));
    }

    Travels newTravel = travelToCreate;
    newTravel.setIsAvailable(true);
    newTravel.setCurrentCapacity(0);
    newTravel.setImages(imagesToSave);

    return travelsRepo.save(newTravel);
  }

  @Override
  public Travels update(Travels updatedTravel, MultipartFile[] images) throws Exception {
    checkFieldsHasContent(new Field("viaje a actualizar", updatedTravel));
    checkFieldsHasContent(new Field("lugar/es", updatedTravel.getDestinies()));

    checkStringIsAlphaNumeric(
      new Field("nombre", updatedTravel.getName())
    );
    for (Destinies destiny : updatedTravel.getDestinies()) {
      checkStringIsAlphaNumeric(new Field("nombre del lugar", destiny.getPlace()));
      checkFieldsHasContent(new Field("lugar de hospedaje", destiny.getStayPlaceId()));
    }

    checkFieldsHasContent(new Field("id", updatedTravel.getId()));

    travelsRepo.findById(updatedTravel.getId()).orElseThrow(
      () -> new DocumentNotFoundException("plan de viaje", updatedTravel.getId(), "id")
    );

    if (images.length != 0) {
      List<Images> imagesToSave = new ArrayList<>();
      for (MultipartFile image : images) {
        imagesToSave.add(imagesService.add(image));
      }

      updatedTravel.setImages(imagesToSave);
    }

    return travelsRepo.save(updatedTravel);
  }

  @Override
  public String addReview(String travelId, Reviews newReview) throws Exception {
    checkFieldsHasContent(new Field("reseña", newReview));

    checkStringIsAlphaNumeric(
      new Field("autor", newReview.getUserName()),
      new Field("comentario", newReview.getComment())
    );

    checkFieldsHasContent(
      new Field("id del viaje", travelId), 
      new Field("nombre del autor de la reseña", newReview.getUserName()),
      new Field("valoración de la reseña", newReview.getRating())
    );

    Travels travel = travelsRepo.findById(travelId).orElseThrow(
      () -> new DocumentNotFoundException("plan de viaje", travelId, "id")
    );

    List<Reviews> reviewsInTravel = travel.getReviews();

    boolean usernameAlreadyHasReview = reviewsInTravel.stream().anyMatch(review -> review.getUserName().equals(newReview.getUserName()));
    if (usernameAlreadyHasReview) throw new InvalidOperationException("Ya existe una reseña para el nombre ingresado");

    reviewsInTravel.add(newReview);

    Double travelRating = 0.0;
    for (Reviews review : reviewsInTravel) {
      travelRating += review.getRating();
    }
    travelRating /= reviewsInTravel.size();

    travel.setReviews(reviewsInTravel);
    travel.setRating(travelRating);
    travelsRepo.save(travel);

    return "Gracias por valorar el viaje";
  }

  @Override
  public String delete(String id) {
    checkFieldsHasContent(new Field("id", id));
  
    travelsRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("plan de viaje", id, "id")
    );

    travelsRepo.deleteById(id);

    return "Operación realizada con éxito";
  }
}
