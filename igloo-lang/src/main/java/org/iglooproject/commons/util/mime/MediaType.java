package org.iglooproject.commons.util.mime;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import org.bindgen.Bindable;

@Bindable
public enum MediaType {
  APPLICATION_GZIP(List.of("application/gzip", "application/x-gzip"), List.of("gz", "tgz")),
  APPLICATION_JAVA_ARCHIVE(List.of("application/java-archive"), List.of("jar")),
  APPLICATION_JSON(List.of("application/json"), List.of("json")),
  APPLICATION_MSWORD(List.of("application/msword", "application/vnd.ms-word"), List.of("doc")),
  APPLICATION_OCTET_STREAM(List.of("application/octet-stream"), List.of("bin")),
  APPLICATION_PDF(List.of("application/pdf", "application/x-pdf"), List.of("pdf")),
  APPLICATION_RTF(List.of("application/rtf", "text/rtf"), List.of("rtf")),
  APPLICATION_MSEXCEL(List.of("application/vnd.ms-excel", "application/msexcel"), List.of("xls")),
  APPLICATION_MSOUTLOOK(List.of("application/vnd.ms-outlook"), List.of("msg")),
  APPLICATION_MSPOWERPOINT(
      List.of("application/vnd.ms-powerpoint", "application/mspowerpoint"), List.of("ppt")),
  APPLICATION_ODP(List.of("application/vnd.oasis.opendocument.presentation"), List.of("odp")),
  APPLICATION_ODS(List.of("application/vnd.oasis.opendocument.spreadsheet"), List.of("ods")),
  APPLICATION_ODT(List.of("application/vnd.oasis.opendocument.text"), List.of("odt")),
  APPLICATION_PPTX(
      List.of("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
      List.of("pptx")),
  APPLICATION_XLSX(
      List.of("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
      List.of("xlsx")),
  APPLICATION_XLSM(List.of("application/vnd.ms-excel.sheet.macroenabled.12"), List.of("xlsm")),
  APPLICATION_DOCX(
      List.of("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
      List.of("docx")),
  APPLICATION_RAR(
      List.of("application/vnd.rar", "application/x-rar-compressed", "application/x-rar"),
      List.of("rar")),
  APPLICATION_7Z(List.of("application/x-7z-compressed"), List.of("7z")),
  APPLICATION_TAR(List.of("application/x-tar"), List.of("tar")),
  APPLICATION_XML(List.of("application/xml", "text/xml"), List.of("xml")),
  APPLICATION_ZIP(List.of("application/zip", "application/x-zip-compressed"), List.of("zip")),

  AUDIO_AAC(List.of("audio/aac", "audio/x-aac"), List.of("aac")),
  AUDIO_FLAC(List.of("audio/flac", "audio/x-flac"), List.of("flac")),
  AUDIO_MP4(List.of("audio/mp4", "audio/x-m4a"), List.of("m4a")),
  AUDIO_MPEG(List.of("audio/mpeg"), List.of("mp3")),
  AUDIO_OGG(List.of("audio/ogg", "audio/vorbis"), List.of("ogg", "oga")),
  AUDIO_OPUS(List.of("audio/opus"), List.of("opus")),
  AUDIO_WAV(List.of("audio/vnd.wave", "audio/wav", "audio/wave", "audio/x-wav"), List.of("wav")),

  IMAGE_AVIF(List.of("image/avif"), List.of("avif")),
  IMAGE_BMP(List.of("image/bmp", "image/x-ms-bmp", "image/x-bmp"), List.of("bmp")),
  IMAGE_GIF(List.of("image/gif"), List.of("gif")),
  IMAGE_HEIC(List.of("image/heic"), List.of("heic")),
  IMAGE_HEIF(List.of("image/heif"), List.of("heif")),
  IMAGE_JPEG(List.of("image/jpeg", "image/pjpeg"), List.of("jpg", "jpeg", "jpe", "jfif")),
  IMAGE_PNG(List.of("image/png", "image/x-png"), List.of("png")),
  IMAGE_TIFF(List.of("image/tiff"), List.of("tif", "tiff")),
  IMAGE_WEBP(List.of("image/webp"), List.of("webp")),

  TEXT_CSS(List.of("text/css"), List.of("css")),
  TEXT_CSV(List.of("text/csv"), List.of("csv")),
  TEXT_HTML(List.of("text/html"), List.of("html", "htm")),
  TEXT_JAVASCRIPT(
      List.of("text/javascript", "application/javascript", "application/x-javascript"),
      List.of("js", "mjs")),
  TEXT_PLAIN(List.of("text/plain"), List.of("txt")),

  VIDEO_MATROSKA(List.of("video/matroska", "video/x-matroska"), List.of("mkv")),
  VIDEO_MP4(List.of("video/mp4", "video/x-m4v"), List.of("mp4", "m4v")),
  VIDEO_MPEG(List.of("video/mpeg"), List.of("mpeg", "mpg")),
  VIDEO_OGG(List.of("video/ogg"), List.of("ogv")),
  VIDEO_QUICKTIME(List.of("video/quicktime"), List.of("mov")),
  VIDEO_WEBM(List.of("video/webm"), List.of("webm")),
  VIDEO_AVI(
      List.of("video/x-msvideo", "video/vnd.avi", "video/avi", "video/msvideo"), List.of("avi")),

  @Deprecated
  APPLICATION_GPX(List.of("application/gpx+xml"), List.of("gpx")),
  @Deprecated
  APPLICATION_KML(List.of("application/vnd.google-earth.kml+xml"), List.of("kml")),
  @Deprecated
  APPLICATION_KMZ(List.of("application/vnd.google-earth.kmz"), List.of("kmz")),
  @Deprecated
  APPLICATION_ODF_CHART(List.of("application/vnd.oasis.opendocument.chart"), List.of("odc")),
  @Deprecated
  APPLICATION_SWF(List.of("application/x-shockwave-flash"), List.of("swf")),
  @Deprecated
  IMAGE_JPEG2000(List.of("image/jp2"), List.of("jp2")),
  @Deprecated
  IMAGE_PSD(List.of("image/x-photoshop"), List.of("psd")),
  @Deprecated
  IMAGE_SVG(List.of("image/svg+xml"), List.of("svg")),
  @Deprecated
  IMAGE_X_ICON(List.of("image/x-icon"), List.of("ico")),
  @Deprecated
  TEXT_CALENDAR(List.of("text/calendar"), List.of("ics"));

  private static final Map<String, MediaType> MIME_TYPE_MAPPING = index(MediaType::mimes);

  private static final Map<String, MediaType> EXTENSION_MAPPING = index(MediaType::extensions);

  private final List<String> mimes;

  private final List<String> extensions;

  MediaType(List<String> mimes, List<String> extensions) {
    this.mimes = mimes;
    this.extensions = extensions;
  }

  public String mime() {
    return mimes.get(0);
  }

  public List<String> mimes() {
    return mimes;
  }

  public String mimeUtf8() {
    return "%s;charset=UTF-8".formatted(mime());
  }

  public String extension() {
    return extensions.get(0);
  }

  public List<String> extensions() {
    return extensions;
  }

  public List<String> supportedExtensions() {
    return extensions;
  }

  public List<String> supportedMimeTypes() {
    return mimes;
  }

  public boolean supports(String extension) {
    return extension != null && extensions.contains(normalize(extension));
  }

  public static MediaType fromExtension(String extension) {
    if (extension == null) {
      return null;
    }
    return EXTENSION_MAPPING.get(normalize(extension));
  }

  public static MediaType fromMimeType(String mimeType) {
    if (mimeType == null) {
      return null;
    }
    int parameterStart = mimeType.indexOf(';');
    return MIME_TYPE_MAPPING.get(
        normalize(parameterStart == -1 ? mimeType : mimeType.substring(0, parameterStart)));
  }

  private static Map<String, MediaType> index(Function<MediaType, List<String>> keys) {
    Map<String, MediaType> index = new HashMap<>();
    for (MediaType mediaType : values()) {
      for (String key : keys.apply(mediaType)) {
        index.putIfAbsent(key, mediaType);
      }
    }
    return Collections.unmodifiableMap(index);
  }

  private static String normalize(String value) {
    return value.trim().toLowerCase(Locale.ROOT);
  }
}
