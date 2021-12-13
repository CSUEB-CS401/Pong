package edu.csueastbay.cs401.classic;

import edu.csueastbay.cs401.pong.Puckable;
import edu.csueastbay.cs401.pong.Puck;

import java.util.Random;

public class PuckFactory {
    private double fieldWidth;
    private double fieldHeight;

    public PuckFactory(double fieldWidth, double fieldHeight) {
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
    }
    public Puckable createPuck() {
        Random random = new Random();
        Puckable puck = null;

        switch (random.nextInt(2)) {
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
