package heap;

public interface IHeap<T extends IHeap<T, S>, S> {
    void insertar(S x, int p);

    S extraer_siguiente();

    T meld(T c);

    S getTop();

    int size();
}
