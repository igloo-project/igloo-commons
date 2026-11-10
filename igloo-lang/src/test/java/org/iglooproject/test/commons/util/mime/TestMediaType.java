package org.iglooproject.test.commons.util.mime;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.iglooproject.commons.util.mime.IMediaTypeRegistry;
import org.iglooproject.commons.util.mime.MediaType;
import org.iglooproject.commons.util.mime.MediaTypeRegistry;
import org.junit.jupiter.api.Test;

class TestMediaType {

  private static final IMediaTypeRegistry REGISTRY = MediaTypeRegistry.of(MediaType.class);

  @Test
  void testFromExtension() {
    Assertions.assertThat(MediaType.IMAGE_GIF).isSameAs(REGISTRY.fromExtension("gif"));
    Assertions.assertThat(MediaType.IMAGE_GIF).isSameAs(REGISTRY.fromExtension("GIF"));

    Assertions.assertThat(MediaType.IMAGE_JPEG).isSameAs(REGISTRY.fromExtension("jpg"));
    Assertions.assertThat(MediaType.IMAGE_JPEG).isSameAs(REGISTRY.fromExtension("jpeg"));
  }

  @Test
  void testFromMimeType() {
    Assertions.assertThat(MediaType.APPLICATION_JSON)
        .isSameAs(REGISTRY.fromMime("application/json"));
    Assertions.assertThat(MediaType.APPLICATION_JSON)
        .isSameAs(REGISTRY.fromMime("application/json;charset=UTF-8"));

    Assertions.assertThat(MediaType.IMAGE_JPEG).isSameAs(REGISTRY.fromMime("image/jpeg"));
    Assertions.assertThat(MediaType.IMAGE_PNG).isSameAs(REGISTRY.fromMime("image/png"));

    // envoyés par IE7 et 8 lors des uploads de jpg et png
    Assertions.assertThat(MediaType.IMAGE_JPEG).isSameAs(REGISTRY.fromMime("image/pjpeg"));
    Assertions.assertThat(MediaType.IMAGE_PNG).isSameAs(REGISTRY.fromMime("image/x-png"));
  }

  @Test
  void testSupports() {
    Assertions.assertThat(MediaType.IMAGE_JPEG.supports("jpg")).isTrue();
    Assertions.assertThat(MediaType.IMAGE_JPEG.supports("jpeg")).isTrue();
    Assertions.assertThat(MediaType.IMAGE_JPEG.supports("jpe")).isTrue();
    Assertions.assertThat(MediaType.IMAGE_JPEG.supports("json")).isFalse();
  }

  @Test
  void testGetters() {
    Assertions.assertThat("jpg").isEqualTo(MediaType.IMAGE_JPEG.extension());
    Assertions.assertThat("image/jpeg").isEqualTo(MediaType.IMAGE_JPEG.mime());
    Assertions.assertThat("application/json;charset=UTF-8")
        .isEqualTo(MediaType.APPLICATION_JSON.mimeUtf8());

    List<String> supportedExtensions = MediaType.IMAGE_JPEG.supportedExtensions();
    Assertions.assertThat(supportedExtensions).contains("jpg");
    Assertions.assertThat(supportedExtensions).contains("jpe");
    Assertions.assertThat(supportedExtensions).contains("jpeg");
  }
}
