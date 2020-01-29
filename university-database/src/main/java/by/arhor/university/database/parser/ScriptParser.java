package by.arhor.university.database.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.Nonnull;

import by.arhor.core.Lazy;

public final class ScriptParser {

  private enum Stage {
    FREE,
    DIRECTIVE_SIMPLE,
    DIRECTIVE_BLOCK_START,
    DIRECTIVE_BLOCK_INSIDE,
    DIRECTIVE_BLOCK_END,
    TEXT,
  }

  private static final Lazy<ScriptParser> INSTANCE = Lazy.evalSafe(ScriptParser::new);

  public static ScriptParser getInstance() {
    return INSTANCE.get();
  }

  private ScriptParser() {}

  private volatile Stage stage = Stage.FREE;

  public Map<String, String> parseFile(@Nonnull final File script) throws FileNotFoundException {
    final var result = new HashMap<String, String>();
    if (script.isFile() && script.canRead()) {
      try (var scan = new Scanner(script)) {

        var statement = new StringBuilder();

        boolean inBlock = false;

        while (scan.hasNext()) {
          final var line = scan.nextLine();

          final var directive = Directive.tryParse(line);

          if (directive != null) {
            inBlock = directive.isBlock() && !inBlock;
            String suffix = inBlock ? " >>> START" : directive.isBlock() ? " <<< END" : "";
            System.out.println(directive + suffix);

            if (inBlock) {
              continue;
            }

            if (!directive.isBlock()) {
              statement.append(line).append('\n');
            }

            if (statement.length() > 0) {
              result.put(directive.name().toLowerCase(), statement.toString());
              statement.delete(0, statement.length());
            }
          }
          if (inBlock) {
            statement.append(line).append('\n');
          }
        }
      }
    }
    return result;
  }

}
