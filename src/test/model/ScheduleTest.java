package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// tests for Schedule class
public class ScheduleTest {

    private Schedule scheduleTest;

    @BeforeEach
    void runBefore() {
        scheduleTest = new Schedule("Today's schedule");
    }

    @Test
    void testConstructor() {

        ArrayList<Workout> menus = scheduleTest.getMenus();
        assertTrue(menus.isEmpty());

        assertEquals("", scheduleTest.getProtein().getFlavor());
        assertEquals(0, scheduleTest.getProtein().getRemainingAmount());
    }

    @Test
    void testAddMenu() {
        Workout menu1 = new Workout("Bench Press", 3, 10, 60);
        scheduleTest.addMenu(menu1);
        ArrayList<Workout> menus = scheduleTest.getMenus();
        assertEquals(menu1, menus.get(0));
        assertEquals(1, menus.size());

        Workout menu2 = new Workout("Squat", 5, 20, 90);
        scheduleTest.addMenu(menu2);
        menus = scheduleTest.getMenus();
        assertEquals(menu2, menus.get(1));
        assertEquals(2, menus.size());
    }

    // Test if adding a single workout with too many repetitions results in isTooMuchWork producing true
    @Test
    void testIsTooMuchWork1() {
        Workout menu1 = new Workout("Squat", 10, 25, 90);
        scheduleTest.addMenu(menu1);
        assertTrue(scheduleTest.isTooMuchWork());
    }

    // Test if adding multiple workouts does not result in exceeding maximum intensity of 230.
    @Test
    void testIsTooMuchWork2() {
        Workout menu1 = new Workout("Bench Press", 3, 10, 60);
        scheduleTest.addMenu(menu1);
        assertFalse(scheduleTest.isTooMuchWork());

        Workout menu2 = new Workout("Squat", 5, 20, 90);
        scheduleTest.addMenu(menu2);
        assertFalse(scheduleTest.isTooMuchWork());

        Workout menu3 = new Workout("Incline Dumbbell Press", 6, 12, 120);
        scheduleTest.addMenu(menu3);
        assertFalse(scheduleTest.isTooMuchWork());
    }

    // Test if a single workout with too many sets will result in exceeding maximum intensity of 230.
    @Test
    void testIsTooMuchWork3() {
        Workout menu1 = new Workout("Bench Press", 25, 10, 180);
        scheduleTest.addMenu(menu1);
        assertTrue(scheduleTest.isTooMuchWork());

    }

    // Test if multiple workouts with too many sets results in isTooMuchWork producing true
    // case where total intensity is exactly at 230
    @Test
    void testIsTooMuchWork4() {
        Workout menu1 = new Workout("Bench Press", 3, 10, 60);
        scheduleTest.addMenu(menu1);
        assertFalse(scheduleTest.isTooMuchWork());

        Workout menu2 = new Workout("Squat", 10, 10, 90);
        scheduleTest.addMenu(menu2);
        assertFalse(scheduleTest.isTooMuchWork());

        Workout menu3 = new Workout("Incline Dumbbell Press", 6, 10, 120);
        scheduleTest.addMenu(menu3);
        assertFalse(scheduleTest.isTooMuchWork());

        Workout menu4 = new Workout("Decline Dumbbell Press", 4, 10, 120);
        scheduleTest.addMenu(menu4);
        assertTrue(scheduleTest.isTooMuchWork());
    }

    // Test if a single, too hard workout with too many sets & reps results in isTooMuchWork producing true
    // case where (total sets > MAX_SET) and (total reps > MAX_REP) are both true
    @Test
    void testIsTooMuchWork5() {
        Workout menu1 = new Workout("Dumbbell Shoulder Press", 25, 20, 60);
        scheduleTest.addMenu(menu1);

        assertTrue(scheduleTest.isTooMuchWork());
    }

