package heap_clasico;

import heap.IHeap;
import heap.PriorityWrapper;

import java.util.ArrayList;
import java.util.List;

public class HeapClasico<S> implements IHeap<HeapClasico<S>, S> {

  private List<PriorityWrapper<S>> heap;

  public HeapClasico() {
    heap = new ArrayList<>();
    heap.add(null);
  }

  private void swap(int a, int b) {
    PriorityWrapper<S> t = heap.get(a);
    heap.set(a, heap.get(b));
    heap.set(b, t);
  }

  private void siftUp(int ind) {
    while (ind > 1) {
      if (heap.get(ind).compareTo(heap.get(ind / 2)) > 0) {
        swap(ind, ind / 2);
      }
      ind /= 2;
    }
  }

  private void siftDown(int ind) {
    int n = heap.size() - 1;
    while (2 * ind <= n) {
      int j = 2 * ind;
      if (j + 1 <= n && heap.get(j + 1).compareTo(heap.get(j)) > 0) {
        j++;
      }
      if (heap.get(ind).compareTo(heap.get(j)) > 0) {
        break;
      }
      swap(ind, j);
      ind = j;
    }
  }

  @Override
  public void insertar(S x, int p) {
    heap.add(new PriorityWrapper<>(x, p));
    int n = heap.size() - 1;
    siftUp(n);
  }

  @Override
  public S extraer_siguiente() {
    S ans = heap.get(1).getValue();
    heap.set(1, heap.get(heap.size() - 1));
    heap.remove(heap.size() - 1);
    siftDown(1);
    return ans;
  }

  @Override
  public HeapClasico<S> meld(HeapClasico<S> c) {
    HeapClasico<S> ans = new HeapClasico<>();
    List<PriorityWrapper<S>> list = new ArrayList<>();
    for (int i = 1; i < heap.size(); ++i) {
      list.add(heap.get(i));
    }
    for (int i = 1; i < c.heap.size(); ++i) {
      list.add(c.heap.get(i));
    }
    ans.heapify(list);
    return ans;
  }

  @Override
  public int size() {
    return heap.size() - 1;
  }

  @Override
  public String getName() {
    return "Heap Clásico";
  }

  @Override
  public S getTop() {
    return heap.get(1).getValue();
  }

  @Override
  public void heapify(List<PriorityWrapper<S>> data) {
    heap = new ArrayList<>();
    heap.add(null);
    heap.addAll(data);
    for (int i = heap.size()-1; i > 0; --i) {
      siftDown(i);
    }
  }
}
