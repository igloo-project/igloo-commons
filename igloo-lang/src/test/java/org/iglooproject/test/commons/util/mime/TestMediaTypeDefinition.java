package org.iglooproject.test.commons.util.mime;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.iglooproject.commons.util.mime.MediaType;
import org.iglooproject.commons.util.mime.MediaTypeDefinition;
import org.junit.jupiter.api.Test;

class TestMediaTypeDefinition {

  @Test
  void primaryValuesFirst() {
    MediaTypeDefinition definition =
        MediaTypeDefinition.of("Image/JPEG")
            .withMimes("image/pjpeg")
            .withExtension("JPG")
            .withExtensions("jpeg");

    Assertions.assertThat(definition.mime()).isEqualTo("image/jpeg");
    Assertions.assertThat(definition.mimes()).containsExactly("image/jpeg", "image/pjpeg");
    Assertions.assertThat(definition.extension()).isEqualTo("jpg");
    Assertions.assertThat(definition.extensions()).containsExactly("jpg", "jpeg");
  }

  @Test
  void canonicalConstructor() {
    Assertions.assertThat(new MediaTypeDefinition(List.of(" Image/PNG "), List.of("PNG")))
        .isEqualTo(MediaTypeDefinition.of("image/png").withExtension("png"))
        .hasSameHashCodeAs(MediaTypeDefinition.of("image/png").withExtension("png"));
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(() -> new MediaTypeDefinition(List.of(), List.of()));
  }

  @Test
  void withoutExtension() {
    MediaTypeDefinition definition = MediaTypeDefinition.of("image/*");

    Assertions.assertThat(definition.extension()).isNull();
    Assertions.assertThat(definition.extensions()).isEmpty();
    Assertions.assertThatIllegalStateException().isThrownBy(() -> definition.withExtensions("jpg"));
  }

  @Test
  void fromCompletesExistingDefinition() {
    MediaTypeDefinition definition =
        MediaTypeDefinition.from(MediaType.TEXT_PLAIN).withExtensions("conf");

    Assertions.assertThat(definition.mimes()).containsExactly("text/plain");
    Assertions.assertThat(definition.extensions()).containsExactly("txt", "conf");
    Assertions.assertThat(MediaType.TEXT_PLAIN.extensions()).containsExactly("txt");
    Assertions.assertThatIllegalStateException().isThrownBy(() -> definition.withExtension("text"));
  }

  @Test
  void invalidValues() {
    Assertions.assertThatIllegalArgumentException().isThrownBy(() -> MediaTypeDefinition.of("pdf"));
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(() -> MediaTypeDefinition.of("text/plain; charset=UTF-8"));
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(() -> MediaTypeDefinition.of("text/plain").withExtension(".txt"));
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(() -> MediaTypeDefinition.of("text/plain").withExtension(" "));
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(() -> MediaTypeDefinition.of("text/plain").withMimes("TEXT/PLAIN"));
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(
            () -> MediaTypeDefinition.of("text/plain").withExtension("txt").withExtensions("txt"));
  }
}
