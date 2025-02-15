package com.nahuelgDev.journeyjoy.utilities;

import java.util.List;

import com.nahuelgDev.journeyjoy.exceptions.EmptyFieldException;
import com.nahuelgDev.journeyjoy.exceptions.InvalidFieldValueException;

import lombok.Getter;

public class Verifications {
  @Getter
  public static class Field {
    private String name;
    private Object value;

    public Field(String name, Object value) {
      this.name = name; this.value = value;
    }
  }

  public static void checkFieldsHasContent(Field... fields) {
    for (int i = 0; i < fields.length; i++) {
      String name = fields[i].getName();
      Object value = fields[i].getValue();

      if (value == null)
        throw new EmptyFieldException(name);

      if (value instanceof String && ((String) value).isBlank())
        throw new EmptyFieldException(name);

      if (value instanceof List) {
        List<?> list = (List<?>) value;

        if (list.isEmpty()) 
          throw new EmptyFieldException(name);
        
        for (int j = 0; j < list.size(); j++) {
          Object object = list.get(j);
          Integer ji = j + 1;
          checkFieldsHasContent(new Field(String.format("%s' en la posición '%s", name, ji.toString()), object));
        }
      }
    }
  }

  public static void checkStringIsAlphaNumeric(Field... fields) throws Exception {
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].value == null) continue;

      if (!(fields[i].value instanceof String)) 
        throw new Exception("Los campos ingresados para comprobar solo deben ser una secuencia de caracteres");
      
      String value = (String) fields[i].value;
      value = value.replaceAll("\\s+", "");
      String fieldName = fields[i].name;
      if (!value.isEmpty() && !value.matches("[\\p{L}0-9¿?¡!.,:\\-]+"))
        throw new InvalidFieldValueException("El campo " + fieldName + " solo puede contener números, letras y algunos signos de puntuación. " + value);
    }
  }

  public static void checkEmailHasCorrectFormat(String email) {
    if (!email.matches("[a-zA-Z0-9]+@[a-z]{1,}\\.[a-z]{2,4}"))
      throw new InvalidFieldValueException("El email ingresado no cumple con el formato requerido");
  }
}
