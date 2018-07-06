package apps;

import heap.IHeap;
import heap.PriorityWrapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class SortApp implements Application {
    @Override
    public String getName() {
        return "Ordenar";
    }

    @Override
    public <T extends IHeap<T, S>, S extends Comparable<S>>
    void doExperiment(Class<?> heapClass, Experiment experiment) {
        List<PriorityWrapper<S>> data = experiment.data;

        Timer t = new Timer();
        T heap = Application.newHeap(heapClass);
        heap.heapify(data);
        experiment.addMeasure("heapify", t.ellapsed());

        List<S> sorted = new ArrayList<>();
        t = new Timer();
        while (heap.size() > 0) {
            sorted.add(heap.extraer_siguiente());
        }
        experiment.addMeasure("extraer_siguiente", t.ellapsed());

        for (int i=0; i<sorted.size()-1; i++) {
            S a = sorted.get(i);
            S b = sorted.get(i+1);
            if (a.compareTo(b) < 0) {
                throw new RuntimeException(
                        "los elementos no salieron de mayor a menor");
            }
        }
    }


    @Override
    public List<Experiment> getExperiments() {
        List<Experiment> experiments = new ArrayList<>();
        for (int j = 15; j <= 21; j++) {
            int n = 1 << j;
            List<PriorityWrapper<Integer>> data = Application.generate(n);
            Experiment experiment = new Experiment(data);
            experiment.json.put("j", j);
            experiment.json.put("n", n);
            experiments.add(experiment);
        }
        return experiments;
    }
}
