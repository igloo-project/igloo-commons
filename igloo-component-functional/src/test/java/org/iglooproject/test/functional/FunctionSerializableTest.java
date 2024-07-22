package org.iglooproject.test.functional;

import com.google.common.collect.ImmutableMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Function;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.iglooproject.functional.Functions2;
import org.iglooproject.functional.SerializableFunction2;
import org.iglooproject.functional.SerializablePredicate2;
import org.iglooproject.functional.SerializableSupplier2;
import org.iglooproject.functional.Suppliers2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FunctionSerializableTest {

  private static final SerializableFunction2<Object, Object> FUNCTION_SERIALIZABLE =
      new SerializableFunction2<Object, Object>() {
        private static final long serialVersionUID = 1L;

        @Override
        public Object apply(Object t) {
          return t;
        }
      };

  private static final SerializablePredicate2<Object> PREDICATE_SERIALIZABLE =
      new SerializablePredicate2<Object>() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean test(Object t) {
          return true;
        }
      };

  private static final SerializableSupplier2<Object> SUPPLIER_SERIALIZABLE =
      new SerializableSupplier2<Object>() {
        private static final long serialVersionUID = 1L;

        @Override
        public Object get() {
          return null;
        }
      };

  public static Stream<Arguments> data() {
    return Stream.of(
        Arguments.arguments(FUNCTION_SERIALIZABLE.compose(FUNCTION_SERIALIZABLE)),
        Arguments.arguments(FUNCTION_SERIALIZABLE.andThen(FUNCTION_SERIALIZABLE)),
        Arguments.arguments(SerializableFunction2.identity()),
        Arguments.arguments(Functions2.from(FUNCTION_SERIALIZABLE)),
        Arguments.arguments(Functions2.identity()),
        Arguments.arguments(Functions2.toStringFunction()),
        Arguments.arguments(Functions2.forMap(ImmutableMap.of(1337L, "Igloo"))),
        Arguments.arguments(Functions2.forMap(ImmutableMap.of(1337L, "Igloo"), "Igloo")),
        Arguments.arguments(
            Functions2.forMap(ImmutableMap.of(1337L, "Igloo"), FUNCTION_SERIALIZABLE)),
        Arguments.arguments(Functions2.compose(FUNCTION_SERIALIZABLE, FUNCTION_SERIALIZABLE)),
        Arguments.arguments(Functions2.andThen(FUNCTION_SERIALIZABLE, FUNCTION_SERIALIZABLE)),
        Arguments.arguments(Functions2.forPredicate(PREDICATE_SERIALIZABLE)),
        Arguments.arguments(Functions2.constant("Igloo")),
        Arguments.arguments(Functions2.forSupplier(SUPPLIER_SERIALIZABLE)),
        Arguments.arguments(Functions2.transformedIterable(FUNCTION_SERIALIZABLE)),
        Arguments.arguments(Functions2.transformedCollection(FUNCTION_SERIALIZABLE)),
        Arguments.arguments(Functions2.transformedList(FUNCTION_SERIALIZABLE)),
        Arguments.arguments(Functions2.unmodifiableCollection()),
        Arguments.arguments(Functions2.unmodifiableList()),
        Arguments.arguments(Functions2.unmodifiableSet()),
        Arguments.arguments(Functions2.unmodifiableSortedSet()),
        Arguments.arguments(Functions2.unmodifiableMap()),
        Arguments.arguments(Functions2.unmodifiableTable()),
        Arguments.arguments(Functions2.first()),
        Arguments.arguments(Functions2.defaultValue(PREDICATE_SERIALIZABLE, FUNCTION_SERIALIZABLE)),
        Arguments.arguments(Functions2.defaultValue("Igloo")),
        Arguments.arguments(Functions2.entryKey()),
        Arguments.arguments(Functions2.entryValue()),
        Arguments.arguments(Functions2.entryToPair()),
        Arguments.arguments(Functions2.tupleValue0()),
        Arguments.arguments(Functions2.tupleValue1()),
        Arguments.arguments(Functions2.capitalize()),
        Arguments.arguments(Functions2.uncapitalize()),
        Arguments.arguments(Suppliers2.supplierFunction()));
  }

  @ParameterizedTest
  @MethodSource("data")
  void testSerializable(Function<Object, Object> function) {
    Assertions.assertThatCode(() -> doSerializeAndDeserialize(function)).doesNotThrowAnyException();
  }

  @SuppressWarnings("unchecked")
  private static <T> T doSerializeAndDeserialize(T object) {
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
}
