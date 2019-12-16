package by.arhor.core.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import javax.annotation.Nonnull;

import by.arhor.core.function.BooleanConsumer;
import by.arhor.core.function.ByteConsumer;
import by.arhor.core.function.CharConsumer;
import by.arhor.core.function.ShortConsumer;

public final class CoreUtils {

  private CoreUtils() {}

  @Nonnull
  public static
  <T, R> Function<T, R> memorize(@Nonnull final Function<T, R> source) {
    final var ctx = new ConcurrentHashMap<T, R>();
    return (arg) -> ctx.computeIfAbsent(arg, source);
  }


  @Nonnull
  @SafeVarargs
  public static
  <T> Consumer<Consumer<T>> forEach(@Nonnull final T... items) {
    return (action) -> {
      for (T item : items) {
        action.accept(item);
      }
    };
  }

  @Nonnull
  @SafeVarargs
  public static
  <T> Consumer<Consumer<T>> forEach(@Nonnull final T[]... items) {
    return (action) -> {
      for (T[] array : items) {
        for (T item : array) {
          action.accept(item);
        }
      }
    };
  }

  @Nonnull
  @SafeVarargs
  public static
  <T, I extends Iterable<T>> Consumer<Consumer<T>> forEach(@Nonnull final I... items) {
    return (action) -> {
      for (I item : items) {
        item.forEach(action);
      }
    };
  }

  /* Specialized forEach for arrays of primitives */

  @Nonnull
  public static
  Consumer<ByteConsumer> forEach(@Nonnull final byte[]... items) {
    return (action) -> {
      for (byte[] array : items) {
        for (byte item : array) {
          action.accept(item);
        }
      }
    };
  }

  @Nonnull
  public static
  Consumer<ShortConsumer> forEach(@Nonnull final short[]... items) {
    return (action) -> {
      for (short[] array : items) {
        for (short item : array) {
          action.accept(item);
        }
      }
    };
  }

  @Nonnull
  public static
  Consumer<IntConsumer> forEach(@Nonnull final int[]... items) {
    return (action) -> {
      for (int[] array : items) {
        for (int item : array) {
          action.accept(item);
        }
      }
    };
  }

  @Nonnull
  public static
  Consumer<LongConsumer> forEach(@Nonnull final long[]... items) {
    return (action) -> {
      for (long[] array : items) {
        for (long item : array) {
          action.accept(item);
        }
      }
    };
  }

  @Nonnull
  public static
  Consumer<CharConsumer> forEach(@Nonnull final char[]... items) {
    return (action) -> {
      for (char[] array : items) {
        for (char item : array) {
          action.accept(item);
        }
      }
    };
  }

  @Nonnull
  public static
  Consumer<BooleanConsumer> forEach(@Nonnull final boolean[]... items) {
    return (action) -> {
      for (boolean[] array : items) {
        for (boolean item : array) {
          action.accept(item);
        }
      }
    };
  }

}
