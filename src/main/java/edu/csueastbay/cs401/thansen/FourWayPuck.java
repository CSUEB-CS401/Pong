package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.pong.Puck;

import java.util.Random;

/**
 * Puck that can start in any direction.
 */
public final class FourWayPuck extends Puck {
    /**
     * Range of possible initialization angles centered within a 90-degree
     * quadrant of the game field.
     */
    public static final double QUADRANT_ANGLE_RANGE = 80.0;
    public static final double QUADRANT_OFFSET = (90.0 - QUADRANT_ANGLE_RANGE) / 2.0;

    private final Random random;

    public FourWayPuck(double fieldWidth, double fieldHeight, Random random) {
        super(fieldWidth, fieldHeight);
        this.random = random;
        reset();
    }

    @Override
    public void reset() {
        super.reset();
        // Not fully initialized yet.
        if (random == null) return;
        setDirection(
                // Note: The range/offset values here make it so that we don't
                // initialize the angle too close to the diagonal divider lines,
                // which could cause the puck to bounce between the corners
                // indefinitely.
                (random.nextDouble() * QUADRANT_ANGLE_RANGE) +
                        QUADRANT_OFFSET +
                        switch (random.nextInt(4)) {
                            case 0 -> -45.0; // Right: [-45, 45]
                            case 1 -> -135.0; // Up: [-135, -45]
                            case 2 -> 135.0; // Left: [135, 225]
                            case 3 -> 45.0; // Down: [45, 135]
                            default -> 0.0;
                        }
        );
    }
}
