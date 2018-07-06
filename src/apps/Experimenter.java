package apps;

import binomial_heap.BinomialHeap;
import fibonacci_heap.FibonacciHeap;
import heap.IHeap;
import heap.PriorityWrapper;
import heap_clasico.HeapClasico;
import leftist_heap.LeftistHeap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import skew.SkewHeap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Experimenter {
    public static void main(String[] args) {
        List<Application> apps = new ArrayList<>();
        apps.add(new SortApp());
        apps.add(new InsertAndMeldingApp());

        List<Class<?>> implementations = new ArrayList<>();
        implementations.add(HeapClasico.class);
        implementations.add(BinomialHeap.class);
        implementations.add(FibonacciHeap.class);
        implementations.add(LeftistHeap.class);
        implementations.add(SkewHeap.class);

        int nMeasures = 13;
        JSONObject json = new JSONObject();
        for (Application app : apps) {
            JSONObject appJson = new JSONObject();
            List<Experiment> experiments = app.getExperiments();
            for (Class<?> impl : implementations) {
                String implName = Application.newHeap(impl).getName();
                System.out.printf("impl: %s\n", implName);
                JSONArray expArray = new JSONArray();
                for (Experiment experiment : experiments) {
                    experiment.startMeasuring();
                    System.out.printf("experiment: %s\n", experiment.json);
                    for (int m = 0; m < nMeasures; m++) {
                        System.out.printf("m = %d\n", m);
                        app.doExperiment(impl, experiment);
                    }
                    expArray.add(experiment.json.clone());
                }
                appJson.put(implName, expArray);
            }
            json.put(app.getName(), appJson);
        }

        System.out.println(json);

        String saveTo = "experiments.json";
        try {
            PrintWriter out = new PrintWriter(saveTo, "UTF-8");
            out.println(json);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
