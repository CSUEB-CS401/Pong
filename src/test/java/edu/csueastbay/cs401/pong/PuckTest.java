package edu.csueastbay.cs401.pong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

import static org.junit.jupiter.api.Assertions.*;

class PuckTest {

    Puck testPuck;

    @BeforeEach
    void setup() {
        testPuck = new Puck(500, 500);
    }

    @Test
    void defaultPuck() {
        assertEquals(Color.WHITE, testPuck.getFill(),
                "Puck should be white");
        assertEquals(5, testPuck.getRadius(),
                "Puck should start with a radius of 5f");

        assertEquals(250, testPuck.getCenterX(),
                "After reset the puck should be back at center X");
        assertEquals(250, testPuck.getCenterY(),
                "After reset the puck should be back at center Y");
        assertEquals(5, testPuck.getSpeed(),
                "Test Puck should have a speed of 5 after reset");
        double d = testPuck.getDirection();
        assertTrue((d > -45 && d < 45) || (d > 115 && d < 205),
                "Test Puch should be pointing roughly at player 1 or player 2");
    }

    @Test
    void setID() {
        testPuck.setID("TestPuck");
    }

    @Test
    void getID() {
        testPuck.setID("SetTestPuck");
        assertEquals("SetTestPuck", testPuck.getID(),
                "Should be able to set and get the puck id");
    }

    @Test
    void setSpeed() {
        testPuck.setSpeed(10);
    }

    @Test
    void getSpeed() {
        testPuck.setSpeed(9);
        assertEquals(9, testPuck.getSpeed(),
                "Should be able to set and get the puck speed");
    }

    @Test
    void setDirection() {
        testPuck.setDirection(90);
    }

    @Test
    void getDirection() {
        testPuck.setDirection(180);
        assertEquals(180, testPuck.getDirection());
    }

    @Test
    void move_test_x() {
        testPuck.setDirection(0);
        testPuck.setSpeed(10);
        testPuck.move();
        assertEquals(260, testPuck.getCenterX(),
                "Puck should have moved 10 pixels to the right");
    }

    @Test
    void move_move_test_y() {
        testPuck.setDirection(90);
        testPuck.setSpeed(5);
        testPuck.move();
        assertEquals(255, testPuck.getCenterY(),
                "Puck should have moved 5 pixels to the down");
    }

    @Test
    void reset() {
        testPuck.setSpeed(20);
        testPuck.setDirection(100);
        testPuck.move();
        testPuck.move();
        testPuck.reset();
        assertEquals(250, testPuck.getCenterX(),
                "After reset the puck should be back at center X");
        assertEquals(250, testPuck.getCenterY(),
                "After reset the puck should be back at center Y");
        assertEquals(5, testPuck.getSpeed(),
                "Test Puck should have a speed of 5 after reset");
        double d = testPuck.getDirection();
        assertTrue((d > -45 && d < 45) || (d > 115 && d < 205),
                "Test Puch should be pointing roughly at player 1 or player 2");
    }
}