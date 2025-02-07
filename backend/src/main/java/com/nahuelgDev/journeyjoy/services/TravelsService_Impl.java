package com.nahuelgDev.journeyjoy.services;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.exceptions.InvalidOperationException;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.TravelsService_I;
import com.nahuelgDev.journeyjoy.utilities.Verifications.Field;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.*;

@Service
public class TravelsService_Impl implements TravelsService_I{
  @Autowired TravelsRepository travelsRepo;
  @Autowired MongoTemplate mongoTemplate;
  
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
  public List<Travels> getByCapacityLeft(boolean wantCapacity) {
    return wantCapacity ? travelsRepo.findByHasCapacityLeft() : travelsRepo.findByNoCapacityLeft();
  }

  @Override
  public List<Travels> search(Boolean available, Integer desiredCapacity, String place, Integer minDays, Integer maxDays) {
    Query query = new Query();

    if (available != null) {
      query.addCriteria(Criteria.where("isAvailable").is(available));
    }
    if (desiredCapacity != null) {
      query.addCriteria(Criteria.expr(() ->
        new Document("$lte", List.of(
          desiredCapacity, 
          new Document("$subtract", List.of("$maxCapacity", "$currentCapacity"))
        ))
      ));
    }
    if (place != null && !place.isBlank()) {
      query.addCriteria(Criteria.where("destinies.place").regex(".*" + place + ".*", "i")); // Búsqueda por lugar
    }
    if (minDays != null) {
      query.addCriteria(Criteria.where("longInDays").gte(minDays));
    }
    if (maxDays != null) {
      query.addCriteria(Criteria.where("longInDays").lte(maxDays));
    }

    return mongoTemplate.find(query, Travels.class);
  }

  @Override
  public Travels create(Travels travelToCreate) {
    checkFieldsHasContent(
      new Field("nombre", travelToCreate.getName()),
      new Field("capacidad máxima", travelToCreate.getMaxCapacity()),
      new Field("lugar/es", travelToCreate.getDestinies()),
      new Field("duración en días", travelToCreate.getLongInDays()),
      new Field("fechas disponibles", travelToCreate.getAvailableDates()),
      new Field("planes de pago", travelToCreate.getPayPlans())
    );

    travelToCreate.setIsAvailable(true);
    travelToCreate.setCurrentCapacity(0);

    return travelsRepo.save(travelToCreate);
  }

  @Override
  public Travels update(Travels updatedTravel) {
    checkFieldsHasContent(new Field("id", updatedTravel.getId()));

    Travels travelToUpdate = travelsRepo.findById(updatedTravel.getId()).orElseThrow(
      () -> new DocumentNotFoundException("plan de viaje", updatedTravel.getId(), "id")
    );

    updatedTravel.setId(travelToUpdate.getId());

    return travelsRepo.save(updatedTravel);
  }

  @Override
  public String changeCurrentCapacity(String travelId, Integer relativeCapacity) {
    checkFieldsHasContent(new Field("id del viaje", travelId), new Field("cambio en capacidad", relativeCapacity));

    Travels travel = travelsRepo.findById(travelId).orElseThrow(
      () -> new DocumentNotFoundException("plan de viaje", travelId, "id")
    );

    int currentCapacity = travel.getCurrentCapacity();
    int newCapacity = currentCapacity + relativeCapacity;

    if (newCapacity > travel.getMaxCapacity()) return "No hay cupo para la cantidad solicitada";
    if (newCapacity < 0) return "Ocurrió un error al dar de baja. La nueva cantidad es menor a 0"; 
    // Esto en realidad sería un log (al cliente retornaría otro msg)

    travel.setCurrentCapacity(newCapacity);
    travelsRepo.save(travel);
    return "Solicitud procesada con éxito";
  }

  @Override
  public String addReview(String travelId, Reviews newReview) {
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
