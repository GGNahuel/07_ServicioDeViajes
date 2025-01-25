package com.nahuelgDev.journeyjoy.services;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkFieldsHasContent;

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

import lombok.AllArgsConstructor;

@Service
public class RequestsService implements RequestsService_I {
  @Autowired RequestsRepository requestsRepo;
  @Autowired TravelsRepository travelsRepo;
  @Autowired ModelMapper modelMapper;

  //check methods
  private Travels getAssociatedTravel(Requests request) {
    String associatedTravelName = request.getAssociatedTravel().getName();
    Travels associatedTravelInDB = travelsRepo.findByName(associatedTravelName).orElseThrow(
      () -> new DocumentNotFoundException("viaje asociado", associatedTravelName, "nombre")
    );

    return associatedTravelInDB;
  }

  private void checkSelectedDateIsInTravel(Travels associatedTravel, Requests requestToCompare) {
    associatedTravel.getAvailableDates().stream().filter(
      date -> date == requestToCompare.getSelectedDate()
    ).findFirst().orElseThrow(
      () -> new RuntimeException("La fecha enviada no coincide con ninguna de las fechas disponibles para el viaje")
    );
  }

  @AllArgsConstructor
  private class CheckCapacityFunctionReturn {
    public Integer newCapacity;
    public boolean hasCapacityForNew;
  }
  private CheckCapacityFunctionReturn checkAssociatedTravelHasCapacity(Travels associatedTravel, Requests requestToCompare, Boolean isRequestUpdate) {
    int currentCapacity = associatedTravel.getCurrentCapacity();
    int newCapacity = currentCapacity + requestToCompare.getPersons().size();

    if (isRequestUpdate) {
      Requests previousStateOfRequest = requestsRepo.findById(requestToCompare.getId()).orElseThrow(
        () -> new DocumentNotFoundException("solicitud de viaje", requestToCompare.getId(), "id")
      );

      newCapacity -= previousStateOfRequest.getPersons().size();
    }

    if (newCapacity > associatedTravel.getMaxCapacity()) return new CheckCapacityFunctionReturn(null, false);
    if (newCapacity < 0) throw new RuntimeException("Ocurrió un error al dar de baja. La nueva cantidad es menor a 0"); 
    // Esto en realidad sería un log (al cliente retornaría otro msg)

    return new CheckCapacityFunctionReturn(newCapacity, true);
  }

  // main methods
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
  public List<Requests> getByEmail(String email) {
    checkFieldsHasContent(new Field("email de búsqueda", email));

    return requestsRepo.findByEmail(email);
  }

  @Override
  public Requests create(Requests requestToCreate) {
    checkFieldsHasContent(
      new Field("plan seleccionado", requestToCreate.getSelectedPlan()),
      new Field("personas en el plan", requestToCreate.getPersons()),
      new Field("viaje asociado", requestToCreate.getAssociatedTravel()),
      new Field("pago realizado", requestToCreate.getAmountPaid()),
      new Field("email", requestToCreate.getEmail())
    );

    Travels associatedTravelInDB = getAssociatedTravel(requestToCreate);
    
    checkSelectedDateIsInTravel(associatedTravelInDB, requestToCreate);
    CheckCapacityFunctionReturn capacityFnReturn = checkAssociatedTravelHasCapacity(associatedTravelInDB, requestToCreate, false);

    Double totalPrice = associatedTravelInDB.getPayPlans().stream().filter(
      plan -> plan.getPlanFor() == requestToCreate.getSelectedPlan().getPlanFor()
    ).findFirst().orElseThrow(
      () -> new RuntimeException("El plan de pago enviado no existe en los disponibles para el viaje seleccionado")
    ).getPrice();

    RequestState state = !capacityFnReturn.hasCapacityForNew ? RequestState.inWaitList :
      requestToCreate.getAmountPaid() == totalPrice ? 
        RequestState.completePayment :
        requestToCreate.getAmountPaid() != 0.0 ?
          RequestState.parcialPayment :
          RequestState.confirmed;

    requestToCreate.setTotalPrice(totalPrice);
    requestToCreate.setState(state);

    if (capacityFnReturn.hasCapacityForNew) {
      associatedTravelInDB.setCurrentCapacity(capacityFnReturn.newCapacity);
      travelsRepo.save(associatedTravelInDB);      
    }

    return requestsRepo.save(requestToCreate);
  }

  @Override
  public Requests update(RequestsUpdateDto updatedRequest) {
    checkFieldsHasContent(new Field("id", updatedRequest.getId()));

    Requests requestToUpdate = requestsRepo.findById(updatedRequest.getId()).orElseThrow(
      () -> new DocumentNotFoundException("solicitud de viaje", updatedRequest.getId(), "id")
    );
    
    Travels associatedTravelInDB = getAssociatedTravel(requestToUpdate);

    Requests mappedUpdatedRequest = modelMapper.map(updatedRequest, Requests.class);
    checkSelectedDateIsInTravel(associatedTravelInDB, mappedUpdatedRequest);

    CheckCapacityFunctionReturn capacityFnReturn = checkAssociatedTravelHasCapacity(associatedTravelInDB, mappedUpdatedRequest, true);
    if (capacityFnReturn.hasCapacityForNew) {
      associatedTravelInDB.setCurrentCapacity(capacityFnReturn.newCapacity);
      travelsRepo.save(associatedTravelInDB);
    }

    return requestsRepo.save(mappedUpdatedRequest);
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
