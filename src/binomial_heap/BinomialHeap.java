package binomial_heap;

import heap.IHeap;
import heap.PriorityWrapper;

import java.util.ArrayList;
import java.util.List;

public class BinomialHeap<S> implements IHeap<BinomialHeap<S>, S> {

  private List<BinomialTree<PriorityWrapper<S>>> forest;
  private int size;

  public BinomialHeap() {
    forest = new ArrayList<>();
    for (int i = 0; i < 60; ++i) {
      forest.add(null);
    }
    size = 0;
  }

  private BinomialHeap<S> merge(BinomialHeap<S> c, boolean inPlace) {
    BinomialHeap<S> ans = new BinomialHeap<>();
    for (int i = 0; i < forest.size() - 1; ++i) {
      BinomialTree<PriorityWrapper<S>> tree = null;
      BinomialTree<PriorityWrapper<S>> carry = null;
      if (forest.get(i) == null) {
        tree = c.forest.get(i);
      } else if (c.forest.get(i) == null) {
        tree = forest.get(i);
      } else {
        carry = forest.get(i).merge(c.forest.get(i));
      }
      if (ans.forest.get(i) != null) {
        if (tree != null) {
          carry = ans.forest.get(i).merge(tree);
          tree = null;
        } else {
          tree = ans.forest.get(i);
        }
      }
      ans.forest.set(i, tree);
      ans.forest.set(i + 1, carry);
    }
    ans.size = size + c.size();
    if (inPlace) {
      forest = ans.forest;
      size = ans.size();
      return this;
    } else {
      return ans;
    }
  }

  private int getMaxInd() {
    int ind = -1;
    for (int i = 0; i < forest.size(); ++i) {
      if (forest.get(i) != null) {
        ind = i;
        break;
      }
    }
    for (int i = 0; i < forest.size(); ++i) {
      if (forest.get(i) == null) {
        continue;
      }
      if (forest.get(i).getValue().compareTo(forest.get(ind).getValue()) > 0) {
        ind = i;
      }
    }
    return ind;
  }

  @Override
  public void insertar(S x, int p) {
    BinomialHeap<S> heap2 = new BinomialHeap<>();
    heap2.forest.set(0, new BinomialTree<>(new PriorityWrapper<>(x, p)));
    heap2.size++;
    merge(heap2, true);
  }

  @Override
  public S extraer_siguiente() {
    int ind = getMaxInd();
    BinomialTree<PriorityWrapper<S>> tree = forest.get(ind);
    S ans = tree.getValue().getValue();
    BinomialHeap<S> heap2 = new BinomialHeap<>();
    for (BinomialTree<PriorityWrapper<S>> cur : tree.getChildren()) {
      heap2.forest.set(cur.getDegree(), cur);
      heap2.size += cur.getSize();
    }
    size -= tree.getSize();
    forest.set(ind, null);
    merge(heap2, true);
    return ans;
  }

  @Override
  public BinomialHeap<S> meld(BinomialHeap<S> c) {
    return merge(c, false);
  }

  @Override
  public S getTop() {
    return forest.get(getMaxInd()).getValue().getValue();
  }

  @Override
  public int size() {
    return size;
  }
}
