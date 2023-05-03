package org.iglooproject.functional.converter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class StringInstantConverter extends AbstractStringTemporalConverter<Instant> {

	private static final long serialVersionUID = 5126003436945781643L;

	private static final StringInstantConverter INSTANCE = new StringInstantConverter();

	private static final DateTimeFormatter DATE_TIME_FORMATER = DateTimeFormatter.ISO_INSTANT;

	public static StringInstantConverter get() {
		return INSTANCE;
	}

	protected StringInstantConverter() {
	}

	@Override
	protected DateTimeFormatter getDateTimeFormatter() {
		return DATE_TIME_FORMATER;
	}

	@Override
	protected Instant createTemporal(TemporalAccessor temporalAccessor) {
		return Instant.from(temporalAccessor);
	}

}
