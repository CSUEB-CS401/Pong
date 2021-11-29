package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.pong.Puck;

import java.util.Random;

/**
 * Puck that can start in any direction.
 */
public class FourWayPuck extends Puck {
    public FourWayPuck(double fieldWidth, double fieldHeight) {
        super(fieldWidth, fieldHeight);
        reset();
    }

    @Override
    public void reset() {
        super.reset();
        final Random random = new Random();
        direction = random.nextDouble() * 360;
    }
}
