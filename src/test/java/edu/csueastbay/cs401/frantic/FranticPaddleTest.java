package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


    class FranticPaddleTest {

        FranticPaddle testFranticPaddle;

        @BeforeEach
        void setUP() {
            testFranticPaddle = new FranticPaddle("Test Paddle", 10, 50, 10, 50, 10, 200);
        }



        @Test
        void getID() {
            assertEquals("Test Paddle", testFranticPaddle.getID(),
                    "Should return the paddle ID.");
        }

        @Test
        void getType() {
            assertEquals("Paddle", testFranticPaddle.getType(),
                    "Should return 'Paddle' for the type.");
        }



        @Test
        void moveUp() {
            testFranticPaddle.moveUp();
            testFranticPaddle.move();
            assertEquals(45, testFranticPaddle.getY(),
                    "Should have a Y of 45 after moving up once.");
            testFranticPaddle.move();
            testFranticPaddle.move();
            assertEquals(35, testFranticPaddle.getY(),
                    "Should have a Y of 35 after moving up 3 times.");

        }

        @Test
        void moveDown() {
            testFranticPaddle.moveDown();
            testFranticPaddle.move();
            assertEquals(55, testFranticPaddle.getY(),
                    "Should have a Y of 55 after moving down once.");
            testFranticPaddle.move();
            testFranticPaddle.move();
            assertEquals(65, testFranticPaddle.getY(),
                    "Should have a Y of 65 after moving down 3 times.");
        }

        void dontMoveOffTop() {
            testFranticPaddle.moveUp();
            for (int i = 0; i < 12; i++) {
                testFranticPaddle.move();
            }
            assertEquals(10, testFranticPaddle.getY(),
                    "Should not move off the top of the field");
        }

        void dontMoveOffBottom() {
            testFranticPaddle.moveDown();
            for (int i = 0; i < 12; i++) {
                testFranticPaddle.move();
            }
            assertEquals(50, testFranticPaddle.getY(),
                    "Should not move off the bottom of the field");
        }

        @Test
        void stop() {
            testFranticPaddle.moveDown();
            testFranticPaddle.move();
            testFranticPaddle.stop();
            testFranticPaddle.move();
            testFranticPaddle.move();
            assertEquals(55, testFranticPaddle.getY(),
                    "Paddle should stop moving after stop is called.");
        }


        @Test
        void getCollision() {
            Rectangle rect = new Rectangle(10, 50, 10, 10);
            Collision bang = testFranticPaddle.getCollision(rect);
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
            Collision bang = testFranticPaddle.getCollision(rect);
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

        @Test
        void shrink(){
            testFranticPaddle.shrink();
            assertEquals(40, testFranticPaddle.getHeight());
            assertEquals(9,testFranticPaddle.getShrinkFactor());
        }

        @Test
        void resetSize(){
            testFranticPaddle.shrink();
            testFranticPaddle.resetHeight();
            assertEquals(50, testFranticPaddle.getHeight());
            assertEquals(10,testFranticPaddle.getShrinkFactor());
        }

    }

