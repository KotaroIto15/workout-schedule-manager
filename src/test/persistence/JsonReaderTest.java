package persistence;

import model.Protein;
import model.Schedule;
import model.Workout;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// tests for JsonReader class
// << test codes retrieved from JsonSerializationDemo.java >>
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Schedule sc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySchedule.json");
        try {
            Schedule sc = reader.read();
            assertEquals("Today's schedule", sc.getName());
            assertEquals(0, sc.getMenus().size());
            assertEquals("", sc.getProtein().getFlavor());
            assertEquals(0, sc.getProtein().getRemainingAmount());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSchedule() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSchedule.json");
        try {
            Schedule sc = reader.read();
            assertEquals("Today's schedule", sc.getName());
            List<Workout> menus = sc.getMenus();
            Protein protein = sc.getProtein();

            assertEquals(2, menus.size());
            checkWorkout("bench press", 3, 10, 60, menus.get(0));
            checkWorkout("squat", 5, 8, 90, menus.get(1));
            checkProtein("vanilla", 800, protein);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
