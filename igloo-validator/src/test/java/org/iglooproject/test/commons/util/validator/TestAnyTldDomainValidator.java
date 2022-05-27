package org.iglooproject.test.commons.util.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.iglooproject.commons.util.validator.AnyTldDomainValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TestAnyTldDomainValidator {
	
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "www.google.fr", true },
				{ "google.fr", true },
				{ "google9.fr", true },
				{ "gîte-lasoldanelle.com", true },
				{ "www.gîte-lasoldanelle.com", true },
				{ "île-lasoldanelle.com", true },
				{ "location_maechler.monsite-orange.fr", true },
				{ "-test-plop.fr", false },
				{ "test-plop-.fr", false },
				{ "test-plop-.fr", false },
				{ "test@-plop.fr", false },
				{ "test,-plop.fr", false }
		});
	}
	
	@ParameterizedTest
	@MethodSource("data")
	void testValidation(String domain, boolean expectedValid) {
		AnyTldDomainValidator validator = AnyTldDomainValidator.getInstance();
		if (expectedValid) {
			assertThat(validator.isValid(domain)).isTrue();
		} else {
			assertThat(validator.isValid(domain)).isFalse();
		}
	}
}
