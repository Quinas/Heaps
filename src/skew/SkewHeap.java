package skew;

import heap.IHeap;
import heap.PriorityWrapper;

class Node<S> {
    PriorityWrapper<S> value;
    Node<S> left, right;
    int size;

    Node(PriorityWrapper<S> value, Node<S> left, Node<S> right) {
        this.value = value;
        this.left = left;
        this.right = right;
        size = 1 + (left != null ? left.size : 0) + (right != null ? right.size : 0);
    }
}

public class SkewHeap<S> implements IHeap<SkewHeap<S>, S> {
    Node<S> root;

    public SkewHeap() {
        root = null;
    }

    SkewHeap(Node<S> root) {
        this.root = root;
    }

    public static <S> Node<S> meld(Node<S> a, Node<S> b) {
        if (a == null && b == null) {
            return null;
        }

        if (b == null && a.right == null) {
            return new Node<>(a.value, a.left, null);
        }
        if (a == null && b.right == null) {
            return new Node<>(b.value, b.left, null);
        }

        if (a != null && (b == null || a.value.compareTo(b.value) >= 0)) {
            return new Node<>(a.value, meld(a.right, b), a.left);
        }
        else {
            return new Node<>(b.value, meld(a, b.right), b.left);
        }
    }

    @Override
    public void insertar(S x, int p) {
        PriorityWrapper<S> value = new PriorityWrapper<>(x, p);
        Node<S> singleton = new Node<>(value, null, null);
        root = meld(root, singleton);
    }

    @Override
    public S extraer_siguiente() {
        S top = root.value.getValue();
        root = meld(root.left, root.right);
        return top;
    }

    @Override
    public SkewHeap<S> meld(SkewHeap<S> c) {
        return new SkewHeap<>(meld(this.root, c.root));
    }

    @Override
    public S getTop() {
        return root.value.getValue();
    }

    @Override
    public int size() {
        return root != null ? root.size : 0;
    }

    @Override
    public String getName() {
        return "Skew Heap";
    }
}
