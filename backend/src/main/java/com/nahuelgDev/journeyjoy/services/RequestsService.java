package com.nahuelgDev.journeyjoy.services;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkFieldsHasContent;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dtos.RequestsUpdateDto;
import com.nahuelgDev.journeyjoy.enums.RequestState;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.repositories.RequestsRepository;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.RequestsService_I;
import com.nahuelgDev.journeyjoy.utilities.Verifications.Field;

@Service
public class RequestsService implements RequestsService_I {
  @Autowired RequestsRepository requestsRepo;
  @Autowired TravelsRepository travelsRepo;
  @Autowired ModelMapper modelMapper;

  @Override
  public List<Requests> getAll() {
    return requestsRepo.findAll();
  }

  @Override
  public Requests getById(String id) {
    checkFieldsHasContent(new Field("id", id));

    return requestsRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("solicitud de viaje", id, "id")
    );
  }

  @Override 
  public List<Requests> getByTravelName(String travelName) {
    checkFieldsHasContent(new Field("nombre del viaje asociado", travelName));

    return requestsRepo.findByAssociatedTravelName(travelName);
  }

  @Override
  public Requests create(Requests requestToCreate) {
    checkFieldsHasContent(
      new Field("plan seleccionado", requestToCreate.getSelectedPlan()),
      new Field("personas en el plan", requestToCreate.getPersons()),
      new Field("viaje asociado", requestToCreate.getAssociatedTravel()),
      new Field("pago realizado", requestToCreate.getAmountPaid())
    );

    String associatedTravelName = requestToCreate.getAssociatedTravel().getName();
    Travels associatedTravelInDB = travelsRepo.findByName(associatedTravelName).orElseThrow(
      () -> new DocumentNotFoundException("viaje asociado", associatedTravelName, "nombre")
    );

    LocalDate selectedDate = associatedTravelInDB.getAvailableDates().stream().filter(
      date -> date == requestToCreate.getSelectedDate()
    ).findFirst().orElseThrow(
      () -> new RuntimeException("La fecha enviada no coincide con ninguna de las fechas disponibles para el viaje")
    );

    Double totalPrice = associatedTravelInDB.getPayPlans().stream().filter(
      plan -> plan.getPlanFor() == requestToCreate.getSelectedPlan().getPlanFor()
    ).findFirst().orElseThrow(
      () -> new RuntimeException("El plan de pago enviado no existe en los disponibles para el viaje seleccionado")
    ).getPrice();

    RequestState state = requestToCreate.getAmountPaid() == totalPrice ? 
      RequestState.completePayment :
      requestToCreate.getAmountPaid() != 0.0 ?
        RequestState.parcialPayment :
        RequestState.confirmed;

    requestToCreate.setTotalPrice(totalPrice);
    requestToCreate.setState(state);
    requestToCreate.setSelectedDate(selectedDate);

    return requestsRepo.save(requestToCreate);
  }

  @Override
  public Requests update(RequestsUpdateDto updatedRequest) {
    checkFieldsHasContent(new Field("id", updatedRequest.getId()));

    Requests requestToUpdate = requestsRepo.findById(updatedRequest.getId()).orElseThrow(
      () -> new DocumentNotFoundException("solicitud de viaje", updatedRequest.getId(), "id")
    );

    String associatedTravelName = requestToUpdate.getAssociatedTravel().getName();
    Travels associatedTravelInDB = travelsRepo.findByName(associatedTravelName).orElseThrow(
      () -> new DocumentNotFoundException("viaje asociado", associatedTravelName, "nombre")
    );

    associatedTravelInDB.getAvailableDates().stream().filter(
      date -> date == updatedRequest.getSelectedDate()
    ).findFirst().orElseThrow(
      () -> new RuntimeException("La fecha enviada no coincide con ninguna de las fechas disponibles para el viaje")
    );

    return requestsRepo.save(modelMapper.map(updatedRequest, Requests.class));
  }

  @Override
  public String addPayment(String id, Double amount) {
    checkFieldsHasContent(new Field("id de la solicitud", id), new Field("monto a pagar", amount));

    Requests request = requestsRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("solicitud de viaje", id, "id")
    );

    Double updatedPayment = request.getAmountPaid() + amount;
    Double remainingPay = request.getTotalPrice() - updatedPayment;

    if (updatedPayment > request.getTotalPrice()) 
      throw new RuntimeException("El valor ingresado como pago, sumado a lo pagado anteriormente, excede al total de lo requerido");

    request.setAmountPaid(updatedPayment);
    requestsRepo.save(request);

    return String.format("Pago realizado con éxito. Total pagado: %s. Falta pagar: %s", updatedPayment, remainingPay);
  }

  @Override
  public String cancelRequest(String id) {
    checkFieldsHasContent(new Field("id", id));

    Requests request = requestsRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("solicitud de viaje", id, "id")
    );
    request.setState(RequestState.canceled);

    requestsRepo.save(request);
    return "Solicitud cancelada con éxito";
  }
}
