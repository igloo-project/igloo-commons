package org.iglooproject.mail.api;

import java.util.Locale;

public class SimpleRecipient implements INotificationRecipient {

  private final Locale locale;

  private final String emailAddress;

  private final String fullName;

  public SimpleRecipient(Locale locale, String emailAddress, String fullName) {
    super();
    this.locale = locale;
    this.emailAddress = emailAddress;
    this.fullName = fullName;
  }

  public SimpleRecipient(INotificationRecipient recipient, String emailAddress) {
    this(recipient.getLocale(), emailAddress, recipient.getFullName());
  }

  @Override
  public Locale getLocale() {
    return locale;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  @Override
  public String getNotificationEmailAddress() {
    return getEmailAddress();
  }

  @Override
  public String getFullName() {
    return fullName;
  }

  @Override
  public boolean isNotificationEnabled() {
    return isEnabled();
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
