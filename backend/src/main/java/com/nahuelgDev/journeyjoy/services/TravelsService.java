package com.nahuelgDev.journeyjoy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.TravelsService_I;
import static com.nahuelgDev.journeyjoy.utilities.Verifications.*;

@Service
public class TravelsService implements TravelsService_I{
  @Autowired TravelsRepository travelsRepo;

  public List<Travels> getAll() {
    
    return travelsRepo.findAll();
  }
  
  public Travels getById(String id) {
    checkFieldsHasContent(new Field("id", id));

    return travelsRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("plan de viaje", id, "id")
    );
  }

  public List<Travels> search(Boolean available, Integer desiredCapacity, String place, String minDays, String maxDays) {
    return travelsRepo.search(available, desiredCapacity, place, minDays, maxDays);
  }

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

  public Travels update(Travels updatedTravel) {
    checkFieldsHasContent(new Field("id", updatedTravel.getId()));

    Travels travelToUpdate = travelsRepo.findById(updatedTravel.getId()).orElseThrow(
      () -> new DocumentNotFoundException("plan de viaje", updatedTravel.getId(), "id")
    );

    updatedTravel.setId(travelToUpdate.getId());

    return travelsRepo.save(updatedTravel);
  }

  public String delete(String id) {
    checkFieldsHasContent(new Field("id", id));
  
    travelsRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("plan de viaje", id, "id")
    );

    travelsRepo.deleteById(id);

    return "Operación realizada con éxito";
  }
}
