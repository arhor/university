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
  ),

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
  ),

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
  ),

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
  );

  private final Lazy<Pattern> pattern;

  Directive(@Nonnull final Lazy<Pattern> pattern) {
    this.pattern = pattern;
  }

  public boolean matches(String input) {
    return pattern
        .get()
        .matcher(input)
        .matches();
  }

}
