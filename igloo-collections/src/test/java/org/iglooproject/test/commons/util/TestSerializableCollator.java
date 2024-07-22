package org.iglooproject.test.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import org.iglooproject.commons.util.ordering.SerializableCollator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSerializableCollator {

  private static final Iterable<Collection<String>> COMPARISON_TEST_DATA =
      ImmutableList.<Collection<String>>of(
          ImmutableList.of("this", "is", "test", "data", "Test"),
          ImmutableList.of(
              "Et un", "autre", "aûtre", "avéc", "avec", "dès", "des", "dés", "àcçents",
              "accents"));

  private static Iterable<Locale> locales() {
    return ImmutableList.of(Locale.ROOT, Locale.FRENCH, Locale.ENGLISH, Locale.US, Locale.UK);
  }

  private static Iterable<Integer> strengths() {
    return ImmutableList.of(
        Collator.IDENTICAL, Collator.TERTIARY, Collator.SECONDARY, Collator.PRIMARY);
  }

  private static Iterable<Integer> decompositions() {
    return ImmutableList.of(
        Collator.FULL_DECOMPOSITION, Collator.CANONICAL_DECOMPOSITION, Collator.NO_DECOMPOSITION);
  }

  private static Stream<Arguments> data() {
    ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
    for (Locale locale : locales()) {
      for (int strength : strengths()) {
        for (int decomposition : decompositions()) {
          Collator collator = Collator.getInstance(locale);
          SerializableCollator serializableCollator = new SerializableCollator(locale);
          collator.setStrength(strength);
          serializableCollator.setStrength(strength);
          collator.setDecomposition(decomposition);
          serializableCollator.setDecomposition(decomposition);
          builder.add(Arguments.of(collator, serializableCollator));
        }
      }
    }
    return builder.build().stream();
  }

  @SuppressWarnings("unchecked")
  private static <T> T serializeAndDeserialize(T object) {
    byte[] array;

    try {
      ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
      ObjectOutputStream objectOut = new ObjectOutputStream(arrayOut);
      objectOut.writeObject(object);
      array = arrayOut.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Error while serializing " + object, e);
    }

    try {
      ByteArrayInputStream arrayIn = new ByteArrayInputStream(array);
      ObjectInputStream objectIn = new ObjectInputStream(arrayIn);
      return (T) objectIn.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException("Error while deserializing " + object, e);
    }
  }

  @ParameterizedTest
  @MethodSource("data")
  void testSameComparisonAsCollator(Collator collator, SerializableCollator serializableCollator) {
    doTestSameComparison(collator, serializableCollator);
  }

  @ParameterizedTest
  @MethodSource("data")
  void testSerialization(Collator collator, SerializableCollator serializableCollator) {
    SerializableCollator deserialized = serializeAndDeserialize(serializableCollator);
    assertEquals(serializableCollator, deserialized);
    doTestSameComparison(collator, deserialized);
  }

  public static void doTestSameComparison(
      Comparator<? super String> expected, Comparator<? super String> actual) {
    for (Collection<String> collection : COMPARISON_TEST_DATA) {
      List<String> expectedList = Lists.newArrayList(collection);
      List<String> actualList = Lists.newArrayList(collection);
      Collections.sort(expectedList, expected);
      Collections.sort(actualList, actual);

      assertEquals(expectedList, actualList);
    }
  }
}
