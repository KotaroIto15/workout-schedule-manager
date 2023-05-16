package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests for Workout class
public class WorkoutTest {

    private Workout workoutTest;

    @BeforeEach
    void runBefore() {
        workoutTest = new Workout("Bench Press", 3, 10, 60);
    }

    @Test
    void testConstructor() {
        assertEquals("Bench Press", workoutTest.getName());
        assertEquals(3, workoutTest.getSet());
        assertEquals(10, workoutTest.getRep());
        assertEquals(60, workoutTest.getRest());
    }

    @Test
    void testChangeSet() {
        workoutTest.changeSet(3);
        assertEquals(3, workoutTest.getSet());

        workoutTest.changeSet(10);
        assertEquals(10, workoutTest.getSet());

        workoutTest.changeSet(5);
        assertEquals(5, workoutTest.getSet());

        workoutTest.changeSet(7);
        assertEquals(7, workoutTest.getSet());

        workoutTest.changeSet(0);
        assertEquals(0, workoutTest.getSet());

        workoutTest.changeSet(20);
        assertEquals(20, workoutTest.getSet());
    }

    @Test
    void testChangeRep() {
        workoutTest.changeRep(10);
        assertEquals(10, workoutTest.getRep());

        workoutTest.changeRep(8);
        assertEquals(8, workoutTest.getRep());

        workoutTest.changeRep(15);
        assertEquals(15, workoutTest.getRep());

        workoutTest.changeRep(20);
        assertEquals(20, workoutTest.getRep());

        workoutTest.changeRep(0);
        assertEquals(0, workoutTest.getRep());

        workoutTest.changeRep(100);
        assertEquals(100, workoutTest.getRep());

        workoutTest.changeRep(200);
        assertEquals(200, workoutTest.getRep());
    }

    @Test
    void testChangeRest() {
        workoutTest.changeRest(60);
        assertEquals(60, workoutTest.getRest());

        workoutTest.changeRest(5);
        assertEquals(5, workoutTest.getRest());

        workoutTest.changeRest(90);
        assertEquals(90, workoutTest.getRest());

        workoutTest.changeRest(120);
        assertEquals(120, workoutTest.getRest());

        workoutTest.changeRest(0);
        assertEquals(0, workoutTest.getRest());

        workoutTest.changeRest(180);
        assertEquals(180, workoutTest.getRest());
    }

}
