package org.iglooproject.functional.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class StringLocalTimeConverter extends AbstractStringTemporalConverter<LocalTime> {

	private static final long serialVersionUID = -122118537439323955L;

	private static final StringLocalTimeConverter INSTANCE = new StringLocalTimeConverter();

	private static final DateTimeFormatter DATE_TIME_FORMATER = DateTimeFormatter.ofPattern("HH:mm[:ss]");

	public static StringLocalTimeConverter get() {
		return INSTANCE;
	}

	protected StringLocalTimeConverter() {
	}

	@Override
	protected DateTimeFormatter getDateTimeFormatter() {
		return DATE_TIME_FORMATER;
	}

	@Override
	protected LocalTime createTemporal(TemporalAccessor temporalAccessor) {
		return LocalTime.from(temporalAccessor);
	}

}
