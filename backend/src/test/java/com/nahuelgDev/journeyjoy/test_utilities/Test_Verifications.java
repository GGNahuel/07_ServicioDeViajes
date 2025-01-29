package com.nahuelgDev.journeyjoy.test_utilities;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkFieldsHasContent;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.nahuelgDev.journeyjoy.exceptions.EmptyFieldException;
import com.nahuelgDev.journeyjoy.utilities.Verifications.Field;

public class Test_Verifications {

  @Test
  void checkFieldHasContent_success() {
    assertDoesNotThrow(() -> checkFieldsHasContent(new Field("name", "value")));
    assertDoesNotThrow(() -> checkFieldsHasContent(
      new Field("name", "value"),
      new Field("name", List.of("asd", "asd"))
    ));
  }

  @Test
  void checkFieldHasContent_throwEmptyFieldException() {
    Field successValue = new Field("name", "value");
    Field nullValue = new Field("name", null);
    Field emptyStringValue = new Field("name", "");
    Field emptyListValue = new Field("name", Arrays.asList());
    Field nullValueInListValue = new Field("name", Arrays.asList("asd", null));
    Field emptyValueInListValue = new Field("name", Arrays.asList("asd", ""));

    assertAll(
      () -> assertThrows(EmptyFieldException.class, () -> checkFieldsHasContent(successValue, nullValue)),
      () -> assertThrows(EmptyFieldException.class, () -> checkFieldsHasContent(successValue, emptyStringValue)),
      () -> assertThrows(EmptyFieldException.class, () -> checkFieldsHasContent(successValue, emptyListValue)),
      () -> assertThrows(EmptyFieldException.class, () -> checkFieldsHasContent(successValue, nullValueInListValue)),
      () -> assertThrows(EmptyFieldException.class, () -> checkFieldsHasContent(successValue, emptyValueInListValue)) 
    );
  }

  @Test
  void checkFieldHasContent_exceptionHasCorrectMsg() {
    EmptyFieldException ex1 = assertThrows(EmptyFieldException.class, () -> checkFieldsHasContent(new Field("fieldName", null)));
    EmptyFieldException ex2 = assertThrows(
      EmptyFieldException.class, 
      () -> checkFieldsHasContent(new Field("listName", Arrays.asList("asd", "")))
    );

    assertEquals("El dato ingresado para el campo 'fieldName' no puede estar vacío o ser nulo.", ex1.getMessage());
    assertEquals("El dato ingresado para el campo 'listName' en la posición '2' no puede estar vacío o ser nulo.", ex2.getMessage());
  }
}
