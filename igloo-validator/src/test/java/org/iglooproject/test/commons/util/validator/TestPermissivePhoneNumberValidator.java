package org.iglooproject.test.commons.util.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.iglooproject.commons.util.validator.PermissivePhoneNumberValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TestPermissivePhoneNumberValidator {

  public static Iterable<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {"+1 (555) 555-5555", true},
          {"+33 3 33 33 33 33", true},
          {"++3 3 33 33 33 33", false},
          {"33 3 33 33 33 33+", false},
          {"some text", false},
        });
  }

  @ParameterizedTest
  @MethodSource("data")
  void testValidation(String phoneNumber, boolean expectedValid) {
    PermissivePhoneNumberValidator validator = PermissivePhoneNumberValidator.getInstance();
    if (expectedValid) {
      assertThat(validator.isValid(phoneNumber)).isTrue();
    } else {
      assertThat(validator.isValid(phoneNumber)).isFalse();
    }
  }
}
