package persistence;

import model.Protein;
import model.Schedule;
import model.Workout;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// tests for JsonWriter class
// << test cases taken from JsonSerializationDemo.java >>
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Schedule sc = new Schedule("Today's schedule");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySchedule() {
        try {
            Schedule sc = new Schedule("Today's schedule");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySchedule.json");
            writer.open();
            writer.write(sc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySchedule.json");
            sc = reader.read();
            assertEquals("Today's schedule", sc.getName());
            assertEquals(0, sc.getMenus().size());
            assertEquals("", sc.getProtein().getFlavor());
            assertEquals(0, sc.getProtein().getRemainingAmount());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSchedule() {
        try {
            Schedule sc = new Schedule("Today's schedule");
            sc.addMenu(new Workout("bench press", 3, 10, 60));
            sc.addMenu(new Workout("squat", 5, 8, 90));
            sc.setProtein(new Protein("chocolate", 900));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSchedule.json");
            writer.open();
            writer.write(sc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSchedule.json");
            sc = reader.read();
            assertEquals("Today's schedule", sc.getName());
            List<Workout> menus = sc.getMenus();
            Protein protein = sc.getProtein();
            assertEquals(2, menus.size());
            checkWorkout("bench press", 3, 10, 60, menus.get(0));
            checkWorkout("squat", 5, 8, 90, menus.get(1));
            checkProtein("chocolate", 900, protein);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
