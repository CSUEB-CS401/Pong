package edu.csueastbay.cs401.StarWars;


import edu.csueastbay.cs401.StarWarsPong.PowerPointsGain;
import edu.csueastbay.cs401.pong.Collision;

import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerPointsGainTest {
    PowerPointsGain gain;

    @BeforeEach
    void setUp() {
        gain = new PowerPointsGain (500, 500);
    }

    @Test
    void getID() {
        assertEquals("Test PointsGain ", gain.getID(),
                "Should be able to get the PointsGain ID");
    }

    @Test
    void getType() {
        assertEquals("PointsGain", gain.getType(),
                "getType should return 'PointsGain'");
    }

    @Test
    void getCollision() {
        Circle circle = new Circle();
        Collision bang = gain.getCollision(circle);
        assertTrue(bang.isCollided());
        assertEquals("PointsGain", bang.getType());
        assertEquals("Test PointsGain", bang.getObjectID());
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
        Collision bang = gain.getCollision(circle);
        assertFalse(bang.isCollided());
        assertEquals("PointsGain", bang.getType());
        assertEquals("Test PointsGain", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(60, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(110, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }


}