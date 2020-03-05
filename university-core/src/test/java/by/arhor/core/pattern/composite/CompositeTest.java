package by.arhor.core.pattern.composite;

import static by.arhor.core.pattern.composite.Node.node;
import static by.arhor.core.pattern.composite.Leaf.leaf;

import org.junit.jupiter.api.Test;

public class CompositeTest {

  @Test
  public void test() {
    Composite<Integer> tree =
        node(
            node(
                leaf(3),
                node(
                    node(),
                    leaf(6),
                    node(
                        leaf(8),
                        leaf(9),
                        leaf(10)
                    ),
                    null,
                    leaf(7)
                ),
                leaf(4)
            ),
            leaf(1),
            leaf(2)
        );

    tree.execute(System.out::println);
  }
}
