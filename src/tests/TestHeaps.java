package tests;

import binomial_heap.BinomialHeap;
import fibonacci_heap.FibonacciHeap;
import heap.IHeap;
import heap_clasico.HeapClasico;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestHeaps {

  private final int initialSize = 10;
  private List<IHeap<?, Integer>> heaps;
  private List<IHeap<?, Integer>> heaps2;
  private List<Integer> list;

  private void register(IHeap<?, Integer> heap1, IHeap<?, Integer> heap2) {
    heaps.add(heap1);
    heaps2.add(heap2);
  }

  @Before
  public void setUp() {
    heaps = new ArrayList<>();
    heaps2 = new ArrayList<>();
    list = new ArrayList<>();
    register(new HeapClasico<>(), new HeapClasico<>());
    register(new BinomialHeap<>(), new BinomialHeap<>());
    register(new FibonacciHeap<>(), new FibonacciHeap<>());
    for (IHeap<?, Integer> heap : heaps) {
      for (int i = 0; i < initialSize; ++i) {
        heap.insertar(i, i);
      }
    }
    for (IHeap<?, Integer> heap : heaps2) {
      for (int i = initialSize; i < initialSize + 20; ++i) {
        heap.insertar(i, i);
      }
    }
    for (int i = 0; i < initialSize; ++i) {
      list.add(i);
    }
    for (int i = initialSize; i < initialSize + 20; ++i) {
      list.add(i);
    }
    list.sort(Collections.reverseOrder());
  }

  @Test
  public void insertar() {
    for (IHeap<?, Integer> heap : heaps) {
      assertEquals(initialSize, heap.size());
      assertEquals(initialSize - 1, heap.getTop().intValue());
      heap.insertar(initialSize + 1, initialSize + 1);
      assertEquals(initialSize + 1, heap.getTop().intValue());
      heap.insertar(initialSize, initialSize);
      assertEquals(initialSize + 1, heap.getTop().intValue());
    }
  }

  @Test
  public void extraer_siguiente() {
    for (IHeap<?, Integer> heap : heaps) {
      for (int i = initialSize - 1; i >= 0; --i) {
        assertEquals(i, heap.getTop().intValue());
        assertEquals(i, heap.extraer_siguiente().intValue());
        assertEquals(i, heap.size());
      }
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void meld() {
    Iterator<IHeap<?, Integer>> iter = heaps2.iterator();
    for (IHeap heap : heaps) {
      IHeap heap2 = iter.next();
      IHeap merged = heap.meld(heap2);
      assertEquals(heap.size() + heap2.size(), merged.size());
      for (Integer aList : list) {
        assertEquals(aList, merged.extraer_siguiente());
      }
    }
  }
}
