package org.iglooproject.functional.converter;

import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.util.Collection;
import org.iglooproject.functional.Supplier2;

public class StringCollectionConverter<T, C extends Collection<T>>
    extends SerializableConverter2<String, C> {

  private static final long serialVersionUID = 8592902832190269705L;

  private final Converter<String, T> converter;

  private final Supplier2<? extends C> supplier;

  private Splitter splitter;

  private Joiner joiner;

  public StringCollectionConverter(
      Converter<String, T> converter, Supplier2<? extends C> supplier) {
    this.converter = converter;
    this.supplier = supplier;
    separator(" ");
  }

  public StringCollectionConverter<T, C> joiner(Joiner joiner) {
    Preconditions.checkNotNull(joiner);
    this.joiner = joiner;
    return this;
  }

  public StringCollectionConverter<T, C> splitter(Splitter splitter) {
    Preconditions.checkNotNull(splitter);
    this.splitter = splitter;
    return this;
  }

  public StringCollectionConverter<T, C> separator(String separator) {
    Preconditions.checkNotNull(separator);
    this.splitter = Splitter.on(separator).omitEmptyStrings().trimResults();
    this.joiner = Joiner.on(separator).skipNulls();
    return this;
  }

  @Override
  protected C doForward(String a) {
    C collection = supplier.get();
    for (String valueAsString : splitter.split(a)) {
      T value = converter.convert(valueAsString);
      if (value != null) {
        collection.add(value);
      }
    }
    return collection;
  }

  @Override
  protected String doBackward(C b) {
    return joiner.join(Iterables.transform(b, converter.reverse()));
  }

  /**
   * Workaround sonar/findbugs - https://github.com/google/guava/issues/1858 Guava Converter
   * overrides only equals to add javadoc, but findbugs warns about non coherent equals/hashcode
   * possible issue.
   */
  @Override
  public boolean equals(Object object) {
    return super.equals(object);
  }

  /** Workaround sonar/findbugs - see #equals(Object) */
  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
