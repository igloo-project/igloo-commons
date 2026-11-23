package org.iglooproject.functional;

import com.google.common.base.Splitter;

public final class Splitters {

  private static final Splitter SPACE = Splitter.on(" ").trimResults().omitEmptyStrings();
  private static final Splitter NON_BREAKING_SPACE =
      Splitter.on("\u00A0").trimResults().omitEmptyStrings();
  private static final Splitter NEW_LINE = Splitter.on("\n").trimResults().omitEmptyStrings();
  private static final Splitter NEW_LINE_SEMICOLON =
      Splitter.on(" ;\n").trimResults().omitEmptyStrings();
  private static final Splitter HYPHEN_SPACE = Splitter.on(" - ").trimResults().omitEmptyStrings();
  private static final Splitter MIDDOT_SPACE = Splitter.on(" · ").trimResults().omitEmptyStrings();
  private static final Splitter DOT = Splitter.on(".").trimResults().omitEmptyStrings();
  private static final Splitter SLASH = Splitter.on("/").trimResults().omitEmptyStrings();
  private static final Splitter COMMA = Splitter.on(",").trimResults().omitEmptyStrings();

  public static Splitter onSpace() {
    return SPACE;
  }

  public static Splitter onNonBreakingSpace() {
    return NON_BREAKING_SPACE;
  }

  public static Splitter onNewLine() {
    return NEW_LINE;
  }

  public static Splitter onNewLineSemicolon() {
    return NEW_LINE_SEMICOLON;
  }

  public static Splitter onHyphenSpace() {
    return HYPHEN_SPACE;
  }

  public static Splitter onMiddotSpace() {
    return MIDDOT_SPACE;
  }

  public static Splitter onDot() {
    return DOT;
  }

  public static Splitter onSlash() {
    return SLASH;
  }

  public static Splitter onComma() {
    return COMMA;
  }

  public static final class Functions {

    private static final SerializableFunction2<Object, Splitter> SPACE = _ -> Splitters.SPACE;
    private static final SerializableFunction2<Object, Splitter> NON_BREAKING_SPACE =
        _ -> Splitters.NON_BREAKING_SPACE;
    private static final SerializableFunction2<Object, Splitter> NEW_LINE = _ -> Splitters.NEW_LINE;
    private static final SerializableFunction2<Object, Splitter> NEW_LINE_SEMICOLON =
        _ -> Splitters.NEW_LINE_SEMICOLON;
    private static final SerializableFunction2<Object, Splitter> HYPHEN_SPACE =
        _ -> Splitters.HYPHEN_SPACE;
    private static final SerializableFunction2<Object, Splitter> MIDDOT_SPACE =
        _ -> Splitters.MIDDOT_SPACE;
    private static final SerializableFunction2<Object, Splitter> DOT = _ -> Splitters.DOT;
    private static final SerializableFunction2<Object, Splitter> SLASH = _ -> Splitters.SLASH;
    private static final SerializableFunction2<Object, Splitter> COMMA = _ -> Splitters.COMMA;

    public static SerializableFunction2<Object, Splitter> onSpace() {
      return SPACE;
    }

    public static SerializableFunction2<Object, Splitter> onNonBreakingSpace() {
      return NON_BREAKING_SPACE;
    }

    public static SerializableFunction2<Object, Splitter> onNewLine() {
      return NEW_LINE;
    }

    public static SerializableFunction2<Object, Splitter> onNewLineSemicolon() {
      return NEW_LINE_SEMICOLON;
    }

    public static SerializableFunction2<Object, Splitter> onHyphenSpace() {
      return HYPHEN_SPACE;
    }

    public static SerializableFunction2<Object, Splitter> onMiddotSpace() {
      return MIDDOT_SPACE;
    }

    public static SerializableFunction2<Object, Splitter> onDot() {
      return DOT;
    }

    public static SerializableFunction2<Object, Splitter> onSlash() {
      return SLASH;
    }

    public static SerializableFunction2<Object, Splitter> onComma() {
      return COMMA;
    }

    private Functions() {}
  }

  private Splitters() {}
}
