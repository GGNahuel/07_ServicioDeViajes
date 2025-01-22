package com.nahuelgDev.journeyjoy.utilities;

import java.util.List;

import com.nahuelgDev.journeyjoy.exceptions.EmptyFieldException;

public class Verifications {
  private static void checkArraysHaveSameLength(Object[] array1, Object[] array2) throws Exception {
    if (array1.length != array2.length)
      throw new Exception("Arrays don't have the same length");
  }

  public static void validateFieldsHasContent(String[] fieldNames, Object... fields) throws Exception {
    checkArraysHaveSameLength(fieldNames, fields);
    for (int i = 0; i < fieldNames.length; i++) {
      if (fields[i] == null)
        throw new EmptyFieldException(fieldNames[i]);

      if (fields[i] instanceof String && ((String) fields[i]).isBlank())
        throw new EmptyFieldException(fieldNames[i]);

      if (fields[i] instanceof List) {
        List<?> list = (List<?>) fields[i];

        if (list.size() == 0) 
          throw new EmptyFieldException(fieldNames[i]);
        
        for (Object object : list) {
          validateFieldsHasContent(new String[]{"objeto en ".concat(fieldNames[i])}, object);
        }
      }
    }
  }
}
