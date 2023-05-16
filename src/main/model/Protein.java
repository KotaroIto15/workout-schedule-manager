package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents the protein powder having the flavor, and remaining amount (in g)
public class Protein implements Writable {

    private String flavor; // name of the flavor
    private int remainingAmount;    // remaining amount in g

    // REQUIRES: amount >= 0, flavor is not an empty String
    // EFFECTS: creates a new Protein object with the given flavor name
    //          and the remaining amount
    public Protein(String flavor, int amount) {
        this.flavor = flavor;
        this.remainingAmount = amount;
    }

    // EFFECTS: gets the name of the flavor
    public String getFlavor() {
        return flavor; //stub
    }

    // EFFECTS: gets the remaining amount of the protein powder
    public int getRemainingAmount() {
        return remainingAmount; //stub
    }

    // REQUIRES: 0 <= amount <= this.getRemainingAmount()
    // MODIFIES: this
    // EFFECTS: if the given amount is equal to or less than the remaining amount,
    //          consume the given amount of protein powder and deducts the amount from the
    //          remaining amount
    public void consume(int amount) {
        remainingAmount -= amount;
    }

    // <code retrieved from JsonSerializationDemo.java>
    // EFFECTS: returns this protein as a JsonObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("flavor", flavor);
        json.put("remaining amount", remainingAmount);

        return json;
    }
}
