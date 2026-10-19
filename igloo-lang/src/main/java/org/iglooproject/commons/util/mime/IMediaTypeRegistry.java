package org.iglooproject.commons.util.mime;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IMediaTypeRegistry {

  List<IMediaType> values();

  IMediaType fromExtension(String extension);

  IMediaType fromMime(String mime);

  IMediaType fromFilename(String filename);

  IMediaType canonical(IMediaType mediaType);

  boolean accepts(Collection<? extends IMediaType> allowed, IMediaType mediaType);

  Map<IMediaType, IMediaType> overrides();
}
