package apps;

import heap.PriorityWrapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Experiment<S> {
    public List<PriorityWrapper<S>> data;
    public JSONObject json;

    Experiment(List<PriorityWrapper<S>> data) {
        this.data = data;
        this.json = new JSONObject();
    }

    void startMeasuring() {
        List<String> keys = new ArrayList<>();
        for (Object o : json.keySet()) {
            String key = (String) o;
            keys.add(key);
        }
        for (String key : keys) {
            if (json.get(key) instanceof JSONArray) {
                json.remove(key);
            }
        }
    }

    void addMeasure(String name, double time) {
        if (!json.containsKey(name)) {
            json.put(name, new JSONArray());
        }
        JSONArray array = (JSONArray) json.get(name);
        array.add(time);
    }
}
