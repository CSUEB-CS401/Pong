package edu.csueastbay.cs401.ttruong;

import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class SpeedDebuffTest {
    SpeedDebuff debuff;

    @BeforeEach
    void setUp() {
        debuff = new SpeedDebuff(1300, 860);
    }

    @Test
    void getID() {
        assertEquals(null, debuff.getID(),
                "Should return null.");
    }

    @Test
    void getType() {
        assertEquals("NoSpeedBoost", debuff.getType(),
                "Should return a .getType of 'NoSpeedBoost'.");
    }

}
