package edu.csueastbay.cs401.ttruong;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AIPaddleTest {
    AIPaddle botPaddle;
    Puckable puck = new Puck(1300, 860);

    @BeforeEach
    void setUP() {
        botPaddle = new AIPaddle("Bot Paddle", 10, 50, 10, 50, 10, 200, puck);
    }


    @Test
    void getID() {
        assertEquals("Bot Paddle", botPaddle.getID(),
                "Should return Bot Paddle, the set ID.");
    }

    @Test
    void move() {
        botPaddle.move();
        assertEquals(45, 55, botPaddle.getY(),
                "Should have a Y value of 45 or 55");
    }

    @Test
    void stop() {
        botPaddle.move();
        botPaddle.stop();
        assertEquals(10.0, botPaddle.getX(),
                "Paddle should stop moving after stop is called.");
    }

    @Test
    void getCollision() {
        Rectangle rect = new Rectangle(10, 50, 10, 10);
        Collision bang = botPaddle.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Paddle", bang.getType());
        assertEquals("Bot Paddle", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(75, bang.getCenterY());
        assertEquals(50, bang.getTop());
        assertEquals(100, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

    @Test
    void getNoCollision() {
        Rectangle rect = new Rectangle(50, 50, 10, 10);
        Collision bang = botPaddle.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Paddle", bang.getType());
        assertEquals("Bot Paddle", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(75, bang.getCenterY());
        assertEquals(50, bang.getTop());
        assertEquals(100, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

}
