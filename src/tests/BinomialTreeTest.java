package tests;

import binomial_heap.BinomialTree;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinomialTreeTest {

  @Test
  public void treeCreation() {
    BinomialTree<Integer> tree = new BinomialTree<>(1);
    assertEquals(1, tree.getValue().intValue());
    assertEquals(0, tree.getDegree());
    assertEquals(1, tree.getSize());
  }

  @Test
  public void merge() {
    BinomialTree<Integer> tree = new BinomialTree<>(1);
    tree = tree.merge(new BinomialTree<>(2));
    assertEquals(1, tree.getDegree());
    assertEquals(2, tree.getSize());
    assertEquals(2, tree.getValue().intValue());
    BinomialTree<Integer> tree2 = new BinomialTree<>(3);
    tree2 = tree2.merge(new BinomialTree<>(4));
    tree = tree.merge(tree2);
    assertEquals(2, tree.getDegree());
    assertEquals(4, tree.getSize());
    assertEquals(4, tree.getValue().intValue());

    BinomialTree<Integer> tree3 = new BinomialTree<>(14);
    tree3 = tree3.merge(new BinomialTree<>(13));
    assertEquals(14, tree3.getValue().intValue());
    BinomialTree<Integer> tree4 = new BinomialTree<>(12);
    tree4 = tree4.merge(new BinomialTree<>(11));
    assertEquals(12, tree4.getValue().intValue());
    tree3 = tree3.merge(tree4);
    assertEquals(14, tree3.getValue().intValue());

    tree = tree.merge(tree3);
    assertEquals(3, tree.getDegree());
    assertEquals(8, tree.getSize());
    assertEquals(14, tree.getValue().intValue());
  }
}
