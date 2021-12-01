package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoosterTest {

    Booster testBooster;

    @BeforeEach
    void setUp() {
        testBooster = new Booster("Test Booster", 500, 500);
        testBooster.setCenterY(10);
        testBooster.setCenterX(10);
    }

    @Test
    void getId() {
        assertEquals ("Test Booster", testBooster.getID(),
                "Test Booster should have Id Test Booster");
    }
    @Test
    void getType() {
        assertEquals("Booster", testBooster.getType(),
                "Booster should have a type of 'Booster'");
    }

    @Test
    void getCollision() {
        Rectangle rect = new Rectangle(10, 10, 10, 10);
        Collision bang = testBooster.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Booster", bang.getType());
        assertEquals("Test Booster", bang.getObjectID());
    }

    @Test
    void getNoCollision() {
        Rectangle rect = new Rectangle(300, 300, 10, 10);
        Collision bang = testBooster.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Booster", bang.getType());
        assertEquals("Test Booster", bang.getObjectID());
    }

    @Test
    void getBoost(){
        assertEquals(false, testBooster.getIsBoosted(),"initial isBooster should be false");
        testBooster.setIsBoosted(true);
        assertEquals(true, testBooster.getIsBoosted(), "after setting isBooster should be true");
    }

    @Test
    void boostDouble(){
        assertEquals(3,testBooster.boost(2),"Boosting 2 should return 3");
    }
}
