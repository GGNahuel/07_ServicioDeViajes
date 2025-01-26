package com.nahuelgDev.journeyjoy.utilities.constants;

import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.dataClasses.Person;

public class EmailContents {
  // info generators

  private static String getPeopleData(List<Person> list) {
    String peopleData = "";
    for (Person person : list) {
      peopleData.concat(
        String.format(
          "\n  Nombre de la persona: %s, edad: %s, número de identificación: %s \n", 
          person.getName(), person.getAge().toString(), person.getIdentificationNumber()
        )
      );
    }

    return peopleData;
  }
  private static String getRequestData(Requests request) {
    String peopleData = getPeopleData(request.getPersons());

    return String.format(
      "\n\nDatos de la solicitud: \n" +
      "Viaje: %s. Cantidad de personas: %i. Plan elegido: %s. Estado de la solicitud: %s. Precio total: $%s. Cantidad paga: $%s. Fecha elegida: %s." +
      "%s", 
      request.getAssociatedTravel().getName(), request.getPersons().size(), request.getSelectedPlan(),
      request.getState(), request.getTotalPrice().toString(), request.getAmountPaid(),
      request.getSelectedDate().toString(),
      peopleData
    );
  }

  // mail content generators

  public static String requestNotification(Requests request) {
    return String.format(
      "Gracias por utilizar nuestro servicio de viajes Journey Joy. Le informamos que su solicitud ha sido procesada con éxito. %s",
      getRequestData(request)
    );
  }

  public static String paymentNotification(Double amountPaid, Requests request) {
    return String.format(
      "Se ha recibido un pago de $%s. %s", 
      amountPaid.toString(), getRequestData(request)
    );
  }

  public static String updatedRequestNotification(Requests request) {
    return String.format(
      "Se ha actualizado la solicitud de su viaje. %s", 
      getRequestData(request)
    );
  }

  public static String cancelRequest(Requests request) {
    return String.format(
      "Su solicitud del viaje %s, para la fecha %s, ha sido cancelada con éxito.", 
      request.getAssociatedTravel().getName(), request.getSelectedDate().toString()
    );
  }

  public static String travelNowHasCapacityNotification(Requests request) {
    return String.format(
      "La capacidad del viaje de su solicitud en lista de espera se ha reducido." +
      "\nPara confirmar su lugar debe realizar un pago de al menos la sexta parte del total. %s", 
      getRequestData(request)
    );
  }
}
