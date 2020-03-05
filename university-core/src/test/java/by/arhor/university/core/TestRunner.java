package by.arhor.university.core;


import by.arhor.university.core.pattern.visitor.Visitable;
import by.arhor.university.core.pattern.visitor.Visitor;
import org.junit.jupiter.api.Test;

public class TestRunner {

  private static abstract class AbstractVisitable<T> implements Visitable<T> {

    public abstract T getValue();

    @Override
    public void accept(Visitor<Visitable<T>> visitor) {
      visitor.visit(this);
    }
  }

  private static class IntVisitable extends AbstractVisitable<Integer> {
    @Override
    public Integer getValue() {
      return 1;
    }
  }

  private static class LongVisitable extends AbstractVisitable<Long> {
    @Override
    public Long getValue() {
      return 2L;
    }
  }

  @Test
  public void testRun() {
    Visitable<Integer> integerVisitable = new Visitable<>() {
      @Override
      public void accept(Visitor<Visitable<Integer>> visitor) {
        visitor.visit(this);
      }
    };

    Visitor<Visitable<Integer>> integerVisitor = System.out::println;

    integerVisitable.accept(integerVisitor);
  }
}
