package org.iglooproject.test.commons.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.iglooproject.commons.util.CloneUtils;
import org.junit.jupiter.api.Test;

class TestCloneUtils {

  @Test
  void testCloneDate() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.set(2010, 5, 24, 17, 0, 15);

    Date date1 = calendar.getTime();

    Date date2 = CloneUtils.clone(date1);

    assertThat(date1).isNotSameAs(date2).isEqualTo(date2);
    assertThat(date1.getTime()).isEqualTo(date2.getTime());

    Date date3 = null;

    Date date4 = CloneUtils.clone(date3);

    assertThat(date4).isNull();
  }

  @Test
  void testCloneArray() {
    String[] strArray1 = {"Chaine1", "Chaine2", "Chaine3"};

    String[] strArray2 = CloneUtils.clone(strArray1);

    assertThat(strArray1).isNotSameAs(strArray2).isEqualTo(strArray2);

    String[] strArray3 = null;

    String[] strArray4 = CloneUtils.clone(strArray3);

    assertThat(strArray4).isNull();
  }
}
