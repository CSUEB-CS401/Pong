package edu.csueastbay.cs401.srishti;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PuckTestEXT {

    PuckEXT testPuck;

    @BeforeEach
    void setup() {
        testPuck = new PuckEXT(500, 500);
    }

    @Test
    void reset() {
        testPuck.setSpeed(20);
        testPuck.setDirection(100);
        testPuck.move();
        testPuck.move();
        testPuck.reset();
        assertEquals(250, testPuck.getCenterX(), "After reset the puck should be back at center X");
        assertEquals(250, testPuck.getCenterY(), "After reset the puck should be back at center Y");
        assertEquals(5, testPuck.getSpeed(), "Test Puck should have a speed of 5 after reset");
        double d = testPuck.getDirection();
        assertTrue((d > -45 && d < 45) || (d > 115 && d < 205),
                "Test Puch should be pointing roughly at player 1 or player 2");
    }

}