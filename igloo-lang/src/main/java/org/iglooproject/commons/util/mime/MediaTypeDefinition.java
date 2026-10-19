package org.iglooproject.commons.util.mime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public record MediaTypeDefinition(List<String> mimes, List<String> extensions)
    implements Serializable {

  public MediaTypeDefinition {
    mimes = normalize(mimes, MediaTypeDefinition::normalizeMime);
    extensions = normalize(extensions, MediaTypeDefinition::normalizeExtension);
    if (mimes.isEmpty()) {
      throw new IllegalArgumentException("At least one MIME type is required");
    }
  }

  public static MediaTypeDefinition of(String mime) {
    return new MediaTypeDefinition(List.of(mime), List.of());
  }

  public static MediaTypeDefinition from(IMediaType mediaType) {
    return mediaType.definition();
  }

  public MediaTypeDefinition withMimes(String... mimes) {
    return new MediaTypeDefinition(concat(this.mimes, mimes), extensions);
  }

  public MediaTypeDefinition withExtension(String extension) {
    if (!extensions.isEmpty()) {
      throw new IllegalStateException(
          "Primary extension already defined for %s: %s".formatted(mime(), extension()));
    }
    return new MediaTypeDefinition(mimes, List.of(extension));
  }

  public MediaTypeDefinition withExtensions(String... extensions) {
    if (this.extensions.isEmpty()) {
      throw new IllegalStateException(
          "Primary extension must be defined before additional extensions for %s"
              .formatted(mime()));
    }
    return new MediaTypeDefinition(mimes, concat(this.extensions, extensions));
  }

  public String mime() {
    return mimes.get(0);
  }

  public String extension() {
    return extensions.isEmpty() ? null : extensions.get(0);
  }

  private static List<String> concat(List<String> values, String... additionalValues) {
    return Stream.concat(values.stream(), Arrays.stream(additionalValues)).toList();
  }

  private static List<String> normalize(List<String> values, UnaryOperator<String> normalizer) {
    List<String> normalized = new ArrayList<>();
    for (String value : Objects.requireNonNull(values)) {
      String normalizedValue = normalizer.apply(value);
      if (normalized.contains(normalizedValue)) {
        throw new IllegalArgumentException("Duplicate value %s".formatted(normalizedValue));
      }
      normalized.add(normalizedValue);
    }
    return List.copyOf(normalized);
  }

  private static String normalizeMime(String mime) {
    String value = normalizeValue(mime);
    if (value.indexOf('/') <= 0 || value.indexOf(';') != -1) {
      throw new IllegalArgumentException("Invalid MIME type: %s".formatted(mime));
    }
    return value;
  }

  private static String normalizeExtension(String extension) {
    String value = normalizeValue(extension);
    if (value.startsWith(".")) {
      throw new IllegalArgumentException(
          "Extension must not start with a dot: %s".formatted(extension));
    }
    return value;
  }

  private static String normalizeValue(String value) {
    Objects.requireNonNull(value);
    String normalized = value.trim().toLowerCase(Locale.ROOT);
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("Blank value");
    }
    return normalized;
  }
}
