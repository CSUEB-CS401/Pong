package edu.csueastbay.cs401.ttruong;

import edu.csueastbay.cs401.pong.Puck;
import edu.csueastbay.cs401.pong.Puckable;

import java.util.Random;

/**
 * class that implements a switch case between
 * regular puck and square puck. the decision is
 * randomized so players can get both pucks at different
 * rates.
 */
public class PuckFactory {
    private double fieldWidth;
    private double fieldHeight;

    /**
     * PuckFactory constructor takes in
     * @param fieldWidth - width of the field
     * @param fieldHeight - height of the field
     */

    public PuckFactory(double fieldWidth, double fieldHeight) {
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
    }

    public Puckable createPuck() {
        Random random = new Random();
        Puckable puck = null;
        switch (random.nextInt(2)) { //randomly generate square or regular puck
            case 0:
                puck = new Puck(fieldWidth, fieldHeight);
                break;
            case 1:
                puck = new SquarePuck(fieldWidth, fieldHeight);
                break;
        }

        return puck;
    }
}
