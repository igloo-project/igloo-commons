package org.iglooproject.commons.util.time;

import java.io.Serializable;

public interface IDateTimePattern extends Serializable {

  String getKey();

  static IDateTimePattern getShortDateTime() {
    return null;
  }
}
