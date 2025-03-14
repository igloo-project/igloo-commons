package org.iglooproject.mail.api;

import java.util.Locale;

public interface INotificationRecipient {

  String getNotificationEmailAddress();

  boolean isNotificationEnabled();

  String getFullName();

  Locale getLocale();

  boolean isEnabled();
}
