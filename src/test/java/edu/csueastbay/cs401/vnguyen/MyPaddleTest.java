package edu.csueastbay.cs401.vnguyen;

import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.vnguyen.MyPaddle;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyPaddleTest {

    MyPaddle testPaddle;

    @BeforeEach
    void setUP() {
        testPaddle = new MyPaddle("Test Paddle", 10, 50, 10, 50, 10, 200);
    }

    @Test
    void getID() {
        assertEquals("Test Paddle", testPaddle.getID(),
                "Should return the paddle ID.");
    }

    @Test
    void getType() {
        assertEquals("Paddle", testPaddle.getType(),
                "Should return 'Paddle' for the type.");
    }

    @Test
    void moveUp() {
        testPaddle.moveUp();
        testPaddle.move();
        assertEquals(45, testPaddle.getY(),
                "Should have a Y of 45 after moving up once.");
        testPaddle.move();
        testPaddle.move();
        assertEquals(35.0, testPaddle.getY(),
                "Should have a Y of 35 after moving up 3 times.");

    }
    @Test
    void moveUp21Times() {
        testPaddle.moveUp();
        testPaddle.move();
        for (int i =0;i<21;i++)
            testPaddle.move();
        assertEquals(10, testPaddle.getY(),
                "Should have a Y of 10 after moving up 20 times.");
    }

    @Test
    void move40Times() {
        testPaddle.moveUp();
        testPaddle.move();
        for (int i =0;i<21;i++)
            testPaddle.move();
        testPaddle.moveDown();
        for (int i =0;i<21;i++)
            testPaddle.move();
        assertEquals(138, (int)testPaddle.getY(),
                "Should have a Y of 138 after moving up 40 times.");

    }
    @Test
    void move70Times() {
        testPaddle.moveUp();
        testPaddle.move();
        for (int i =0;i<31;i++)
            testPaddle.move();
        testPaddle.moveDown();
        for (int i =0;i<41;i++)
            testPaddle.move();
        assertEquals(155, (int)testPaddle.getY(),
                "Should have a Y of 151 after moving up 70 times.");
        assertEquals(44, (int)testPaddle.getHeight(),"Should have a height of 44 after moving up 70 times.");

    }
    @Test
    void getSpeedTest() {
        testPaddle.moveUp();
        testPaddle.move();
        testPaddle.moveDown();
        testPaddle.move();
        assertEquals(5, (int)testPaddle.getSpeed(),
                "Should have speed of 5 after moving twice.");
    }
    @Test
    void SpeedUpCollidedObjTest() {
        testPaddle.setSpeedUpCollidedObj(false);
        assertEquals(false, testPaddle.getSpeedUpCollidedObj(),
                "Should have speed of 5 after moving twice.");
    }

    @Test
    void moveDown() {
        testPaddle.moveDown();
        testPaddle.move();
        assertEquals(55, testPaddle.getY(),
                "Should have a Y of 55 after moving down once.");
        testPaddle.move();
        testPaddle.move();
        assertEquals(65, testPaddle.getY(),
                "Should have a Y of 65 after moving down 3 times.");
    }

    @Test
    void dontMoveOffTop() {
        testPaddle.moveUp();
        for (int i = 0; i < 20; i++) {
            testPaddle.move();
        }
        assertEquals(10, testPaddle.getY(),
                "Should not move off the top of the field");
    }

    @Test
    void dontMoveOffBottom() {
        testPaddle.moveDown();
        for (int i = 0; i < 20; i++) {
            testPaddle.move();
        }
        assertEquals(150, testPaddle.getY(),
                "Should not move off the bottom of the field");
    }

    @Test
    void stop() {
        testPaddle.moveDown();
        testPaddle.move();
        testPaddle.stop();
        testPaddle.move();
        testPaddle.move();
        assertEquals(55, testPaddle.getY(),
                "Paddle should stop moving after stop is called.");
    }

    @Test
    void getCollision() {
        Rectangle rect = new Rectangle(10, 50, 10, 10);
        Collision bang = testPaddle.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Paddle", bang.getType());
        assertEquals("Test Paddle", bang.getObjectID());
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
        Collision bang = testPaddle.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Paddle", bang.getType());
        assertEquals("Test Paddle", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(75, bang.getCenterY());
        assertEquals(50, bang.getTop());
        assertEquals(100, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }
}
