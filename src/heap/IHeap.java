package heap;

import java.util.List;

public interface IHeap<T extends IHeap<T, S>, S> {
  void insertar(S x, int p);

  S extraer_siguiente();

  T meld(T c);

  S getTop();

  int size();

  default void heapify(List<PriorityWrapper<S>> data) {
    for (PriorityWrapper<S> pw : data) {
      insertar(pw.getValue(), pw.getPriority());
    }
  }

  String getName();
}
