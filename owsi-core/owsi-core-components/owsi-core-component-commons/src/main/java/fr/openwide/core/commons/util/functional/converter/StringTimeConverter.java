package fr.openwide.core.commons.util.functional.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;

import com.google.common.base.Converter;

public class StringTimeConverter extends Converter<String, Date> {

	private static final StringTimeConverter INSTANCE = new StringTimeConverter();

	private static final String PATTERN = "HH:mm";
	private static final String PATTERN_FULL = "HH:mm:ss";

	public static StringTimeConverter get() {
		return INSTANCE;
	}

	protected StringTimeConverter() {
	}

	@Override
	protected Date doForward(String a) {
		try {
			return DateUtils.parseDate(a, Locale.ROOT, PATTERN, PATTERN_FULL);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date value '" + a + "'", e);
		}
	}

	@Override
	protected String doBackward(Date b) {
		return new SimpleDateFormat(PATTERN_FULL, Locale.ROOT).format(b);
	}

}
