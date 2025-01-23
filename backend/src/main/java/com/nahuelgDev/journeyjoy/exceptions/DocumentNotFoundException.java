package com.nahuelgDev.journeyjoy.exceptions;

public class DocumentNotFoundException extends RuntimeException {
  public DocumentNotFoundException(String documentName, String searchValue, String searchFieldName) {
    super(String.format("No se ha encontrado ningún/a %s con el valor '%s' en su %s", documentName, searchValue, searchFieldName));
  }
}
