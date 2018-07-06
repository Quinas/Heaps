package apps;

import heap.IHeap;

import heap.PriorityWrapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface Application {
    static <T extends IHeap<T, S>, S>
    T newHeap(Class<?> heapClass) {
        try {
            //noinspection unchecked
            return (T) heapClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    String getName();

    <T extends IHeap<T, S>, S extends Comparable<S>>
    void doExperiment(Class<?> heapClass, Experiment experiment);

    List<Experiment> getExperiments();

    static List<PriorityWrapper<Integer>> generate(int n) {
        List<PriorityWrapper<Integer>> data = new ArrayList<>();
        for (int i=1; i<=n; i++) {
            data.add(new PriorityWrapper<>(i, i));
        }
        Collections.shuffle(data);
        return data;
    }
}
