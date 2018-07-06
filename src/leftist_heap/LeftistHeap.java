package leftist_heap;

import heap.IHeap;
import heap.PriorityWrapper;

public class LeftistHeap<S> implements IHeap<LeftistHeap<S>, S> {

  private LeftistTree<PriorityWrapper<S>> root;
  private int size = 0;

  @Override
  public void insertar(S x, int p) {
    LeftistTree<PriorityWrapper<S>> tree = new LeftistTree<>(new PriorityWrapper<>(x, p));
    root = tree.merge(tree, root);
    size++;
  }

  @Override
  public S extraer_siguiente() {
    S ans = root.getValue().getValue();
    LeftistTree<PriorityWrapper<S>> left = root.getLeftChild();
    LeftistTree<PriorityWrapper<S>> right = root.getRightChild();
    if (left == null) {
      root = right;
    } else {
      root = left.merge(left, right);
    }
    size--;
    return ans;
  }

  @Override
  public LeftistHeap<S> meld(LeftistHeap<S> c) {
    LeftistHeap<S> ans = new LeftistHeap<>();
    ans.root = root;
    ans.size = size + c.size;
    ans.root = ans.root.merge(root, c.root);
    return ans;
  }

  @Override
  public S getTop() {
    return root.getValue().getValue();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public String getName() {
    return "Leftist Heap";
  }
}
