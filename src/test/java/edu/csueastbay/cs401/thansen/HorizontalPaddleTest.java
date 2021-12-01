package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HorizontalPaddleTest {
    HorizontalPaddle paddle;

    @BeforeEach
    void setUp() {
        paddle = new HorizontalPaddle("Test Paddle", 50, 10, 50, 10, 10, 200);
    }

    @Test
    void getID() {
        assertEquals("Test Paddle", paddle.getID(),
                "Should return the paddle ID.");
    }

    @Test
    void getType() {
        assertEquals("HorizontalPaddle", paddle.getType(),
                "Should return 'HorizontalPaddle' for the type.");
    }

    @Test
    void moveLeft() {
        paddle.moveLeft();
        paddle.move();
        assertEquals(45, paddle.getX(),
                "Should have a X of 45 after moving left once.");
        paddle.move();
        paddle.move();
        assertEquals(35, paddle.getX(),
                "Should have a X of 35 after moving left 3 times.");

    }

    @Test
    void moveRight() {
        paddle.moveRight();
        paddle.move();
        assertEquals(55, paddle.getX(),
                "Should have a X of 55 after moving right once.");
        paddle.move();
        paddle.move();
        assertEquals(65, paddle.getX(),
                "Should have a X of 65 after moving right 3 times.");
    }

    @Test
    void dontMoveOffLeft() {
        paddle.moveLeft();
        for (int i = 0; i < 20; i++) {
            paddle.move();
        }
        assertEquals(10, paddle.getX(),
                "Should not move off the left side of the field");
    }

    @Test
    void dontMoveOffRight() {
        paddle.moveRight();
        for (int i = 0; i < 20; i++) {
            paddle.move();
        }
        assertEquals(150, paddle.getX(),
                "Should not move off the right side of the field");
    }

    @Test
    void stop() {
        paddle.moveRight();
        paddle.move();
        paddle.stop();
        paddle.move();
        paddle.move();
        assertEquals(55, paddle.getX(),
                "Paddle should stop moving after stop is called.");
    }

    @Test
    void getCollision() {
        final Rectangle rect = new Rectangle(50, 10, 10, 10);
        final Collision bang = paddle.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("HorizontalPaddle", bang.getType());
        assertEquals("Test Paddle", bang.getObjectID());
        assertEquals(75, bang.getCenterX());
        assertEquals(15, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(20, bang.getBottom());
        assertEquals(50, bang.getLeft());
        assertEquals(100, bang.getRight());
    }

    @Test
    void getNoCollision() {
        final Rectangle rect = new Rectangle(50, 50, 10, 10);
        final Collision bang = paddle.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("HorizontalPaddle", bang.getType());
        assertEquals("Test Paddle", bang.getObjectID());
        assertEquals(75, bang.getCenterX());
        assertEquals(15, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(20, bang.getBottom());
        assertEquals(50, bang.getLeft());
        assertEquals(100, bang.getRight());
    }
}
