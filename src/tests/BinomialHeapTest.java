package tests;

import binomial_heap.BinomialHeap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;

public class BinomialHeapTest {

    private final int initialSize = 10;
    private BinomialHeap<Integer> heap;

    @Before
    public void setUp() {
        heap = new BinomialHeap<>();
        for (int i = 0; i < initialSize; ++i) {
            heap.insertar(i, i);
        }
    }

    @Test
    public void meld() {
        BinomialHeap<Integer> heap2 = new BinomialHeap<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < initialSize; ++i) {
            list.add(i);
        }
        for (int i = initialSize; i < initialSize + 5; ++i) {
            heap2.insertar(i, i);
            list.add(i);
        }
        BinomialHeap<Integer> merged = heap.meld(heap2);
        assertEquals(heap.size() + heap2.size(), merged.size());
        list.sort(Collections.reverseOrder());
        for (Integer aList : list) {
            assertEquals(aList, merged.extraer_siguiente());
        }
    }
}