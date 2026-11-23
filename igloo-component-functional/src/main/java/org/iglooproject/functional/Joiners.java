package org.iglooproject.functional;

import com.google.common.base.Joiner;
import java.util.Locale;

public final class Joiners {

  private static final Joiner SPACE = Joiner.on(" ").skipNulls();
  private static final Joiner NON_BREAKING_SPACE = Joiner.on("\u00A0").skipNulls();
  private static final Joiner NEW_LINE = Joiner.on("\n").skipNulls();
  private static final Joiner NEW_LINE_SEMICOLON = Joiner.on(" ;\n").skipNulls();
  private static final Joiner HYPHEN = Joiner.on("-").skipNulls();
  private static final Joiner HYPHEN_SPACE = Joiner.on(" - ").skipNulls();
  private static final Joiner MIDDOT_SPACE = Joiner.on(" · ").skipNulls();
  private static final Joiner DOT = Joiner.on(".").skipNulls();
  private static final Joiner SLASH = Joiner.on("/").skipNulls();
  private static final Joiner SLASH_SPACE = Joiner.on(" / ").skipNulls();
  private static final Joiner COMMA = Joiner.on(", ").skipNulls();

  public static Joiner onSpace() {
    return SPACE;
  }

  public static Joiner onNonBreakingSpace() {
    return NON_BREAKING_SPACE;
  }

  public static Joiner onNewLine() {
    return NEW_LINE;
  }

  public static Joiner onNewLineSemicolon() {
    return NEW_LINE_SEMICOLON;
  }

  public static Joiner onHyphen() {
    return HYPHEN;
  }

  public static Joiner onHyphenSpace() {
    return HYPHEN_SPACE;
  }

  public static Joiner onMiddotSpace() {
    return MIDDOT_SPACE;
  }

  public static Joiner onDot() {
    return DOT;
  }

  public static Joiner onSlash() {
    return SLASH;
  }

  public static Joiner onSlashSpace() {
    return SLASH_SPACE;
  }

  public static Joiner onComma() {
    return COMMA;
  }

  /**
   * Utility functions for places where we need a serializable reference to a {@link Joiner}.
   *
   * <p>These are functions, and not suppliers, because in most places a <code>
   * Function&lt;? super Locale, ? extends Joiner&gt;</code> is required (which would allow the
   * function to return a different {@link Joiner} depending on the {@link Locale}.
   */
  public static final class Functions {

    private static final SerializableFunction2<Object, Joiner> SPACE = _ -> Joiners.SPACE;
    private static final SerializableFunction2<Object, Joiner> NON_BREAKING_SPACE =
        _ -> Joiners.NON_BREAKING_SPACE;
    private static final SerializableFunction2<Object, Joiner> NEW_LINE = _ -> Joiners.NEW_LINE;
    private static final SerializableFunction2<Object, Joiner> NEW_LINE_SEMICOLON =
        _ -> Joiners.NEW_LINE_SEMICOLON;
    private static final SerializableFunction2<Object, Joiner> HYPHEN = _ -> Joiners.HYPHEN;
    private static final SerializableFunction2<Object, Joiner> HYPHEN_SPACE =
        _ -> Joiners.HYPHEN_SPACE;
    private static final SerializableFunction2<Object, Joiner> MIDDOT_SPACE =
        _ -> Joiners.MIDDOT_SPACE;
    private static final SerializableFunction2<Object, Joiner> DOT = _ -> Joiners.DOT;
    private static final SerializableFunction2<Object, Joiner> SLASH = _ -> Joiners.SLASH;
    private static final SerializableFunction2<Object, Joiner> SLASH_SPACE =
        _ -> Joiners.SLASH_SPACE;
    private static final SerializableFunction2<Object, Joiner> COMMA = _ -> Joiners.COMMA;

    public static SerializableFunction2<Object, Joiner> onSpace() {
      return SPACE;
    }

    public static SerializableFunction2<Object, Joiner> onNonBreakingSpace() {
      return NON_BREAKING_SPACE;
    }

    public static SerializableFunction2<Object, Joiner> onNewLine() {
      return NEW_LINE;
    }

    public static SerializableFunction2<Object, Joiner> onNewLineSemicolon() {
      return NEW_LINE_SEMICOLON;
    }

    public static SerializableFunction2<Object, Joiner> onHyphen() {
      return HYPHEN;
    }

    public static SerializableFunction2<Object, Joiner> onHyphenSpace() {
      return HYPHEN_SPACE;
    }

    public static SerializableFunction2<Object, Joiner> onMiddotSpace() {
      return MIDDOT_SPACE;
    }

    public static SerializableFunction2<Object, Joiner> onDot() {
      return DOT;
    }

    public static SerializableFunction2<Object, Joiner> onSlash() {
      return SLASH;
    }

    public static SerializableFunction2<Object, Joiner> onSlashSpace() {
      return SLASH_SPACE;
    }

    public static SerializableFunction2<Object, Joiner> onComma() {
      return COMMA;
    }

    private Functions() {}
  }

  private Joiners() {}
}
