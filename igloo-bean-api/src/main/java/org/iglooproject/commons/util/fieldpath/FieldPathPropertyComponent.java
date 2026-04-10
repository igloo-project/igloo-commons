package org.iglooproject.commons.util.fieldpath;

import java.util.Objects;

public class FieldPathPropertyComponent extends FieldPathComponent {

  private static final long serialVersionUID = 2111693502794024737L;

  public static final char PROPERTY_SEPARATOR_CHAR = '.';

  public static final String PROPERTY_SEPARATOR = String.valueOf(PROPERTY_SEPARATOR_CHAR);

  private final String propertyName;

  public FieldPathPropertyComponent(String name) {
    super();
    this.propertyName = name;
  }

  @Override
  public String getName() {
    return propertyName;
  }

  @Override
  public void appendTo(StringBuilder builder) {
    builder.append(PROPERTY_SEPARATOR);
    builder.append(propertyName);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof FieldPathPropertyComponent other)) {
      return false;
    }
    return Objects.equals(propertyName, other.propertyName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(propertyName);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    appendTo(builder);
    return builder.toString();
  }
}
