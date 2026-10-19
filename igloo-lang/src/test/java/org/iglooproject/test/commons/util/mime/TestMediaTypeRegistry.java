package org.iglooproject.test.commons.util.mime;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.iglooproject.commons.util.mime.IMediaType;
import org.iglooproject.commons.util.mime.IMediaTypeRegistry;
import org.iglooproject.commons.util.mime.MediaType;
import org.iglooproject.commons.util.mime.MediaTypeDefinition;
import org.iglooproject.commons.util.mime.MediaTypeRegistry;
import org.junit.jupiter.api.Test;

class TestMediaTypeRegistry {

  private enum ProjectMediaType implements IMediaType {
    APPLICATION_PDF(MediaTypeDefinition.from(MediaType.APPLICATION_PDF).withExtensions("pdfa")),
    TEXT_XML(MediaTypeDefinition.of("text/xml").withExtension("xml")),
    APPLICATION_FOO(MediaTypeDefinition.of("application/x-foo").withExtension("foo")),
    APPLICATION_FOO_DUPLICATE(MediaTypeDefinition.of("application/x-foo2").withExtension("foo")),
    APPLICATION_PDF_PRIMARY(MediaTypeDefinition.of("application/pdf").withExtension("pdf")),
    APPLICATION_PDF_SECONDARY(MediaTypeDefinition.of("application/x-pdf").withExtension("pdfx")),
    PDF_AND_DOC(MediaTypeDefinition.of("application/pdf").withExtension("doc")),
    DOC_EXTENSION_ONLY(MediaTypeDefinition.of("application/x-mydoc").withExtension("doc"));

    private final MediaTypeDefinition definition;

    ProjectMediaType(MediaTypeDefinition definition) {
      this.definition = definition;
    }

    @Override
    public MediaTypeDefinition definition() {
      return definition;
    }
  }

  private static IMediaTypeRegistry igloo() {
    return MediaTypeRegistry.of(MediaType.class);
  }

  private static IMediaTypeRegistry withAdditions(ProjectMediaType... additions) {
    return MediaTypeRegistry.of(List.of(MediaType.values()), List.of(additions));
  }

  @Test
  void catalogHasNoDuplicate() {
    Assertions.assertThat(igloo().values()).containsExactly(MediaType.values());
  }

  @Test
  void lookups() {
    IMediaTypeRegistry registry = igloo();

    Assertions.assertThat(registry.fromExtension("JPEG")).isSameAs(MediaType.IMAGE_JPEG);
    Assertions.assertThat(registry.fromExtension(".pdf")).isSameAs(MediaType.APPLICATION_PDF);
    Assertions.assertThat(registry.fromExtension("msg")).isSameAs(MediaType.APPLICATION_MSOUTLOOK);
    Assertions.assertThat(registry.fromExtension("exe")).isNull();
    Assertions.assertThat(registry.fromExtension(null)).isNull();
    Assertions.assertThat(registry.fromMime("Image/PJPEG")).isSameAs(MediaType.IMAGE_JPEG);
    Assertions.assertThat(registry.fromMime("application/json; charset=UTF-8"))
        .isSameAs(MediaType.APPLICATION_JSON);
    Assertions.assertThat(registry.fromMime("application/vnd.ms-word"))
        .isSameAs(MediaType.APPLICATION_MSWORD);
    Assertions.assertThat(registry.fromMime(null)).isNull();
  }

  @Test
  void fromFilename() {
    IMediaTypeRegistry registry = igloo();

    Assertions.assertThat(registry.fromFilename("dir.v2/Photo.HEIC"))
        .isSameAs(MediaType.IMAGE_HEIC);
    Assertions.assertThat(registry.fromFilename("archive.tar.gz"))
        .isSameAs(MediaType.APPLICATION_GZIP);
    Assertions.assertThat(registry.fromFilename("C:\\dir.v2\\README")).isNull();
    Assertions.assertThat(registry.fromFilename(".pdf")).isSameAs(MediaType.APPLICATION_PDF);
    Assertions.assertThat(registry.fromFilename(".gitinfo")).isNull();
    Assertions.assertThat(registry.fromFilename("document.")).isNull();
    Assertions.assertThat(registry.fromFilename(null)).isNull();
  }

