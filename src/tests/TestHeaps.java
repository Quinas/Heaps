package tests;

import binomial_heap.BinomialHeap;
import heap.IHeap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestHeaps {

    final int initialSize = 10;
    List<IHeap<?, Integer>> heaps;

    @Before
    public void setUp() {
        heaps = new ArrayList<>();

        heaps.add(new BinomialHeap<>());
        for (IHeap<?, Integer> heap : heaps) {
            for (int i = 0; i < initialSize; ++i) {
                heap.insertar(i, i);
            }
        }
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
}