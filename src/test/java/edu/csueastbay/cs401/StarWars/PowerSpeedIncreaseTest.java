package edu.csueastbay.cs401.StarWars;

import edu.csueastbay.cs401.StarWarsPong.PowerSpeedIncrease;
import edu.csueastbay.cs401.pong.Collision;

import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerSpeedIncreaseTest {

    PowerSpeedIncrease increase;

    @BeforeEach
    void setUp() {
        increase = new PowerSpeedIncrease(500, 500);
    }

    @Test
    void getID() {
        assertEquals("Test SpeedIncrease ", increase.getID(),
                "Should be able to get the PointsDrain ID");
    }

    @Test
    void getType() {
        assertEquals("SpeedIncrease", increase.getType(),
                "getType should return 'SpeedIncrease'");
    }

    @Test
    void getCollision() {
        Circle circle = new Circle();
        Collision bang = increase.getCollision(circle);
        assertTrue(bang.isCollided());
        assertEquals("SpeedIncrease", bang.getType());
        assertEquals("Test SpeedIncrease", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(60, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(110, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

    @Test
    void getNoCollision() {
        Circle circle = new Circle();
        Collision bang = increase.getCollision(circle);
        assertFalse(bang.isCollided());
        assertEquals("SpeedIncrease", bang.getType());
        assertEquals("Test SpeedIncrease", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(60, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(110, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

}