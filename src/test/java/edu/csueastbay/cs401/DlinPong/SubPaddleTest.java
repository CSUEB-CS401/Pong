package edu.csueastbay.cs401.DlinPong;

import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubPaddleTest {
    SubPaddle testSubPaddle;

    @BeforeEach
    void setUP() {
        testSubPaddle = new SubPaddle("Test SubPaddle", 550, 500, 10, 50, 10, 200);
    }



    @Test
    void getID() {
        assertEquals("Test SubPaddle", testSubPaddle.getID(),
                "Should return the subpaddle ID.");
    }

    @Test
    void getType() {
        assertEquals("Paddle", testSubPaddle.getType(),
                "Should return 'Paddle' for the type.");
    }

    @Test
    void getXLocation(){
        assertEquals(550, testSubPaddle.getX());
    }

    @Test
    void getYLocation(){
        assertEquals(500, testSubPaddle.getY());
    }


    @Test
    void getCollision() {
        Rectangle rect = new Rectangle(550, 500, 10, 50);
        Collision bang = testSubPaddle.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Paddle", bang.getType());
        assertEquals("Test SubPaddle", bang.getObjectID());
        assertEquals(555, bang.getCenterX());
        assertEquals(525, bang.getCenterY());
        assertEquals(500, bang.getTop());
        assertEquals(550, bang.getBottom());
        assertEquals(550, bang.getLeft());
        assertEquals(560, bang.getRight());
    }

    @Test
    void getNoCollision() {
        Rectangle rect = new Rectangle(50, 50, 10, 10);
        Collision bang = testSubPaddle.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Paddle", bang.getType());
        assertEquals("Test SubPaddle", bang.getObjectID());
        assertEquals(555, bang.getCenterX());
        assertEquals(525, bang.getCenterY());
        assertEquals(500, bang.getTop());
        assertEquals(550, bang.getBottom());
        assertEquals(550, bang.getLeft());
        assertEquals(560, bang.getRight());
    }
}
