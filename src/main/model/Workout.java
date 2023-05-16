package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a workout having name, sets, reps, and rest period(in seconds)
public class Workout implements Writable {

    private String name;  // name of the workout menu (e.g. Bench Press)
    private int set;      // set count of the workout
    private int rep;      // reps for each set of the workout
    private int rest;     // rest period between each set in seconds

    // REQUIRES: set, rep, and rest are all equal to, or greater than 0 (>= 0)
    //           and name is not an empty String
    // EFFECTS: creates a new Workout object with the given name, set, rep and rest
    public Workout(String name, int set, int rep, int rest) {
        this.name = name;
        this.set  = set;
        this.rep  = rep;
        this.rest = rest;
    }

    // REQUIRES: 0 <= newSet <= Schedule.MAX_SET
    // MODIFIES: this
    // EFFECTS: change the number of sets with the given set count
    public void changeSet(int newSet) {
        set = newSet;
    }

    // REQUIRES: 0 <=  newRep <= Schedule.MAX_REP
    // MODIFIES: this
    // EFFECTS: change the number of reps with the given rep
    public void changeRep(int newRep) {
        rep = newRep;
    }

    // REQUIRES: newRest >= 0
    // MODIFIES: this
    // EFFECTS: change the rest period with the given rest value
    public void changeRest(int newRest) {
        rest = newRest;
    }

    // EFFECTS: gets the name of the workout
    public String getName() {
        return name; // stub
    }

    // EFFECTS: gets the number of sets of the workout
    public int getSet() {
        return set; // stub
    }

    // EFFECTS: gets the number of reps of the workout
    public int getRep() {
        return rep; // stub
    }

    // EFFECTS: gets the rest period of the workout
    public int getRest() {
        return rest; // stub
    }

    // <code retrieved from JsonSerializationDemo.java>
    // EFFECTS: returns this workout as a JsonObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("set", set);
        json.put("rep", rep);
        json.put("rest", rest);
        return json;
    }


}
