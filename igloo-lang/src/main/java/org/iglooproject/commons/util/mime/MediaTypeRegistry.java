package org.iglooproject.commons.util.mime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public final class MediaTypeRegistry implements IMediaTypeRegistry {

  private final List<IMediaType> values;

  private final Map<String, IMediaType> byMime;

  private final Map<String, IMediaType> byExtension;

  private final Map<IMediaType, IMediaType> overrides;

  private MediaTypeRegistry(
      Collection<? extends IMediaType> base, Collection<? extends IMediaType> additions) {
    Map<String, IMediaType> baseMimes = index(base, IMediaType::mimes);
    Map<String, IMediaType> baseExtensions = index(base, IMediaType::extensions);
    index(additions, IMediaType::mimes);
    index(additions, IMediaType::extensions);

    Map<IMediaType, IMediaType> overridden = new IdentityHashMap<>();
    for (IMediaType addition : additions) {
      IMediaType target = target(addition, baseMimes, baseExtensions);
      if (target != null) {
        IMediaType previous = overridden.put(target, addition);
        if (previous != null) {
          throw new IllegalStateException(
              "%s and %s both override %s".formatted(previous, addition, target));
        }
      }
    }

    List<IMediaType> registered = new ArrayList<>();
    for (IMediaType mediaType : base) {
      registered.add(overridden.getOrDefault(mediaType, mediaType));
    }
    for (IMediaType addition : additions) {
      if (!overridden.containsValue(addition)) {
        registered.add(addition);
      }
    }

    this.values = Collections.unmodifiableList(registered);
    this.byMime = index(registered, IMediaType::mimes);
    this.byExtension = index(registered, IMediaType::extensions);
    this.overrides = Collections.unmodifiableMap(new LinkedHashMap<>(overridden));
  }

  public static MediaTypeRegistry of(Collection<? extends IMediaType> base) {
    return new MediaTypeRegistry(base, List.of());
  }

  public static MediaTypeRegistry of(
      Collection<? extends IMediaType> base, Collection<? extends IMediaType> additions) {
    return new MediaTypeRegistry(base, additions);
  }

  @SafeVarargs
  public static MediaTypeRegistry of(
      Class<? extends IMediaType> base, Class<? extends IMediaType>... additions) {
    List<IMediaType> values = new ArrayList<>();
    for (Class<? extends IMediaType> addition : additions) {
      values.addAll(constants(addition));
    }
    return new MediaTypeRegistry(constants(base), values);
  }

  @Override
  public List<IMediaType> values() {
    return values;
  }

  @Override
  public IMediaType fromExtension(String extension) {
    if (extension == null) {
      return null;
    }
    String value = normalize(extension);
    return byExtension.get(value.startsWith(".") ? value.substring(1) : value);
  }

  @Override
  public IMediaType fromMime(String mime) {
    if (mime == null) {
      return null;
    }
    int parameterStart = mime.indexOf(';');
    return byMime.get(normalize(parameterStart == -1 ? mime : mime.substring(0, parameterStart)));
  }

  @Override
  public IMediaType fromFilename(String filename) {
    if (filename == null) {
      return null;
    }
    int separator = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
    int dot = filename.lastIndexOf('.');
    if (dot <= separator || dot == filename.length() - 1) {
      return null;
    }
    return fromExtension(filename.substring(dot + 1));
  }

  @Override
  public IMediaType canonical(IMediaType mediaType) {
    return overrides.getOrDefault(Objects.requireNonNull(mediaType), mediaType);
  }

  @Override
  public boolean accepts(Collection<? extends IMediaType> allowed, IMediaType mediaType) {
    if (mediaType == null) {
      return false;
    }
    IMediaType candidate = canonical(mediaType);
    return allowed.stream().anyMatch(allowedMediaType -> canonical(allowedMediaType) == candidate);
  }

  @Override
  public Map<IMediaType, IMediaType> overrides() {
    return overrides;
  }

  private static IMediaType target(
      IMediaType addition,
      Map<String, IMediaType> baseMimes,
      Map<String, IMediaType> baseExtensions) {
    Set<IMediaType> mimeOwners = owners(addition.mimes(), baseMimes);
    Set<IMediaType> owners = new LinkedHashSet<>(mimeOwners);
    owners.addAll(owners(addition.extensions(), baseExtensions));
    if (owners.isEmpty()) {
      return null;
    }
    if (owners.size() > 1 || mimeOwners.isEmpty()) {
      throw new IllegalStateException(
          "%s conflicts with %s: an override must share a MIME type with a single media type"
              .formatted(addition, owners));
    }
    return owners.iterator().next();
  }

  private static Set<IMediaType> owners(List<String> keys, Map<String, IMediaType> index) {
    Set<IMediaType> owners = Collections.newSetFromMap(new IdentityHashMap<>());
    for (String key : keys) {
      IMediaType owner = index.get(key);
      if (owner != null) {
        owners.add(owner);
      }
    }
    return owners;
  }

  private static Map<String, IMediaType> index(
      Collection<? extends IMediaType> mediaTypes, Function<IMediaType, List<String>> keys) {
    Map<String, IMediaType> index = new HashMap<>();
    for (IMediaType mediaType : mediaTypes) {
      for (String key : keys.apply(mediaType)) {
        IMediaType previous = index.putIfAbsent(key, mediaType);
        if (previous != null && previous != mediaType) {
          throw new IllegalStateException(
              "%s is declared by both %s and %s".formatted(key, previous, mediaType));
        }
      }
    }
    return Collections.unmodifiableMap(index);
  }

  private static List<IMediaType> constants(Class<? extends IMediaType> type) {
    IMediaType[] constants = type.getEnumConstants();
    if (constants == null) {
      throw new IllegalArgumentException("%s is not an enum".formatted(type.getName()));
    }
    return Arrays.asList(constants);
  }

  private static String normalize(String value) {
    return value.trim().toLowerCase(Locale.ROOT);
  }
}
