package org.iglooproject.test.commons.util.mime;

import java.util.Arrays;
import java.util.Locale;
import org.assertj.core.api.Assertions;
import org.iglooproject.commons.util.mime.MediaType;
import org.junit.jupiter.api.Test;

class TestMediaType {

  @Test
  void testFromExtension() {
    Assertions.assertThat(MediaType.fromExtension("gif")).isSameAs(MediaType.IMAGE_GIF);
    Assertions.assertThat(MediaType.fromExtension(" GIF ")).isSameAs(MediaType.IMAGE_GIF);

    Assertions.assertThat(MediaType.fromExtension("jpg")).isSameAs(MediaType.IMAGE_JPEG);
    Assertions.assertThat(MediaType.fromExtension("jpeg")).isSameAs(MediaType.IMAGE_JPEG);

    Assertions.assertThat(MediaType.fromExtension("xlsm")).isSameAs(MediaType.APPLICATION_XLSM);

    Assertions.assertThat(MediaType.fromExtension("unknown")).isNull();
    Assertions.assertThat(MediaType.fromExtension(null)).isNull();
  }

  @Test
  void testFromMimeType() {
    Assertions.assertThat(MediaType.fromMimeType("application/json"))
        .isSameAs(MediaType.APPLICATION_JSON);
    Assertions.assertThat(MediaType.fromMimeType("Application/JSON; charset=UTF-8"))
        .isSameAs(MediaType.APPLICATION_JSON);

    Assertions.assertThat(MediaType.fromMimeType("image/jpeg")).isSameAs(MediaType.IMAGE_JPEG);
    Assertions.assertThat(MediaType.fromMimeType("image/png")).isSameAs(MediaType.IMAGE_PNG);

    // envoyés par IE7 et 8 lors des uploads de jpg et png
    Assertions.assertThat(MediaType.fromMimeType("image/pjpeg")).isSameAs(MediaType.IMAGE_JPEG);
    Assertions.assertThat(MediaType.fromMimeType("image/x-png")).isSameAs(MediaType.IMAGE_PNG);

    Assertions.assertThat(MediaType.fromMimeType("application/x-unknown")).isNull();
    Assertions.assertThat(MediaType.fromMimeType(null)).isNull();
  }

  @Test
  @SuppressWarnings("deprecation")
  void testDeprecated() {
    Assertions.assertThat(MediaType.fromExtension("svg")).isSameAs(MediaType.IMAGE_SVG);
    Assertions.assertThat(MediaType.fromMimeType("image/svg+xml")).isSameAs(MediaType.IMAGE_SVG);
    Assertions.assertThat(MediaType.fromExtension("ics")).isSameAs(MediaType.TEXT_CALENDAR);
  }

  @Test
  void testSupports() {
    Assertions.assertThat(MediaType.IMAGE_JPEG.supports("jpg")).isTrue();
    Assertions.assertThat(MediaType.IMAGE_JPEG.supports("JPEG")).isTrue();
    Assertions.assertThat(MediaType.IMAGE_JPEG.supports("jpe")).isTrue();
    Assertions.assertThat(MediaType.IMAGE_JPEG.supports("json")).isFalse();
    Assertions.assertThat(MediaType.IMAGE_JPEG.supports(null)).isFalse();
  }

  @Test
  void testGetters() {
    Assertions.assertThat(MediaType.IMAGE_JPEG.extension()).isEqualTo("jpg");
    Assertions.assertThat(MediaType.IMAGE_JPEG.extensions())
        .containsExactly("jpg", "jpeg", "jpe", "jfif")
        .isEqualTo(MediaType.IMAGE_JPEG.supportedExtensions());
    Assertions.assertThat(MediaType.IMAGE_JPEG.mime()).isEqualTo("image/jpeg");
    Assertions.assertThat(MediaType.IMAGE_JPEG.mimes())
        .containsExactly("image/jpeg", "image/pjpeg")
        .isEqualTo(MediaType.IMAGE_JPEG.supportedMimeTypes());
    Assertions.assertThat(MediaType.APPLICATION_JSON.mimeUtf8())
        .isEqualTo("application/json;charset=UTF-8");
  }

  @Test
  void testValues() {
    for (MediaType mediaType : MediaType.values()) {
      Assertions.assertThat(mediaType.mimes()).as(mediaType.name()).isNotEmpty();
      Assertions.assertThat(mediaType.extensions()).as(mediaType.name()).isNotEmpty();
      Assertions.assertThat(mediaType.mimes())
          .as(mediaType.name())
          .allSatisfy(
              mime ->
                  Assertions.assertThat(mime)
                      .isEqualTo(mime.trim().toLowerCase(Locale.ROOT))
                      .contains("/")
                      .doesNotContain(";"));
      Assertions.assertThat(mediaType.extensions())
          .as(mediaType.name())
          .allSatisfy(
              extension ->
                  Assertions.assertThat(extension)
                      .isEqualTo(extension.trim().toLowerCase(Locale.ROOT))
                      .doesNotStartWith("."));
    }

    Assertions.assertThat(
            Arrays.stream(MediaType.values()).flatMap(mediaType -> mediaType.mimes().stream()))
        .doesNotHaveDuplicates();
    Assertions.assertThat(
            Arrays.stream(MediaType.values()).flatMap(mediaType -> mediaType.extensions().stream()))
        .doesNotHaveDuplicates();
  }
}
