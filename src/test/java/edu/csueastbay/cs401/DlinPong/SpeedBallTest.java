package edu.csueastbay.cs401.DlinPong;

import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpeedBallTest {
    SpeedBall speedBall;

    @BeforeEach
    void setup() {speedBall = new SpeedBall("Test SpeedBall", 650, 500, 10, 30);}

    @Test
    void getID() {
        assertEquals("Test SpeedBall", speedBall.getID(), "Should return 'Test SpeedBall' for the ID");
    }

    @Test
    void getType() {
        assertEquals("SpeedBall", speedBall.getType(),
                "Should return 'SpeedBall' for the type.");
    }



    @Test
    void getNoCollision() {
        Rectangle rect = new Rectangle(50, 50, 10, 10);
        Collision bang = speedBall.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("SpeedBall", bang.getType());
        assertEquals("Test SpeedBall", bang.getObjectID());
        assertEquals(655, bang.getCenterX());
        assertEquals(515, bang.getCenterY());
        assertEquals(500, bang.getTop());
        assertEquals(530, bang.getBottom());
        assertEquals(650, bang.getLeft());
        assertEquals(660, bang.getRight());
    }




}
