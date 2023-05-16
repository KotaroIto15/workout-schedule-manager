package persistence;

import model.Protein;
import model.Schedule;
import model.Workout;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads schedule from JSON data stored in file
// << codes in this class were retrieved from JsonSerializationDemo.java >>
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads schedule from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Schedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSchedule(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses schedule from JSON object and returns it
    private Schedule parseSchedule(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Schedule sc = new Schedule(name);
        addMenus(sc, jsonObject);
        addProtein(sc, jsonObject);
        return sc;
    }

    // MODIFIES: sc
    // EFFECTS: parses menus from JSON object and adds them to schedule
    private void addMenus(Schedule sc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("menus");
        for (Object json : jsonArray) {
            JSONObject nextMenu = (JSONObject) json;
            addMenu(sc, nextMenu);
        }
    }

    // MODIFIES: sc
    // EFFECTS: parses workout menu from JSON object and adds it to schedule
    private void addMenu(Schedule sc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int set = jsonObject.getInt("set");
        int rep = jsonObject.getInt("rep");
        int rest = jsonObject.getInt("rest");

        Workout workout = new Workout(name, set, rep, rest);
        sc.addMenu(workout);
    }

    // MODIFIES: sc
    // EFFECTS: parses protein from JSON object and adds it to schedule
    private void addProtein(Schedule sc, JSONObject jsonObject) {
        JSONObject pr = jsonObject.getJSONObject("protein");
        String flavor = pr.getString("flavor");
        int remainingAmount = pr.getInt("remaining amount");

        Protein protein = new Protein(flavor, remainingAmount);
        sc.setProtein(protein);
    }
}
