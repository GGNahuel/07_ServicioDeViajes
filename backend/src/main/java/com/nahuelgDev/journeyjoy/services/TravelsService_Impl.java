package com.nahuelgDev.journeyjoy.services;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkFieldsHasContent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.exceptions.InvalidOperationException;
import com.nahuelgDev.journeyjoy.repositories.Custom_TravelRepository;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.TravelsService_I;
import com.nahuelgDev.journeyjoy.utilities.Verifications.Field;

@Service
public class TravelsService_Impl implements TravelsService_I{
  @Autowired TravelsRepository travelsRepo;
  @Autowired Custom_TravelRepository customRepo;
  
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
  public List<Travels> search(Boolean available, Integer desiredCapacity, String place, Integer minDays, Integer maxDays) {
    return customRepo.search(available, desiredCapacity, place, minDays, maxDays);
  }

  @Override
  public Travels create(Travels travelToCreate) {
    checkFieldsHasContent(new Field("viaje", travelToCreate));
    checkFieldsHasContent(
      new Field("nombre", travelToCreate.getName()),
      new Field("capacidad máxima", travelToCreate.getMaxCapacity()),
      new Field("lugar/es", travelToCreate.getDestinies()),
      new Field("duración en días", travelToCreate.getLongInDays()),
      new Field("fechas disponibles", travelToCreate.getAvailableDates()),
      new Field("planes de pago", travelToCreate.getPayPlans())
    );

    Travels newTravel = travelToCreate;
    newTravel.setIsAvailable(true);
    newTravel.setCurrentCapacity(0);

    return travelsRepo.save(newTravel);
  }

  @Override
  public Travels update(Travels updatedTravel) {
    checkFieldsHasContent(new Field("viaje a actualizar", updatedTravel));
    checkFieldsHasContent(new Field("id", updatedTravel.getId()));

    travelsRepo.findById(updatedTravel.getId()).orElseThrow(
      () -> new DocumentNotFoundException("plan de viaje", updatedTravel.getId(), "id")
    );

    return travelsRepo.save(updatedTravel);
  }

  @Override
  public String addReview(String travelId, Reviews newReview) {
    checkFieldsHasContent(new Field("reseña", newReview));
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
