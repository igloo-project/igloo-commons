package org.iglooproject.functional.converter;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.iglooproject.functional.Suppliers2;
import org.iglooproject.mail.api.INotificationRecipient;
import org.iglooproject.mail.api.SimpleRecipient;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;


/**
 * Allows you to transform an email list into a list of notification recipients
 * 
 * example : Kobalt email<example@kobalt.fr>;other@kobalt.fr
 * result : Notification(email = "example@kobalt.fr", fullName = "Kobalt email"), Notification(email = "other@kobalt.fr", fullName = null)
 */
public class StringEmailNotificationRecipientCollectionConverter<T, C extends Collection<T>> extends SerializableConverter2<String, List<INotificationRecipient>> {

	private static final long serialVersionUID = -7987113207382904621L;

	private static final String GROUP_LABEL = "label";
	private static final String GROUP_EMAIL = "email";
	private static final String REGEX_PATTERN = "(?<" + GROUP_LABEL + ">.[^<>]*)(<(?<" + GROUP_EMAIL + ">.+)>)?";

	private Locale locale;

	private Splitter splitter;
	private Joiner joiner;

	public StringEmailNotificationRecipientCollectionConverter(Locale locale) {
		separator(";");
		this.locale = Objects.requireNonNull(locale);
	}

	public StringEmailNotificationRecipientCollectionConverter<T, C> separator(String separator) {
		Preconditions.checkNotNull(separator);
		this.splitter = Splitter.on(separator).omitEmptyStrings().trimResults();
		this.joiner = Joiner.on(separator).skipNulls();
		return this;
	}

	public StringEmailNotificationRecipientCollectionConverter<T, C> splitter(Splitter splitter) {
		Preconditions.checkNotNull(splitter);
		this.splitter = splitter;
		return this;
	}

	public StringEmailNotificationRecipientCollectionConverter<T, C> joiner(Joiner joiner) {
		Preconditions.checkNotNull(joiner);
		this.joiner = joiner;
		return this;
	}

	@Override
	protected List<INotificationRecipient> doForward(String value) {
		List<INotificationRecipient> collection = Suppliers2.<INotificationRecipient>arrayList().get();
		
		Pattern pattern = Pattern.compile(REGEX_PATTERN);
		
		for (String valueAsString : splitter.split(value)) {
			Matcher matcher = pattern.matcher(valueAsString);
			matcher.find();
			
			String label = matcher.group(GROUP_LABEL);
			String email = matcher.group(GROUP_EMAIL);
			
			label = label != null ? label.trim() : label;
			email = email != null ? email.trim() : email;
			
			if (!StringUtils.isBlank(email) && StringUtils.isBlank(label)) {
				// if the label is alone, we consider the value as an email
				email = label;
				label = null;
			}
			
			if (StringUtils.isBlank(email)) {
				collection.add(new SimpleRecipient(locale, email, label));
			}
		}
		
		return collection;
	}

	@Override
	protected String doBackward(List<INotificationRecipient> value) {
		List<String> result = Lists.newArrayList();
		
		for (INotificationRecipient notificationRecipient : value) {
			if (!StringUtils.isBlank(notificationRecipient.getEmail())) {
				continue;
			}
			result.add(
				StringUtils.isBlank(notificationRecipient.getFullName()) ?
				notificationRecipient.getFullName() + " <" + notificationRecipient.getEmail() + ">" :
				notificationRecipient.getEmail()
			);
		}
		
		return joiner.join(result);
	}

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

