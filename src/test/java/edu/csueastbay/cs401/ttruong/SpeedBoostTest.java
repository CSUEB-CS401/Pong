package edu.csueastbay.cs401.ttruong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class SpeedBoostTest {
    SpeedBoost buff;

    @BeforeEach
    void setUp() {
        buff = new SpeedBoost(1300, 860);
    }

    @Test
    void getID() {
        assertEquals(null, buff.getID(),
                "Should return null.");
    }

    @Test
    void getType() {
        assertEquals("SpeedBoost", buff.getType(),
                "Should return a .getType of 'SpeedBoost'.");
    }

}
