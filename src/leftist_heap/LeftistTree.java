package leftist_heap;

public class LeftistTree<T extends Comparable<T>> {
  private LeftistTree<T> leftChild;
  private LeftistTree<T> rightChild;
  private T value;
  private int rank;

  public LeftistTree(T value) {
    this.value = value;
    rank = 1;
  }

  public LeftistTree<T> merge(LeftistTree<T> tree1, LeftistTree<T> tree2) {
    if (tree1 == null) {
      return tree2;
    } else if (tree2 == null) {
      return tree1;
    } else {
      LeftistTree<T> max = tree1.value.compareTo(tree2.value) > 0 ? tree1 : tree2;
      LeftistTree<T> min = max == tree1 ? tree2 : tree1;

      max.rightChild = merge(max.rightChild, min);

      if (max.leftChild == null) {
        max.leftChild = max.rightChild;
        max.rightChild = null;
      } else {
        if (max.leftChild.rank < max.rightChild.rank) {
          LeftistTree<T> t = max.leftChild;
          max.leftChild = max.rightChild;
          max.rightChild = t;
        }
        max.rank = max.rightChild.rank + 1;
      }
      return max;
    }
  }

  public T getValue() {
    return value;
  }

  public LeftistTree<T> getLeftChild() {
    return leftChild;
  }

  public LeftistTree<T> getRightChild() {
    return rightChild;
  }
}
