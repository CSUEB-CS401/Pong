package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import edu.csueastbay.cs401.frantic.Barrier.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BarrierTest {

    Barrier testBarrier;

    @BeforeEach
    void setUp() {
        testBarrier = new Barrier("Test Barrier", 10, 10, 1000, 10, 100,100,10);
    }

    @Test
    void getID() {
        assertEquals("Test Barrier", testBarrier.getID(),
                "Test Barrier should have a id of 'Test Barrier'");
    }

    @Test
    void getType() {
        assertEquals("Barrier", testBarrier.getType(),
                "Barrier should have a type of 'Barrier'");
    }

    @Test
    void getCollision() {
        Rectangle rect = new Rectangle(10, 10, 10, 10);
        Collision bang = testBarrier.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Barrier", bang.getType());
        assertEquals("Test Barrier", bang.getObjectID());
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
        Collision bang = testBarrier.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Barrier", bang.getType());
        assertEquals("Test Barrier", bang.getObjectID());
        assertEquals(510, bang.getCenterX());
        assertEquals(15, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(20, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(1010, bang.getRight());
    }

    @Test
    void shuffleTest(){
        testBarrier.shuffle(Owner.PLAYER_ONE);
        assertNotEquals(10, testBarrier.getX());
        assertNotEquals(10,testBarrier.getY());
    }

    void resetTest(){
        testBarrier.shuffle((Owner.PLAYER_ONE));
        testBarrier.reset();
        assertEquals(10,testBarrier.getX());
        assertEquals(10,testBarrier.getY());
    }

}