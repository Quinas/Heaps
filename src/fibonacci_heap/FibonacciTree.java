package fibonacci_heap;

public class FibonacciTree<T extends Comparable<T>> {

  private FibonacciTree<T> left;
  private FibonacciTree<T> right;
  private FibonacciTree<T> child;
  private int degree;
  private int size;
  private T value;

  public FibonacciTree(T value) {
    this.value = value;
    left = this;
    right = this;
    degree = 0;
    size = 1;
  }

  public FibonacciTree<T> getLeft() {
    return left;
  }

  public void setLeft(FibonacciTree<T> left) {
    this.left = left;
  }

  public FibonacciTree<T> getRight() {
    return right;
  }

  public void setRight(FibonacciTree<T> right) {
    this.right = right;
  }

  public FibonacciTree<T> getChild() {
    return child;
  }

  public T getValue() {
    return value;
  }

  public int getDegree() {
    return degree;
  }

  private void addChild(FibonacciTree<T> tree) {
    if (child == null) {
      child = tree;
    } else {
      FibonacciTree<T> r = child.right;
      child.right = tree;
      r.left = tree;
      tree.left = child;
      tree.right = r;
    }
    size += tree.size;
  }

  public void union(FibonacciTree<T> tree) {
    if (tree == null) {
      return;
    }
    FibonacciTree<T> r1 = getRight();
    FibonacciTree<T> l2 = tree.getLeft();
    setRight(tree);
    tree.setLeft(this);
    r1.setLeft(l2);
    l2.setRight(r1);
  }

  public FibonacciTree<T> merge(FibonacciTree<T> tree) {
    if (tree == null) {
      return this;
    } else {
      if (degree != tree.getDegree()) {
        throw new AssertionError();
      }
      if (value.compareTo(tree.getValue()) > 0) {
        addChild(tree);
        degree++;
        return this;
      } else {
        tree.addChild(this);
        tree.degree++;
        return tree;
      }
    }
  }

  public int getSize() {
    return size;
  }

  @Override
  public String toString() {
    String ans = "";
    ans += value + " ";
    FibonacciTree<T> cur = getRight();
    while (cur != this) {
      ans += cur.getValue() + " ";
      cur = cur.getRight();
    }
    return ans;
  }
}
