package edu.csueastbay.cs401.StarWars;

import edu.csueastbay.cs401.StarWarsPong.PowerPointsDrain;
import edu.csueastbay.cs401.pong.Collision;

import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PowerPointsDrainTest {
   PowerPointsDrain drain;

    @BeforeEach
    void setUp() {
        drain = new PowerPointsDrain (500, 500);
    }

    @Test
    void getID() {
        assertEquals("Test PointsDrain ", drain.getID(),
                "Should be able to get the PointsDrain ID");
    }

    @Test
    void getType() {
        assertEquals("Size", drain.getType(),
                "getType should return 'Size'");
    }

    @Test
    void getCollision() {
        Circle circle = new Circle();
        Collision bang = drain.getCollision(circle);
        assertTrue(bang.isCollided());
        assertEquals("Size", bang.getType());
        assertEquals("Test PointsDrain", bang.getObjectID());
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
        Collision bang = drain.getCollision(circle);
        assertFalse(bang.isCollided());
        assertEquals("Size", bang.getType());
        assertEquals("Test PointsDrain", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(60, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(110, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }


}