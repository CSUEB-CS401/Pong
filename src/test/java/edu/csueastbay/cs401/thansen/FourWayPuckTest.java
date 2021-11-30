package edu.csueastbay.cs401.thansen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FourWayPuckTest {
    FakeRandom random;
    FourWayPuck puck;

    // TODO: Use an actual mocking library.
    private static class FakeRandom extends Random {
        private double nextDoubleValue;
        private int nextIntValue;

        public void setNextDouble(double value) {
            nextDoubleValue = value;
        }

        @Override
        public double nextDouble() {
            return nextDoubleValue;
        }

        public void setNextInt(int value) {
            nextIntValue = value;
        }

        @Override
        public int nextInt(int bound) {
            assertEquals(4, bound, "nextInt() must be called with bound=4.");
            return nextIntValue;
        }
    }

    @BeforeEach
    void setUp() {
        random = new FakeRandom();
        puck = new FourWayPuck(100, 100, random);
    }

    @Test
    void right() {
        random.setNextInt(0);
        random.setNextDouble(0);
        puck.reset();
        assertEquals(-40, puck.getDirection(), "Lower value out of range");

        random.setNextDouble(1);
        puck.reset();
        assertEquals(40, puck.getDirection(), "Upper value out of range");
    }

    @Test
    void up() {
        random.setNextInt(1);
        random.setNextDouble(0);
        puck.reset();
        assertEquals(-130, puck.getDirection(), "Lower value out of range");

        random.setNextDouble(1);
        puck.reset();
        assertEquals(-50, puck.getDirection(), "Upper value out of range");
    }

    @Test
    void left() {
        random.setNextInt(2);
        random.setNextDouble(0);
        puck.reset();
        assertEquals(140, puck.getDirection(), "Lower value out of range");

        random.setNextDouble(1);
        puck.reset();
        assertEquals(220, puck.getDirection(), "Upper value out of range");
    }

    @Test
    void down() {
        random.setNextInt(3);
        random.setNextDouble(0);
        puck.reset();
        assertEquals(50, puck.getDirection(), "Lower value out of range");

        random.setNextDouble(1);
        puck.reset();
        assertEquals(130, puck.getDirection(), "Upper value out of range");
    }
}
