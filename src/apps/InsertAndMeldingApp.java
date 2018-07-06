package apps;

import heap.IHeap;
import heap.PriorityWrapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class InsertAndMeldingApp implements Application {
    <T extends IHeap<T, S>, S>
    List<T> tournamentIteration(List<T> heaps) {
        List<T> ans = new ArrayList<>();
        for (int i=0; i<heaps.size(); i+=2) {
            T a = heaps.get(i);
            T b = heaps.get(i+1);
            ans.add(a.meld(b));
        }
        return ans;
    }

    @Override
    public List<Experiment> getExperiments() {
        List<Experiment> experiments = new ArrayList<>();
        int n = 1 << 20;
        List<PriorityWrapper<Integer>> data = Application.generate(n);
        for (int i = 0; i <= 15; i++) {
            int k = 1 << i;
            Experiment experiment = new Experiment(data);
            experiment.json.put("n", n);
            experiment.json.put("i", i);
            experiment.json.put("k", k);
            experiments.add(experiment);
        }
        return experiments;
    }

    @Override
    public String getName() {
        return "Inserción y Melding";
    }

    @Override
    public <T extends IHeap<T, S>, S extends Comparable<S>>
    void doExperiment(Class<?> heapClass, Experiment experiment) {
        List<PriorityWrapper<S>> data = experiment.data;
        int k = (int) experiment.json.get("k");

        int partSize = data.size() / k;
        List<T> heaps = new ArrayList<>();
        Timer t = new Timer();
        for (int start=0; start<data.size(); start+=partSize) {
            T heap = Application.newHeap(heapClass);
            for (int i=start; i<start+partSize; i++) {
                PriorityWrapper<S> pw = data.get(i);
                heap.insertar(pw.getValue(), pw.getPriority());
            }
            heaps.add(heap);
        }
        experiment.addMeasure("Inserción", t.ellapsed());

        t = new Timer();
        while (heaps.size() > 1) {
            heaps = tournamentIteration(heaps);
        }
        experiment.addMeasure("Melding", t.ellapsed());
    }


}
