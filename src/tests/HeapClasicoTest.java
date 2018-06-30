package tests;

import heap_clasico.HeapClasico;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;

public class HeapClasicoTest {

    final int initialSize = 10;
    HeapClasico<Integer> heap;

    @Before
    public void setUp() {
        heap = new HeapClasico<>();
        for (int i = 0; i < initialSize; ++i) {
            heap.insertar(i, i);
        }
    }

    @Test
    public void meld() {
        HeapClasico<Integer> heap2 = new HeapClasico<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < initialSize; ++i) {
            list.add(i);
        }
        for (int i = 7; i < 12; ++i) {
            heap2.insertar(i, i);
            list.add(i);
        }
        HeapClasico<Integer> merged = heap.meld(heap2);
        assertEquals(heap.size() + heap2.size(), merged.size());
        list.sort(Collections.reverseOrder());
        for (Integer aList : list) {
            assertEquals(aList, merged.extraer_siguiente());
        }
    }
}