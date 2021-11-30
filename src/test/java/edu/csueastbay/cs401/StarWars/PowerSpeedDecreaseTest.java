package edu.csueastbay.cs401.StarWars;

import edu.csueastbay.cs401.StarWarsPong.PowerSpeedDecrease;
import edu.csueastbay.cs401.pong.Collision;

import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerSpeedDecreaseTest {
    PowerSpeedDecrease decrease;

    @BeforeEach
    void setUp() {
        decrease = new PowerSpeedDecrease(500, 500);
    }

    @Test
    void getID() {
        assertEquals("Test SpeedDecrease ", decrease.getID(),
                "Should be able to get the SpeedDecrease ID");
    }

    @Test
    void getType() {
        assertEquals("SpeedDecrease", decrease.getType(),
                "getType should return 'SpeedDecrease'");
    }

    @Test
    void getCollision() {
        Circle circle = new Circle();
        Collision bang = decrease.getCollision(circle);
        assertTrue(bang.isCollided());
        assertEquals("SpeedDecrease", bang.getType());
        assertEquals("Test SpeedDecrease", bang.getObjectID());
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
        Collision bang = decrease.getCollision(circle);
        assertFalse(bang.isCollided());
        assertEquals("SpeedDecrease", bang.getType());
        assertEquals("Test SpeedDecrease", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(60, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(110, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

}