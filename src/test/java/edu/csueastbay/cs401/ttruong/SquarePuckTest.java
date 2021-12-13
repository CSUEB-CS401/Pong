package edu.csueastbay.cs401.ttruong;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SquarePuckTest {
    SquarePuck squarePuck;

    @BeforeEach
    void setUp() {
        squarePuck = new SquarePuck(1300, 860);
    }

    @Test
    void setID() {
        squarePuck.setID("Square Puck");
        assertEquals("Square Puck", squarePuck.getID(),
                "Should return 'Square Puck'.");
    }

    @Test
    void setSpeed() {
        squarePuck.setSpeed(20.0);
    }

    @Test
    void getSpeed() {
        squarePuck.setSpeed(20.0);
        assertEquals(20.0, squarePuck.getSpeed(),
                "Should be able to set the speed to 20 and get the return speed value of 20.");
    }

    @Test
    void move_test_x() {
        squarePuck.setDirection(0);
        squarePuck.setSpeed(10);
        squarePuck.move();
        assertEquals(647.5, squarePuck.getCenterX(),
                "Puck should move 10 pixels to the right.");
    }

    @Test
    void move_move_test_y() {
        squarePuck.setDirection(90);
        squarePuck.setSpeed(5);
        squarePuck.move();
        assertEquals(447.5, squarePuck.getCenterY(),
                "Puck should move 5 pixels down.");
    }

    @Test
    void defaultPuck() {
        assertEquals(Color.WHITE, squarePuck.getFill(),
                "The color of the square puck should be white.");
        assertEquals(25, squarePuck.getWidth(),
                "Width of the square puck should be 25.");

        assertEquals(637.5, squarePuck.getCenterX(),
                "After reset the puck should be back at center X.");
        assertEquals(442.5, squarePuck.getCenterY(),
                "After reset the puck should be back at center Y.");
        assertEquals(5, squarePuck.getSpeed(),
                "Square puck should have a speed of 5 after reset.");
        double d = squarePuck.getDirection();
        assertTrue((d > -45 && d < 45) || (d > 115 && d < 205),
                "Square puck should be pointing roughly at player 1 or player 2.");
    }
}
