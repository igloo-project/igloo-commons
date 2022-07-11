package org.iglooproject.mail.api;

import java.util.Locale;

public interface INotificationRecipient {

	Locale getLocale();

	String getEmail();

	String getFullName();

	boolean isNotificationEnabled();

	boolean isEnabled();

}
