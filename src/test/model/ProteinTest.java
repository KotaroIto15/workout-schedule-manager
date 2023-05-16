package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests for Protein class
class ProteinTest {

    private Protein proteinTest;

    @BeforeEach
    void runBefore() {
        proteinTest = new Protein("Chocolate", 500);
    }

    @Test
    void testConstructor() {
        assertEquals("Chocolate", proteinTest.getFlavor());
        assertEquals(500, proteinTest.getRemainingAmount());
    }


    @Test
    void testConsume1() {
        proteinTest.consume(0);
        assertEquals(500, proteinTest.getRemainingAmount());

        proteinTest.consume(300);
        assertEquals(200, proteinTest.getRemainingAmount());

        proteinTest.consume(150);
        assertEquals(50, proteinTest.getRemainingAmount());

        proteinTest.consume(50);
        assertEquals(0, proteinTest.getRemainingAmount());
    }

    @Test
    void testConsume2() {
        proteinTest.consume(500);
        assertEquals(0, proteinTest.getRemainingAmount());
    }

    @Test
    void testConsume3() {
        proteinTest.consume(499);
        assertEquals(1, proteinTest.getRemainingAmount());

        proteinTest.consume(1);
        assertEquals(0, proteinTest.getRemainingAmount());
    }
}