  @Test
  void accepts() {
    IMediaTypeRegistry registry = igloo();

    Assertions.assertThat(registry.accepts(List.of(MediaType.IMAGE_PNG), MediaType.IMAGE_PNG))
        .isTrue();
    Assertions.assertThat(registry.accepts(List.of(MediaType.IMAGE_PNG), MediaType.IMAGE_JPEG))
        .isFalse();
    Assertions.assertThat(registry.accepts(List.of(MediaType.IMAGE_PNG), null)).isFalse();
  }

  @Test
  void overrideByPrimaryMime() {
    IMediaTypeRegistry registry = withAdditions(ProjectMediaType.APPLICATION_PDF);

    Assertions.assertThat(registry.fromExtension("pdf")).isSameAs(ProjectMediaType.APPLICATION_PDF);
    Assertions.assertThat(registry.fromExtension("pdfa"))
        .isSameAs(ProjectMediaType.APPLICATION_PDF);
    Assertions.assertThat(registry.fromMime("application/x-pdf"))
        .isSameAs(ProjectMediaType.APPLICATION_PDF);
    Assertions.assertThat(registry.canonical(MediaType.APPLICATION_PDF))
        .isSameAs(ProjectMediaType.APPLICATION_PDF);
    Assertions.assertThat(registry.values())
        .contains(ProjectMediaType.APPLICATION_PDF)
        .doesNotContain(MediaType.APPLICATION_PDF);
    Assertions.assertThat(registry.overrides())
        .containsExactly(Map.entry(MediaType.APPLICATION_PDF, ProjectMediaType.APPLICATION_PDF));
    Assertions.assertThat(
            registry.accepts(List.of(MediaType.APPLICATION_PDF), registry.fromFilename("a.pdf")))
        .isTrue();
  }

  @Test
  void overrideBySecondaryMime() {
    IMediaTypeRegistry registry = withAdditions(ProjectMediaType.TEXT_XML);

    Assertions.assertThat(registry.fromExtension("xml")).isSameAs(ProjectMediaType.TEXT_XML);
    Assertions.assertThat(registry.fromMime("application/xml")).isNull();
    Assertions.assertThat(registry.canonical(MediaType.APPLICATION_XML))
        .isSameAs(ProjectMediaType.TEXT_XML);
  }

  @Test
  void addition() {
    IMediaTypeRegistry registry = withAdditions(ProjectMediaType.APPLICATION_FOO);

    Assertions.assertThat(registry.fromExtension("foo")).isSameAs(ProjectMediaType.APPLICATION_FOO);
    Assertions.assertThat(registry.values()).endsWith(ProjectMediaType.APPLICATION_FOO);
    Assertions.assertThat(registry.overrides()).isEmpty();
    Assertions.assertThat(registry.canonical(ProjectMediaType.APPLICATION_FOO))
        .isSameAs(ProjectMediaType.APPLICATION_FOO);
  }

  @Test
  void conflicts() {
    Assertions.assertThatIllegalStateException()
        .isThrownBy(() -> withAdditions(ProjectMediaType.PDF_AND_DOC));
    Assertions.assertThatIllegalStateException()
        .isThrownBy(() -> withAdditions(ProjectMediaType.DOC_EXTENSION_ONLY));
    Assertions.assertThatIllegalStateException()
        .isThrownBy(
            () ->
                withAdditions(
                    ProjectMediaType.APPLICATION_PDF_PRIMARY,
                    ProjectMediaType.APPLICATION_PDF_SECONDARY))
        .withMessageContaining("both override");
    Assertions.assertThatIllegalStateException()
        .isThrownBy(
            () ->
                withAdditions(
                    ProjectMediaType.APPLICATION_FOO, ProjectMediaType.APPLICATION_FOO_DUPLICATE))
        .withMessageContaining("declared by both");
  }

  @Test
  void enumOnly() {
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(() -> MediaTypeRegistry.of(IMediaType.class));
  }
}
