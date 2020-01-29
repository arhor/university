package by.arhor.university.database;

import java.util.regex.Pattern;

import by.arhor.core.Lazy;

public final class ScriptParser {

  private static final Lazy<Pattern> DIRECTIVE_MAIN = Lazy.evalSafe(() ->
      Pattern.compile(""
          + "^"                         // new line start
          + "(--)"                      // sql comment started
          + "( ?)"                      // possible whitespace character
          + "(#)"                       // directive flag
          + "(main)"                    // directive name
          + "$"                         // line end
      )
  );

  private static final Lazy<Pattern> DIRECTIVE_DEPENDENCIES = Lazy.evalSafe(() ->
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
  );

  private static final Lazy<Pattern> DIRECTIVE_CREATE = Lazy.evalSafe(() ->
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
  );

  private static final Lazy<Pattern> DIRECTIVE_INIT = Lazy.evalSafe(() ->
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
  );
}
