package by.arhor.university.database.parser;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import by.arhor.core.Lazy;

public enum Directive {

  MAIN (
      Lazy.evalSafe(() ->
          Pattern.compile(""
              + "^"                         // new line start
              + "(--)"                      // sql comment started
              + "( ?)"                      // possible whitespace character
              + "(#)"                       // directive flag
              + "(main)"                    // directive name
              + "$"                         // line end
          )
      )
  ) {
    @Override public boolean isBlock() { return false; }
  },

  DEPENDENCIES (
      Lazy.evalSafe(() ->
          Pattern.compile(""
              + "^"                          // new line start
              + "(--)"                       // sql comment started
              + "( ?)"                       // possible whitespace character
              + "(#)"                        // directive flag
              + "(dependencies)"             // directive name
              + "(:)"                        // arguments separator
              + "( ?)"                       // possible whitespace character
              + "(\\[)"                      // dependencies declaration start
              + "(( ?)(\\w+)( ?))"           // at least one dependency
              + "(,( ?)(\\w+)( ?))*"         // any number of other dependencies
              + "(])"                        // dependencies declaration start
              + "$"                          // line end
          )
      )
  ) {
    @Override public boolean isBlock() { return false; }
  },

  CREATE (
      Lazy.evalSafe(() ->
          Pattern.compile(""
              + "^"                          // new line start
              + "(--)"                       // sql comment started
              + "( ?)"                       // possible whitespace character
              + "(#)"                        // directive flag
              + "(create)"                   // directive name
              + "(-)"                        // directive separator
              + "(database|table|procedure)" // database entity type
              + "(:)"                        // arguments separator
              + "( ?)"                       // possible whitespace character
              + "(\\w+)"                     // database entity name
              + "( ?)"                       // possible whitespace character
              + "("                          //
              + "(>>>( ?)START)"             // directive block start
              + "|"                          // or
              + "(<<<( ?)END)"               // directive block end
              + ")"                          //
              + "$"                          // line end
          )
      )
  ) {
    @Override public boolean isBlock() { return true; }
  },

  INIT (
      Lazy.evalSafe(() ->
          Pattern.compile(""
              + "^"                          // new line start
              + "(--)"                       // sql comment started
              + "( ?)"                       // possible whitespace character
              + "(#)"                        // directive flag
              + "(init)"                     // directive name
              + "(-)"                        // directive separator
              + "(table)"                    // database entity type
              + "(:)"                        // arguments separator
              + "( ?)"                       // possible whitespace character
              + "(\\w+)"                     // database entity name
              + "( ?)"                       // possible whitespace character
              + "("                          //
              + "(>>>( ?)START)"             // directive block start
              + "|"                          // or
              + "(<<<( ?)END)"               // directive block end
              + ")"                          //
              + "$"                          // line end
          )
      )
  ) {
    @Override public boolean isBlock() { return true; }
  };

  private final Lazy<Pattern> pattern;

  Directive(@Nonnull final Lazy<Pattern> pattern) { this.pattern = pattern; }

  public static Directive tryParse(String line) {
    if (MAIN.matches(line))              { return MAIN; }
    else if (DEPENDENCIES.matches(line)) { return DEPENDENCIES; }
    else if (CREATE.matches(line))       { return CREATE; }
    else if (INIT.matches(line))         { return INIT; }
    else                                 { return null; }
  }

  public boolean matches(@Nonnull final String input) {
    return pattern.get().matcher(input).matches();
  }

  abstract public boolean isBlock();

}
