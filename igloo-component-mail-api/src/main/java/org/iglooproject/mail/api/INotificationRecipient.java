package org.iglooproject.mail.api;

import java.util.Locale;

public interface INotificationRecipient {

  Locale getLocale();

  String getEmailAddress();

  String getFullName();

  boolean isNotificationEnabled();

  boolean isEnabled();
}
