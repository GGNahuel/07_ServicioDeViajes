package com.nahuelgDev.journeyjoy.utilities;

import java.util.List;

import com.nahuelgDev.journeyjoy.exceptions.EmptyFieldException;

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
        
        for (Object object : list) {
          checkFieldsHasContent(new Field("objeto en lista".concat(name), object));
        }
      }
    }
  }
}
