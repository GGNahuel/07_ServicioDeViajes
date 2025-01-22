package com.nahuelgDev.journeyjoy.exceptions;

public class EmptyFieldException extends RuntimeException {
  public EmptyFieldException(String fieldName) {
    super(String.format("El dato ingresado para el campo '%s' no puede estar vacío o ser nulo.", fieldName));
  }
}