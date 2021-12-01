package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Wall;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorTest {

    Sensor testSensor;

    @BeforeEach
    void setUp() {
        testSensor = new Sensor("Test Sensor", 10, 10, 1000, 10);
    }

    @Test
    void getID() {
        assertEquals("Test Sensor", testSensor.getID(),
                "Test Wall should have a id of 'Test Wall'");
    }

    @Test
    void getType() {
        assertEquals("Sensor", testSensor.getType(),
                "Sensor should have a type of 'Sensor'");
    }

    @Test
    void getCollision() {
        Rectangle rect = new Rectangle(10, 10, 10, 10);
        Collision bang = testSensor.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Sensor", bang.getType());
        assertEquals("Test Sensor", bang.getObjectID());
        assertEquals(510, bang.getCenterX());
        assertEquals(15, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(20, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(1010, bang.getRight());
    }

    @Test
    void getNoCollision() {
        Rectangle rect = new Rectangle(100, 100, 10, 10);
        Collision bang = testSensor.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Sensor", bang.getType());
        assertEquals("Test Sensor", bang.getObjectID());
        assertEquals(510, bang.getCenterX());
        assertEquals(15, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(20, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(1010, bang.getRight());
    }

}