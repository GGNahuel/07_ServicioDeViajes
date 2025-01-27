package com.nahuelgDev.journeyjoy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

  @ExceptionHandler(DocumentNotFoundException.class)
  public ResponseEntity<String> documentNotFound(DocumentNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(EmptyFieldException.class)
  public ResponseEntity<String> emptyField(EmptyFieldException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
  }
  
  @ExceptionHandler(InvalidFieldValueException.class)
  public ResponseEntity<String> invalidFieldValue(InvalidFieldValueException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(InvalidOperationException.class)
  public ResponseEntity<String> invalidOperation(InvalidOperationException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }
}
