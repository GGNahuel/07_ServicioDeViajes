package com.nahuelgDev.journeyjoy.services;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkFieldsHasContent;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.collections.Emails;
import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dtos.RequestsUpdateDto;
import com.nahuelgDev.journeyjoy.enums.RequestState;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.exceptions.InvalidFieldValueException;
import com.nahuelgDev.journeyjoy.exceptions.InvalidOperationException;
import com.nahuelgDev.journeyjoy.repositories.EmailsRepository;
import com.nahuelgDev.journeyjoy.repositories.RequestsRepository;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.RequestsService_I;
import com.nahuelgDev.journeyjoy.utilities.Verifications.Field;
import com.nahuelgDev.journeyjoy.utilities.constants.EmailContents;

import lombok.AllArgsConstructor;

@Service
public class RequestsService_Impl implements RequestsService_I {
  @Autowired RequestsRepository requestsRepo;
  @Autowired TravelsRepository travelsRepo;
  @Autowired EmailsRepository emailsRepo;
  @Autowired EmailsService emailsService;
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
      () -> new InvalidFieldValueException("La fecha enviada no coincide con ninguna de las fechas disponibles para el viaje")
    );
  }

  private RequestState getNewState(boolean hasCapacityForNew, double amountPaid, double totalPrice) {
    return !hasCapacityForNew ? RequestState.inWaitList :
    amountPaid == totalPrice ? 
      RequestState.completePayment :
      RequestState.parcialPayment;
  }

  @AllArgsConstructor
  private class CheckCapacityFunctionReturn {
    public Integer newCapacity;
    public boolean hasCapacityForNew;
  }

  private CheckCapacityFunctionReturn checkAssociatedTravelHasCapacity(
    Travels associatedTravel, Requests requestToCompare, Boolean requestCapacityHasChange
  ) {
    int currentCapacity = associatedTravel.getCurrentCapacity();
    int newCapacity = currentCapacity + requestToCompare.getPersons().size() * (requestToCompare.getState() == RequestState.canceled ? -1 : 1);

    if (requestCapacityHasChange) {
      Requests previousStateOfRequest = requestsRepo.findById(requestToCompare.getId()).orElseThrow(
        () -> new DocumentNotFoundException("solicitud de viaje", requestToCompare.getId(), "id")
      );

      newCapacity -= previousStateOfRequest.getPersons().size();
    }

    if (newCapacity > associatedTravel.getMaxCapacity()) return new CheckCapacityFunctionReturn(null, false);
    if (newCapacity < 0) throw new InvalidOperationException("Ocurrió un error al dar de baja. La nueva cantidad es menor a 0"); 
    // Esto en realidad sería un log (al cliente retornaría otro msg)

    return new CheckCapacityFunctionReturn(newCapacity, true);
  }

  // others

  private void makeRequestInWaitListConfirmed(Double updatedPayment, Requests request) {
    if (updatedPayment < (request.getTotalPrice() * 0.16))
      throw new InvalidFieldValueException("Para que la solicitud sea confirmada debe recibirse como pago al menos la sexta parte del total");
    
    Travels associatedTravelInDB = getAssociatedTravel(request);
    
    CheckCapacityFunctionReturn capacityFnReturn = checkAssociatedTravelHasCapacity(associatedTravelInDB, request, false);
    if (!capacityFnReturn.hasCapacityForNew) 
      throw new InvalidOperationException(
        "En estos momentos no hay cupo disponible para validar el pago de su solicitud. \n" + 
        "Si usted ha recibido un mail indicando lo contrario es probable que alguien en lista de espera ya haya hecho el pago requerido"
      );

    RequestState state = getNewState(capacityFnReturn.hasCapacityForNew, updatedPayment, request.getTotalPrice());
    request.setState(state);
    
    associatedTravelInDB.setCurrentCapacity(capacityFnReturn.newCapacity);
    travelsRepo.save(associatedTravelInDB);
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
    checkFieldsHasContent(new Field("solicitud de viaje", requestToCreate));
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
      () -> new InvalidFieldValueException("El plan de pago enviado no existe en los disponibles para el viaje seleccionado")
    ).getPrice();

    if (!capacityFnReturn.hasCapacityForNew && requestToCreate.getAmountPaid() != 0.0) {
      throw new InvalidFieldValueException("No hay cupo disponible para el viaje seleccionado, no debería recibirse pago de la solicitud.");
    }

    RequestState state = getNewState(capacityFnReturn.hasCapacityForNew, requestToCreate.getAmountPaid(), totalPrice);

    requestToCreate.setTotalPrice(totalPrice);
    requestToCreate.setState(state);

    String associatedEmail = requestToCreate.getEmail().getEmail();
    boolean emailIsRegistered = emailsRepo.findByEmail(associatedEmail).isPresent();
    if (!emailIsRegistered) {
      Emails newEmailInDB = new Emails();

      newEmailInDB.setEmail(associatedEmail);
      newEmailInDB.setOwner(requestToCreate.getPersons().get(0).getName());

      emailsRepo.save(newEmailInDB);
    }

    if (capacityFnReturn.hasCapacityForNew) {
      associatedTravelInDB.setCurrentCapacity(capacityFnReturn.newCapacity);
      travelsRepo.save(associatedTravelInDB);      
    }

    emailsService.sendEmail(associatedEmail, "Solicitud de viaje realizada - Journey Joy", EmailContents.requestNotification(requestToCreate));
    return requestsRepo.save(requestToCreate);
  }

  @Override
  public Requests update(RequestsUpdateDto updatedRequest) {
    checkFieldsHasContent(new Field("solicitud de viaje", updatedRequest));
    checkFieldsHasContent(new Field("id", updatedRequest.getId()));

    Requests requestToUpdate = requestsRepo.findById(updatedRequest.getId()).orElseThrow(
      () -> new DocumentNotFoundException("solicitud de viaje", updatedRequest.getId(), "id")
    );
    if (requestToUpdate.getState() == RequestState.canceled)
      throw new InvalidOperationException("No se puede modificar una solicitud que ya fue cancelada");
    
    Travels associatedTravelInDB = getAssociatedTravel(requestToUpdate);

    Requests mappedUpdatedRequest = modelMapper.map(updatedRequest, Requests.class);
    checkSelectedDateIsInTravel(associatedTravelInDB, mappedUpdatedRequest);

    CheckCapacityFunctionReturn capacityFnReturn = checkAssociatedTravelHasCapacity(associatedTravelInDB, mappedUpdatedRequest, true);
    if (capacityFnReturn.hasCapacityForNew) {
      associatedTravelInDB.setCurrentCapacity(capacityFnReturn.newCapacity);
      travelsRepo.save(associatedTravelInDB);
    }

    emailsService.sendEmail(requestToUpdate.getEmail().getEmail(), "Solicitud de viaje actualizada - Journey Joy", EmailContents.updatedRequestNotification(mappedUpdatedRequest));
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
      throw new InvalidFieldValueException("El valor ingresado como pago, sumado a lo pagado anteriormente, excede al total de lo requerido");

    if (request.getState() == RequestState.inWaitList) {
      makeRequestInWaitListConfirmed(updatedPayment, request);
    }
    
    request.setAmountPaid(updatedPayment);
    requestsRepo.save(request);
    emailsService.sendEmail(request.getEmail().getEmail(), "Pago realizado - Journey Joy", EmailContents.paymentNotification(amount, request));
    return String.format("Pago realizado con éxito. Total pagado: %s. Falta pagar: %s", updatedPayment, remainingPay);
  }

  @Override
  public String cancelRequest(String id) {
    checkFieldsHasContent(new Field("id", id));

    Requests request = requestsRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("solicitud de viaje", id, "id")
    );
    request.setState(RequestState.canceled);

    Travels associatedTravel = travelsRepo.findById(request.getAssociatedTravel().getId()).orElseThrow(
      () -> new DocumentNotFoundException("viaje", id, "id")
    );
    CheckCapacityFunctionReturn check = checkAssociatedTravelHasCapacity(associatedTravel, request, false);
    if (check.hasCapacityForNew) {
      associatedTravel.setCurrentCapacity(check.newCapacity);

      travelsRepo.save(associatedTravel);
    }

    // here should be the connection with the payment gateway to make the refund if it's applicable

    requestsRepo.save(request);
    emailsService.sendEmail(request.getEmail().getEmail(), "Cancelación de solicitud - Journey Joy", EmailContents.cancelRequest(request));

    List<Requests> requestsInWaitList = requestsRepo.findByAssociatedTravelNameAndState(request.getAssociatedTravel().getName(), RequestState.inWaitList);

    requestsInWaitList.forEach(requestInWaitList -> {
      emailsService.sendEmail(
        requestInWaitList.getEmail().getEmail(), 
        "Posible confirmación de viaje - Journey Joy", 
        EmailContents.travelNowHasCapacityNotification(request)
      );
    });
    return "Solicitud cancelada con éxito";
  }
}
