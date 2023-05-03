package org.iglooproject.functional.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class StringLocalDateConverter extends AbstractStringTemporalConverter<LocalDate> {

	private static final long serialVersionUID = -122118537439323955L;

	private static final StringLocalDateConverter INSTANCE = new StringLocalDateConverter();

	private static final DateTimeFormatter DATE_TIME_FORMATER = DateTimeFormatter.ISO_LOCAL_DATE;

	public static StringLocalDateConverter get() {
		return INSTANCE;
	}

	protected StringLocalDateConverter() {
	}

	@Override
	protected DateTimeFormatter getDateTimeFormatter() {
		return DATE_TIME_FORMATER;
	}

	@Override
	protected LocalDate createTemporal(TemporalAccessor temporalAccessor) {
		return LocalDate.from(temporalAccessor);
	}

}
