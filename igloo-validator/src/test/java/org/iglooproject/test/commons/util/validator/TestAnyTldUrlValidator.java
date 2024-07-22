package org.iglooproject.test.commons.util.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.iglooproject.commons.util.validator.AnyTldUrlValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TestAnyTldUrlValidator {

  public static Iterable<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {"http://www.google.fr/test", true},
          {"http://google.fr/test/2", true},
          {"http://google9.fr/test/2", true},
          {"http://gîte-lasoldanelle.com/test/2?param=value", true},
          {"http://www.gîte-lasoldanelle.com/test/2?param=value", true},
          {"http://île-lasoldanelle.com/test/2?param=value", true},
          {"http://location_maechler.monsite-orange.fr/test/2?param=value", true},
          {"http://-test-plop.fr", false},
          {"http://test-plop-.fr", false},
          {"http://test-plop-.fr", false},
          {"http://test@-plop.fr", false},
          {"http://test,-plop.fr", false}
        });
  }

  @ParameterizedTest
  @MethodSource("data")
  void testValidation(String url, boolean expectedValid) {
    AnyTldUrlValidator validator = AnyTldUrlValidator.getInstance();
    if (expectedValid) {
      assertThat(validator.isValid(url)).isTrue();
    } else {
      assertThat(validator.isValid(url)).isFalse();
    }
  }
}
