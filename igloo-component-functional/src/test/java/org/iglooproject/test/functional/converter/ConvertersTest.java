package org.iglooproject.test.functional.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.iglooproject.functional.Suppliers2;
import org.iglooproject.functional.converter.StringBigDecimalConverter;
import org.iglooproject.functional.converter.StringBooleanConverter;
import org.iglooproject.functional.converter.StringCollectionConverter;
import org.iglooproject.functional.converter.StringDateConverter;
import org.iglooproject.functional.converter.StringDateTimeConverter;
import org.iglooproject.functional.converter.StringEmailNotificationRecipientCollectionConverter;
import org.iglooproject.functional.converter.StringLocaleConverter;
import org.iglooproject.functional.converter.StringURIConverter;
import org.iglooproject.mail.api.SimpleRecipient;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

class ConvertersTest {

	public static Stream<Arguments> data() throws URISyntaxException {
		return Stream.of(
			Arguments.arguments(StringBigDecimalConverter.get(), "15.01", new BigDecimal("15.01")),
			Arguments.arguments(StringBigDecimalConverter.get().reverse(), new BigDecimal("15.01"), "15.01"),
			Arguments.arguments(StringBooleanConverter.get(), "true", true),
			Arguments.arguments(StringBooleanConverter.get(), "on", true),
			Arguments.arguments(StringBooleanConverter.get(), "yes", true),
			Arguments.arguments(StringBooleanConverter.get(), "1", true),
			Arguments.arguments(StringBooleanConverter.get(), "false", false),
			Arguments.arguments(StringBooleanConverter.get(), "off", false),
			Arguments.arguments(StringBooleanConverter.get(), "no", false),
			Arguments.arguments(StringBooleanConverter.get(), "0", false),
			Arguments.arguments(StringBooleanConverter.get().reverse(), true, "true"),
			Arguments.arguments(StringBooleanConverter.get().reverse(), false, "false"),
			Arguments.arguments(new StringCollectionConverter<>(StringBooleanConverter.get(), Suppliers2.<Boolean>arrayList()), "true false true", Lists.newArrayList(true, false, true)),
			Arguments.arguments(new StringCollectionConverter<>(Converter.<String>identity(), Suppliers2.<String>arrayList()).separator("_"), "test1_test2_test3", Lists.newArrayList("test1", "test2", "test3")),
			Arguments.arguments(new StringCollectionConverter<>(StringBooleanConverter.get(), Suppliers2.<Boolean>arrayList()).separator(";").reverse(), Lists.newArrayList(true, false, true), "true;false;true"),
			Arguments.arguments(new StringCollectionConverter<>(Converter.<String>identity(), Suppliers2.<String>arrayList()).joiner(Joiner.on("@").skipNulls()).reverse(), Lists.newArrayList("test1", "test2", null), "test1@test2"),
			Arguments.arguments(StringDateConverter.get(), "1990-04-18", new GregorianCalendar(1990, Calendar.APRIL, 18, 0, 0, 0).getTime()),
			Arguments.arguments(StringDateConverter.get(), "2015-12-25", new GregorianCalendar(2015, Calendar.DECEMBER, 25, 0, 0, 0).getTime()),
			Arguments.arguments(StringDateConverter.get().reverse(), new GregorianCalendar(1990, Calendar.APRIL, 18, 0, 0, 0).getTime(), "1990-04-18"),
			Arguments.arguments(StringDateConverter.get().reverse(), new GregorianCalendar(2015, Calendar.DECEMBER, 25, 0, 0, 0).getTime(), "2015-12-25"),
			Arguments.arguments(StringDateTimeConverter.get(), "1990-04-18 12:04", new GregorianCalendar(1990, Calendar.APRIL, 18, 12, 4, 0).getTime()),
			Arguments.arguments(StringDateTimeConverter.get(), "2015-12-31 23:59:59", new GregorianCalendar(2015, Calendar.DECEMBER, 31, 23, 59, 59).getTime()),
			Arguments.arguments(StringDateTimeConverter.get().reverse(), new GregorianCalendar(1990, Calendar.APRIL, 18, 12, 4, 0).getTime(), "1990-04-18 12:04:00"),
			Arguments.arguments(StringDateTimeConverter.get().reverse(), new GregorianCalendar(2015, Calendar.DECEMBER, 31, 23, 59, 59).getTime(), "2015-12-31 23:59:59"),
			Arguments.arguments(StringLocaleConverter.get(), "fr", Locale.FRENCH),
			Arguments.arguments(StringLocaleConverter.get(), "fr-FR", Locale.FRANCE),
			Arguments.arguments(StringLocaleConverter.get(), "en", Locale.ENGLISH),
			Arguments.arguments(StringLocaleConverter.get(), "en-US", Locale.US),
			Arguments.arguments(StringLocaleConverter.get(), "pt-BR", new Locale("pt", "BR")),
			Arguments.arguments(StringLocaleConverter.get().reverse(), Locale.FRENCH, "fr"),
			Arguments.arguments(StringLocaleConverter.get().reverse(), Locale.FRANCE, "fr-FR"),
			Arguments.arguments(StringLocaleConverter.get().reverse(), Locale.ENGLISH, "en"),
			Arguments.arguments(StringLocaleConverter.get().reverse(), Locale.US, "en-US"),
			Arguments.arguments(StringLocaleConverter.get().reverse(), new Locale("pt", "BR"), "pt-BR"),
			Arguments.arguments(StringURIConverter.get(), "http://www.openwide.fr", new URI("http://www.openwide.fr")),
			Arguments.arguments(StringURIConverter.get().reverse(), new URI("www.openwide.fr"), "www.openwide.fr"),
			Arguments.arguments(
				new StringEmailNotificationRecipientCollectionConverter<>(Locale.FRENCH).reverse(),
				Lists.newArrayList(
					new SimpleRecipient(Locale.FRENCH, "example@kobalt.fr", "email"),
					new SimpleRecipient(Locale.FRENCH, "other@kobalt.fr", null)
				),
				"email<example@kobalt.fr>;other@kobalt.fr"
			)
		);
	}

	public static Stream<Arguments> dataCollection() throws URISyntaxException {
		return Stream.of(
			Arguments.arguments(
				new StringEmailNotificationRecipientCollectionConverter<>(Locale.FRENCH),
				"email<example@kobalt.fr>;other@kobalt.fr",
				Lists.newArrayList(
					new SimpleRecipient(Locale.FRENCH, "example@kobalt.fr", "email"),
					new SimpleRecipient(Locale.FRENCH, "other@kobalt.fr", null)
				)
			)
		);
	}

	@ParameterizedTest
	@MethodSource("data")
	void testConverters(Converter<Object, Object> converter, Object value, Object expectedValue) {
		Assertions.assertThat(converter.convert(value)).isEqualTo(expectedValue);
	}

	@ParameterizedTest
	@MethodSource("dataCollection")
	void testConvertersCollectionResult(Converter<Object, Object> converter, Object value, Collection<Object> expectedValue) {
		assertThat(converter.convert(value)).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedValue);
	}

}
