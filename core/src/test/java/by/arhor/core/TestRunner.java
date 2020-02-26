package by.arhor.core;


import org.junit.jupiter.api.Test;

import by.arhor.core.pattern.visitor.Visitable;
import by.arhor.core.pattern.visitor.Visitor;

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

  private static abstract class AbstractVisitor<T> implements Visitor<AbstractVisitable<T>> {
    public abstract void visit(AbstractVisitable<T> item);
  }

  @Test
  public void testRun() {
    Visitable<Integer> integerVisitable = new Visitable<>() {
      @Override
      public void accept(Visitor<Visitable<Integer>> visitor) {
        visitor.visit(this);
      }
    };

    Visitor<Visitable<Integer>> integerVisitor = new Visitor<Visitable<Integer>>() {
      @Override
      public void visit(Visitable<Integer> item) {
        System.out.println(item);
      }
    };

    integerVisitable.accept(integerVisitor);
  }
}
