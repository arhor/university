package by.arhor.utility.structure;

import java.util.function.Supplier;

import by.arhor.utility.Lazy;

public final class MagicWorld {

  private static final Supplier<MagicWorld> WORLD_INSTANCE = Lazy.eval(MagicWorld::new);

  private MagicWorld() {}

  public static MagicWorld getWorld() {
    return WORLD_INSTANCE.get();
  }

}
