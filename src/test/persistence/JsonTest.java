package persistence;

import model.Protein;
import model.Workout;

import static org.junit.jupiter.api.Assertions.assertEquals;

// general test methods to check if workout and protein are properly created
// << part of the code was retrieved from JsonSerializationDemo.java >>
public class JsonTest {
    protected void checkWorkout(String name, int set, int rep, int rest, Workout menu) {
        assertEquals(name, menu.getName());
        assertEquals(set, menu.getSet());
        assertEquals(rep, menu.getRep());
        assertEquals(rest, menu.getRest());
    }

    protected void checkProtein(String flavor, int amount, Protein protein) {
        assertEquals(flavor, protein.getFlavor());
        assertEquals(amount, protein.getRemainingAmount());
    }
}
