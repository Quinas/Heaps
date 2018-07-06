package fibonacci_heap;

import heap.IHeap;
import heap.PriorityWrapper;

import java.util.ArrayList;
import java.util.List;

public class FibonacciHeap<S> implements IHeap<FibonacciHeap<S>, S> {

  private FibonacciTree<PriorityWrapper<S>> maxTree;
  private int size = 0;

  private void mergeTrees(
      List<FibonacciTree<PriorityWrapper<S>>> trees,
      FibonacciTree<PriorityWrapper<S>> tree1,
      FibonacciTree<PriorityWrapper<S>> tree2) {
    if (tree1 == null) {
      trees.set(tree2.getDegree(), tree2);
    } else if (tree2 == null) {
      trees.set(tree1.getDegree(), tree1);
    } else {
      int d = tree1.getDegree();
      trees.set(d, null);
      mergeTrees(trees, tree1.merge(tree2), trees.get(d + 1));
    }
  }

  private void consolidate(
      List<FibonacciTree<PriorityWrapper<S>>> trees, FibonacciTree<PriorityWrapper<S>> root) {
    FibonacciTree<PriorityWrapper<S>> cur = root;
    List<FibonacciTree<PriorityWrapper<S>>> list = new ArrayList<>();
    list.add(root);
    cur = cur.getRight();
    while (cur != root) {
      list.add(cur);
      cur = cur.getRight();
    }
    for (FibonacciTree<PriorityWrapper<S>> tree : list) {
      tree.setLeft(tree);
      tree.setRight(tree);
    }
    for (FibonacciTree<PriorityWrapper<S>> tree : list) {
      mergeTrees(trees, tree, trees.get(tree.getDegree()));
    }
  }

  private FibonacciTree<PriorityWrapper<S>> linkList(List<FibonacciTree<PriorityWrapper<S>>> list) {
    FibonacciTree<PriorityWrapper<S>> ini = null;
    FibonacciTree<PriorityWrapper<S>> last = null;
    FibonacciTree<PriorityWrapper<S>> max = null;
    for (FibonacciTree<PriorityWrapper<S>> tree : list) {
      if (tree == null) {
        continue;
      }
      if (last == null) {
        ini = tree;
        max = tree;
        last = tree;
      } else {
        last.setRight(tree);
        tree.setLeft(last);
        max = tree.getValue().compareTo(max.getValue()) > 0 ? tree : max;
        last = tree;
      }
    }
    if (ini != null) {
      ini.setLeft(last);
      last.setRight(ini);
    }
    return max;
  }

  @Override
  public void insertar(S x, int p) {
    if (maxTree == null) {
      maxTree = new FibonacciTree<>(new PriorityWrapper<>(x, p));
    } else {
      FibonacciHeap<S> heap = new FibonacciHeap<>();
      heap.insertar(x, p);
      meld(heap);
      maxTree = meld(heap).maxTree;
    }
    size++;
  }

  @Override
  public S extraer_siguiente() {
    S ans = maxTree.getValue().getValue();
    if (size == 1) {
      maxTree = null;
    } else {
      FibonacciTree<PriorityWrapper<S>> l = maxTree.getLeft();
      FibonacciTree<PriorityWrapper<S>> r = maxTree.getRight();
      FibonacciTree<PriorityWrapper<S>> child = maxTree.getChild();
      if (l != maxTree) {
        r.setLeft(l);
        l.setRight(r);

        if (child != null) {
          l.union(child);
        }
      } else {
        l = child;
      }

      ArrayList<FibonacciTree<PriorityWrapper<S>>> treesDegree = new ArrayList<>();
      for (int i = 0; i < 60; ++i) {
        treesDegree.add(null);
      }
      consolidate(treesDegree, l);
      maxTree = linkList(treesDegree);
    }
    size--;
    return ans;
  }

  @Override
  public FibonacciHeap<S> meld(FibonacciHeap<S> c) {
    FibonacciHeap<S> ans = new FibonacciHeap<>();
    ans.maxTree = maxTree;
    ans.size = size + c.size;
    ans.maxTree.union(c.maxTree);
    if (c.maxTree.getValue().compareTo(ans.maxTree.getValue()) > 0) {
      ans.maxTree = c.maxTree;
    }
    return ans;
  }

  @Override
  public S getTop() {
    return maxTree.getValue().getValue();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public String getName() {
    return "Fibonacci Heap";
  }
}
