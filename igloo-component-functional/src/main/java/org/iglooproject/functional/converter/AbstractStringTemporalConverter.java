package org.iglooproject.functional.converter;

import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

public abstract class AbstractStringTemporalConverter<T extends Temporal> extends SerializableConverter2<String, T> {

	private static final long serialVersionUID = -2129154130345491911L;

	protected AbstractStringTemporalConverter() {
	}

	@Override
	protected T doForward(String a) {
		return createTemporal(getDateTimeFormatter().parse(a));
	}

	@Override
	protected String doBackward(T b) {
		return getDateTimeFormatter().format(b);
	}

	protected abstract DateTimeFormatter getDateTimeFormatter();

	protected abstract T createTemporal(TemporalAccessor temporalAccessor);

	/**
	 * Workaround sonar/findbugs - https://github.com/google/guava/issues/1858
	 * Guava Converter overrides only equals to add javadoc, but findbugs warns about non coherent equals/hashcode
	 * possible issue.
	 */
	@Override
	public boolean equals(Object object) {
		return super.equals(object);
	}

	/**
	 * Workaround sonar/findbugs - see #equals(Object)
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
