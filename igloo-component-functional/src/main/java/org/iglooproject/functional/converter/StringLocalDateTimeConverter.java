package org.iglooproject.functional.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class StringLocalDateTimeConverter extends AbstractStringTemporalConverter<LocalDateTime> {

  private static final long serialVersionUID = -122118537439323955L;

  private static final StringLocalDateTimeConverter INSTANCE = new StringLocalDateTimeConverter();

  private static final DateTimeFormatter DATE_TIME_FORMATER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm[:ss]");

  public static StringLocalDateTimeConverter get() {
    return INSTANCE;
  }

  protected StringLocalDateTimeConverter() {}

  @Override
  protected DateTimeFormatter getDateTimeFormatter() {
    return DATE_TIME_FORMATER;
  }

  @Override
  protected LocalDateTime createTemporal(TemporalAccessor temporalAccessor) {
    return LocalDateTime.from(temporalAccessor);
  }
}
