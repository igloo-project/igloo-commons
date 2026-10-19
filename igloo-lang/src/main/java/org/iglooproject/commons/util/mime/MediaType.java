package org.iglooproject.commons.util.mime;

import java.util.List;
import java.util.Locale;
import org.bindgen.Bindable;

@Bindable
public enum MediaType implements IMediaType {
  APPLICATION_GZIP(
      MediaTypeDefinition.of("application/gzip")
          .withMimes("application/x-gzip")
          .withExtension("gz")
          .withExtensions("tgz")),
  APPLICATION_JAVA_ARCHIVE(MediaTypeDefinition.of("application/java-archive").withExtension("jar")),
  APPLICATION_JSON(MediaTypeDefinition.of("application/json").withExtension("json")),
  APPLICATION_MSWORD(
      MediaTypeDefinition.of("application/msword")
          .withMimes("application/vnd.ms-word")
          .withExtension("doc")),
  APPLICATION_OCTET_STREAM(MediaTypeDefinition.of("application/octet-stream").withExtension("bin")),
  APPLICATION_PDF(
      MediaTypeDefinition.of("application/pdf")
          .withMimes("application/x-pdf")
          .withExtension("pdf")),
  APPLICATION_RTF(
      MediaTypeDefinition.of("application/rtf").withMimes("text/rtf").withExtension("rtf")),
  APPLICATION_MSEXCEL(
      MediaTypeDefinition.of("application/vnd.ms-excel")
          .withMimes("application/msexcel")
          .withExtension("xls")),
  APPLICATION_MSOUTLOOK(MediaTypeDefinition.of("application/vnd.ms-outlook").withExtension("msg")),
  APPLICATION_MSPOWERPOINT(
      MediaTypeDefinition.of("application/vnd.ms-powerpoint")
          .withMimes("application/mspowerpoint")
          .withExtension("ppt")),
  APPLICATION_ODP(
      MediaTypeDefinition.of("application/vnd.oasis.opendocument.presentation")
          .withExtension("odp")),
  APPLICATION_ODS(
      MediaTypeDefinition.of("application/vnd.oasis.opendocument.spreadsheet")
          .withExtension("ods")),
  APPLICATION_ODT(
      MediaTypeDefinition.of("application/vnd.oasis.opendocument.text").withExtension("odt")),
  APPLICATION_PPTX(
      MediaTypeDefinition.of(
              "application/vnd.openxmlformats-officedocument.presentationml.presentation")
          .withExtension("pptx")),
  APPLICATION_XLSX(
      MediaTypeDefinition.of("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
          .withExtension("xlsx")),
  APPLICATION_DOCX(
      MediaTypeDefinition.of(
              "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
          .withExtension("docx")),
  APPLICATION_RAR(
      MediaTypeDefinition.of("application/vnd.rar")
          .withMimes("application/x-rar-compressed", "application/x-rar")
          .withExtension("rar")),
  APPLICATION_7Z(MediaTypeDefinition.of("application/x-7z-compressed").withExtension("7z")),
  APPLICATION_TAR(MediaTypeDefinition.of("application/x-tar").withExtension("tar")),
  APPLICATION_XML(
      MediaTypeDefinition.of("application/xml").withMimes("text/xml").withExtension("xml")),
  APPLICATION_ZIP(
      MediaTypeDefinition.of("application/zip")
          .withMimes("application/x-zip-compressed")
          .withExtension("zip")),

  AUDIO_AAC(MediaTypeDefinition.of("audio/aac").withMimes("audio/x-aac").withExtension("aac")),
  AUDIO_FLAC(MediaTypeDefinition.of("audio/flac").withMimes("audio/x-flac").withExtension("flac")),
  AUDIO_MP4(MediaTypeDefinition.of("audio/mp4").withMimes("audio/x-m4a").withExtension("m4a")),
  AUDIO_MPEG(MediaTypeDefinition.of("audio/mpeg").withExtension("mp3")),
  AUDIO_OGG(
      MediaTypeDefinition.of("audio/ogg")
          .withMimes("audio/vorbis")
          .withExtension("ogg")
          .withExtensions("oga")),
  AUDIO_OPUS(MediaTypeDefinition.of("audio/opus").withExtension("opus")),
  AUDIO_WAV(
      MediaTypeDefinition.of("audio/vnd.wave")
          .withMimes("audio/wav", "audio/wave", "audio/x-wav")
          .withExtension("wav")),

  IMAGE_AVIF(MediaTypeDefinition.of("image/avif").withExtension("avif")),
  IMAGE_BMP(
      MediaTypeDefinition.of("image/bmp")
          .withMimes("image/x-ms-bmp", "image/x-bmp")
          .withExtension("bmp")),
  IMAGE_GIF(MediaTypeDefinition.of("image/gif").withExtension("gif")),
  IMAGE_HEIC(MediaTypeDefinition.of("image/heic").withExtension("heic")),
  IMAGE_HEIF(MediaTypeDefinition.of("image/heif").withExtension("heif")),
  IMAGE_JPEG(
      MediaTypeDefinition.of("image/jpeg")
          .withMimes("image/pjpeg")
          .withExtension("jpg")
          .withExtensions("jpeg", "jpe", "jfif")),
  IMAGE_PNG(MediaTypeDefinition.of("image/png").withMimes("image/x-png").withExtension("png")),
  IMAGE_TIFF(MediaTypeDefinition.of("image/tiff").withExtension("tif").withExtensions("tiff")),
  IMAGE_WEBP(MediaTypeDefinition.of("image/webp").withExtension("webp")),

  TEXT_CSS(MediaTypeDefinition.of("text/css").withExtension("css")),
  TEXT_CSV(MediaTypeDefinition.of("text/csv").withExtension("csv")),
  TEXT_HTML(MediaTypeDefinition.of("text/html").withExtension("html").withExtensions("htm")),
  TEXT_JAVASCRIPT(
      MediaTypeDefinition.of("text/javascript")
          .withMimes("application/javascript", "application/x-javascript")
          .withExtension("js")
          .withExtensions("mjs")),
  TEXT_PLAIN(MediaTypeDefinition.of("text/plain").withExtension("txt")),

  VIDEO_MATROSKA(
      MediaTypeDefinition.of("video/matroska").withMimes("video/x-matroska").withExtension("mkv")),
  VIDEO_MP4(
      MediaTypeDefinition.of("video/mp4")
          .withMimes("video/x-m4v")
          .withExtension("mp4")
          .withExtensions("m4v")),
  VIDEO_MPEG(MediaTypeDefinition.of("video/mpeg").withExtension("mpeg").withExtensions("mpg")),
  VIDEO_OGG(MediaTypeDefinition.of("video/ogg").withExtension("ogv")),
  VIDEO_QUICKTIME(MediaTypeDefinition.of("video/quicktime").withExtension("mov")),
  VIDEO_WEBM(MediaTypeDefinition.of("video/webm").withExtension("webm")),
  VIDEO_AVI(
      MediaTypeDefinition.of("video/x-msvideo")
          .withMimes("video/vnd.avi", "video/avi", "video/msvideo")
          .withExtension("avi"));

  private final MediaTypeDefinition definition;

  MediaType(MediaTypeDefinition definition) {
    this.definition = definition;
  }

  @Override
  public MediaTypeDefinition definition() {
    return definition;
  }

  @Override
  public String mime() {
    return definition.mime();
  }

  public String mimeUtf8() {
    return "%s;charset=UTF-8".formatted(mime());
  }

  @Override
  public String extension() {
    return definition.extension();
  }

  public List<String> supportedExtensions() {
    return definition.extensions();
  }

  public List<String> supportedMimeTypes() {
    return definition.mimes();
  }

  public boolean supports(String extension) {
    return extension != null
        && definition.extensions().contains(extension.trim().toLowerCase(Locale.ROOT));
  }
}