    // Test if multiple workouts with too many sets & reps results in isTooMuchWork producing true
    // case where total intensity is way greater than 230
    @Test
    void testIsTooMuchWork6() {
        Workout menu1 = new Workout("Bench Press", 20, 10, 60);
        scheduleTest.addMenu(menu1);
        assertFalse(scheduleTest.isTooMuchWork());

        Workout menu2 = new Workout("Squat", 5, 5, 90);
        scheduleTest.addMenu(menu2);
        assertFalse(scheduleTest.isTooMuchWork());

        Workout menu3 = new Workout("Incline Dumbbell Press", 6, 8, 120);
        scheduleTest.addMenu(menu3);
        assertTrue(scheduleTest.isTooMuchWork());

        Workout menu4 = new Workout("Deadlift", 8, 13, 90);
        scheduleTest.addMenu(menu4);
        assertTrue(scheduleTest.isTooMuchWork());
    }

    @Test
    void testSetProtein() {
        Protein protein = new Protein("Chocolate", 500);
        scheduleTest.setProtein(protein);
        assertEquals(protein, scheduleTest.getProtein());

        protein = new Protein("Vanilla", 1000);
        scheduleTest.setProtein(protein);
        assertEquals(protein, scheduleTest.getProtein());
    }

    @Test
    void testSelectWorkout() {
        Workout menu1 = new Workout("Bench Press", 3, 10, 60);
        scheduleTest.addMenu(menu1);
        Workout menu2 = new Workout("Squat", 5, 20, 90);
        scheduleTest.addMenu(menu2);
        Workout menu3 = new Workout("Incline Dumbbell Press", 6, 8, 120);
        scheduleTest.addMenu(menu3);

        assertEquals(menu1, scheduleTest.selectWorkout(0));
        assertEquals(menu2, scheduleTest.selectWorkout(1));
        assertEquals(menu3, scheduleTest.selectWorkout(2));
    }

    @Test
    void testIsProteinInInitialState() {
        assertTrue(scheduleTest.isProteinInitialState());

        Protein protein1 = new Protein("Chocolate", 0);
        scheduleTest.setProtein(protein1);
        assertFalse(scheduleTest.isProteinInitialState());

        Protein protein2 = new Protein("", 900);
        scheduleTest.setProtein(protein2);
        assertFalse(scheduleTest.isProteinInitialState());

        Protein protein3 = new Protein("Vanilla", 600);
        scheduleTest.setProtein(protein3);
        assertFalse(scheduleTest.isProteinInitialState());
    }

    @Test
    void testSearchWorkoutSingleWorkoutMatches() {
        Workout menu1 = new Workout("A", 3, 10, 60);
        scheduleTest.addMenu(menu1);
        Workout menu2 = new Workout("B", 5, 20, 90);
        scheduleTest.addMenu(menu2);
        Workout menu3 = new Workout("C", 6, 8, 120);
        scheduleTest.addMenu(menu3);

        List<Workout> result = scheduleTest.searchWorkout("A");
        assertEquals(1, result.size());
        assertEquals(menu1, result.get(0));

        result = scheduleTest.searchWorkout("B");
        assertEquals(1, result.size());
        assertEquals(menu2, result.get(0));

        result = scheduleTest.searchWorkout("C");
        assertEquals(1, result.size());
        assertEquals(menu3, result.get(0));
    }

    @Test
    void testSearchWorkoutMultipleWorkoutMatch() {
        Workout menu1 = new Workout("A", 3, 10, 60);
        scheduleTest.addMenu(menu1);
        Workout menu2 = new Workout("A", 5, 20, 90);
        scheduleTest.addMenu(menu2);
        Workout menu3 = new Workout("B", 6, 8, 120);
        scheduleTest.addMenu(menu3);

        List<Workout> result = scheduleTest.searchWorkout("A");
        assertEquals(2, result.size());
        assertEquals(menu1, result.get(0));
        assertEquals(menu2, result.get(1));
    }

    @Test
    void testSearchWorkoutNoneMatches() {
        Workout menu1 = new Workout("A", 3, 10, 60);
        scheduleTest.addMenu(menu1);
        Workout menu2 = new Workout("A", 5, 20, 90);
        scheduleTest.addMenu(menu2);
        Workout menu3 = new Workout("B", 6, 8, 120);
        scheduleTest.addMenu(menu3);

        List<Workout> result = scheduleTest.searchWorkout("C");
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }


}
