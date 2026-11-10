package org.iglooproject.commons.util.mime;

import java.io.Serializable;
import java.util.List;

public interface IMediaType extends Serializable {

  MediaTypeDefinition definition();

  default String mime() {
    return definition().mime();
  }

  default List<String> mimes() {
    return definition().mimes();
  }

  default String extension() {
    return definition().extension();
  }

  default List<String> extensions() {
    return definition().extensions();
  }
}
