package com.nahuelgDev.journeyjoy.utilities.constants;

import com.nahuelgDev.journeyjoy.collections.Requests;
import com.nahuelgDev.journeyjoy.dataClasses.Person;

public class EmailContents {
  public static String requestNotification(Requests request) {
    String peopleData = "";
    for (Person person : request.getPersons()) {
      peopleData.concat(
        String.format(
          "\n  Nombre de la persona: %s, edad: %s, número de identificación: %s \n", 
          person.getName(), person.getAge().toString(), person.getIdentificationNumber()
        )
      );
    }

    return String.format(
      "Gracias por utilizar nuestro servicio de viajes Journey Joy. Le informamos que su solicitud ha sido procesada con éxito. \n" +
      "Datos de la solicitud: \n" +
      "Viaje: %s. Cantidad de personas: %i. Plan elegido: %s. Estado de la solicitud: %s. Precio total: %s. Cantidad paga: %s. Fecha elegida: %s." +
      "%s", 
      request.getAssociatedTravel().getName(), request.getPersons().size(), request.getSelectedPlan(),
      request.getState(), request.getTotalPrice().toString(), request.getAmountPaid(),
      request.getSelectedDate().toString(),
      peopleData
    );
  }
}
