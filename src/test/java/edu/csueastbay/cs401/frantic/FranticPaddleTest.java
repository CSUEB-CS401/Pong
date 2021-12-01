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
            testFranticPaddle = new FranticPaddle("Test Paddle", 10, 50, 10, 50, 10, 200, 1);
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
            assertEquals(40, testFranticPaddle.getY(),
                    "Should have a Y of 40 after moving up once.");
            testFranticPaddle.move();
            testFranticPaddle.move();
            assertEquals(20, testFranticPaddle.getY(),
                    "Should have a Y of 20 after moving up 3 times.");

        }

        @Test
        void moveDown() {
            testFranticPaddle.moveDown();
            testFranticPaddle.move();
            assertEquals(60, testFranticPaddle.getY(),
                    "Should have a Y of 60 after moving down once.");
            testFranticPaddle.move();
            testFranticPaddle.move();
            assertEquals(80, testFranticPaddle.getY(),
                    "Should have a Y of 80 after moving down 3 times.");
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
            assertEquals(60, testFranticPaddle.getY(),
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
            assertEquals(40, testFranticPaddle.getHeight(),"After Shrink height should be 40");
            assertEquals(9,testFranticPaddle.getShrinkFactor(),"After shrink, shrink factor should be 9");
        }

        @Test
        void resetSize(){
            testFranticPaddle.shrink();
            testFranticPaddle.resetHeight();
            assertEquals(50, testFranticPaddle.getHeight(),"after reset, height should be 50");
            assertEquals(10,testFranticPaddle.getShrinkFactor(), "after reset shrink factor should be 10");
        }
        @Test
        void testBonus(){
            assertEquals(0,testFranticPaddle.getTotalBonus(), "bonus should start at 0");
            testFranticPaddle.stop();
            testFranticPaddle.move();
            assertNotEquals(0,testFranticPaddle.getTotalBonus(),"bonus should not be 0 after 1 move");
        }
        @Test
        void noBonus(){
            testFranticPaddle.stop();
            testFranticPaddle.move();
            testFranticPaddle.moveUp();
            testFranticPaddle.move();
            assertEquals(0,testFranticPaddle.getTotalBonus(),"bonus should be 0 after moving");
        }

    }

