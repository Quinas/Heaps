package tests;

import fibonacci_heap.FibonacciTree;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FibonacciTreeTest {

  @Test
  public void union() {
    FibonacciTree<Integer> tree1 = new FibonacciTree<>(1);
    FibonacciTree<Integer> tree2 = new FibonacciTree<>(2);
    tree1.union(new FibonacciTree<>(3));
    tree2.union(new FibonacciTree<>(4));
    assertEquals(3, tree1.getRight().getValue().intValue());
    assertEquals(tree1.getRight(), tree1.getLeft());
    tree1.union(tree2);
    FibonacciTree<Integer> cur = tree1.getRight();
    int total = 1;
    while (cur != tree1) {
      total += cur.getValue();
      cur = cur.getRight();
    }
    assertEquals(10, total);
  }

  @Test
  public void merge() {
    FibonacciTree<Integer> tree = new FibonacciTree<>(1);
    tree = tree.merge(new FibonacciTree<>(2));
    assertEquals(1, tree.getDegree());
    assertEquals(2, tree.getSize());
    assertEquals(2, tree.getValue().intValue());
    FibonacciTree<Integer> tree2 = new FibonacciTree<>(3);
    tree2 = tree2.merge(new FibonacciTree<>(4));
    tree = tree.merge(tree2);
    assertEquals(2, tree.getDegree());
    assertEquals(4, tree.getSize());
    assertEquals(4, tree.getValue().intValue());

    FibonacciTree<Integer> tree3 = new FibonacciTree<>(14);
    tree3 = tree3.merge(new FibonacciTree<>(13));
    assertEquals(14, tree3.getValue().intValue());
    FibonacciTree<Integer> tree4 = new FibonacciTree<>(12);
    tree4 = tree4.merge(new FibonacciTree<>(11));
    assertEquals(12, tree4.getValue().intValue());
    tree3 = tree3.merge(tree4);
    assertEquals(14, tree3.getValue().intValue());

    tree = tree.merge(tree3);
    assertEquals(3, tree.getDegree());
    assertEquals(8, tree.getSize());
    assertEquals(14, tree.getValue().intValue());
  }
}
