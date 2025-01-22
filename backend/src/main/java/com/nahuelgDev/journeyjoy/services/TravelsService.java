package com.nahuelgDev.journeyjoy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.TravelsService_I;

@Service
public class TravelsService implements TravelsService_I{
  @Autowired TravelsRepository travelsRepo;

  public List<Travels> getAll() {
    return travelsRepo.findAll();
  }

  public Travels getById(String id) {
    return travelsRepo.findById(id).orElse(null);
  }

  public List<Travels> search(Boolean available, Integer desiredCapacity, String place, String minDays, String maxDays) {
    return travelsRepo.search(available, desiredCapacity, place, minDays, maxDays);
  }

  public Travels create(Travels travelToCreate) {
    return travelsRepo.save(travelToCreate);
  }

  public Travels update(Travels updatedTravel) {
    Travels travelToUpdate = travelsRepo.findById(updatedTravel.getId()).orElse(null);

    if (travelToUpdate != null) {
      updatedTravel.setId(travelToUpdate.getId());

      return travelsRepo.save(updatedTravel);
    }

    return null;
  }

  public String delete(String id) {
    Travels travelToDelete = travelsRepo.findById(id).orElse(null);

    if (travelToDelete != null) {
      travelsRepo.deleteById(id);

      return "Operación realizada con éxito";
    }

    return "No se pudo completar la operación";
  }
}
