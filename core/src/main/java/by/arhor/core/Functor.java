package by.arhor.core;

import java.util.function.Function;

public interface Functor<A, B> {

  Function<Function<A, A>, Function<B, B>> flatMap(Function<A, B> f);

}
