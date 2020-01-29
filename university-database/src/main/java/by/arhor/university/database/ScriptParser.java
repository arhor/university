package by.arhor.university.database;

import java.util.regex.Pattern;

import by.arhor.core.Lazy;

public final class ScriptParser {

  private static final Lazy<Pattern> COMMAND_MAIN = Lazy.evalSafe(() ->
      Pattern.compile(""
          + "^"                         // new line start
          + "(--)"                      // sql comment started
          + "( ?)"                      // possible whitespace character
          + "(#)"                       // directive flag
          + "(main)"                    // directive name
          + "$"                         // line end
      )
  );

  private static final Lazy<Pattern> COMMAND_CREATE = Lazy.evalSafe(() ->
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
}
