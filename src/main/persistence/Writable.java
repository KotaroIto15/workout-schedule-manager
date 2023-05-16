package persistence;

import org.json.JSONObject;

// general specifications for toJson method
// << codes in this interface were retrieved from JsonSerializationDemo.java >>
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
