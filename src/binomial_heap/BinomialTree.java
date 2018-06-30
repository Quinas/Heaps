package binomial_heap;

import java.util.ArrayList;
import java.util.List;

public class BinomialTree<T extends Comparable<T>> {

    private List<BinomialTree<T>> children;
    private T value;
    private int degree;
    private int size;

    public BinomialTree(T value) {
        this.value = value;
        degree = 0;
        size = 1;
        children = new ArrayList<>();
    }

    public BinomialTree<T> merge(BinomialTree<T> tree) {
        if (degree != tree.getDegree()) {
            throw new AssertionError();
        }
        if (value.compareTo(tree.value) > 0) {
            children.add(tree);
            size += tree.size;
            degree++;
            return this;
        } else {
            tree.children.add(this);
            tree.size += size;
            tree.degree++;
            return tree;
        }
    }


    public int getDegree() {
        return degree;
    }

    public T getValue() {
        return value;
    }

    public List<BinomialTree<T>> getChildren() {
        return children;
    }

    public int getSize() {
        return size;
    }

}
