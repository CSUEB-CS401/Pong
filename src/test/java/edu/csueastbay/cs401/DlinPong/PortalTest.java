package edu.csueastbay.cs401.DlinPong;

import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortalTest {

    Portal testPortal;

    @BeforeEach
    void setup() {testPortal = new Portal("Test Portal", 10, 10, 10, 100);}

    @Test
    void getID() {
        assertEquals("Test Portal", testPortal.getID(), "Should return 'Test Portal' for the ID");
    }

    @Test
    void getType(){ assertEquals("Portal", testPortal.getType(), "Should return 'Portal' for the type");}

    @Test
    void getDimensions() {
        assertEquals(10,testPortal.getX());
        assertEquals(10,testPortal.getY());
        assertEquals(10,testPortal.getWidth());
        assertEquals(100,testPortal.getHeight());
    }

    @Test
    void getCollision() {
        Rectangle rect = new Rectangle(10, 10, 10, 10);
        Collision bang = testPortal.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Portal", bang.getType());
        assertEquals("Test Portal", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(60, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(110, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

    @Test
    void getNoCollision() {
        Rectangle rect = new Rectangle(50, 50, 10, 10);
        Collision bang = testPortal.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Portal", bang.getType());
        assertEquals("Test Portal", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(60, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(110, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }
}
