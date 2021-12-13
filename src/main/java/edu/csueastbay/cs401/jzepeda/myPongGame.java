package edu.csueastbay.cs401.jzepeda;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;

/**
 * Class For Basics Of Pong Game Attributes.
 */
public class myPongGame extends Game {

    private double fieldHeight;
    private double fieldWidth;

    /**
     * Initializes The Standard Classic Pong Game, With Minor Tweaks For My Version.
     * - Color Changed For Walls, Paddles, Goals.
     * - Code Provided By Professor.
     *
     * @param victoryScore  Score Needed For Victor.
     * @param fieldWidth    Width Of The Game Field.
     * @param fieldHeight   Height Of The Game Field.
     */
    public myPongGame(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("Classic");
        addPuck(puck);

        Wall top = new Wall("Top Wall", 0,100, this.fieldWidth, 10);
        top.setFill(Color.GRAY);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight -10, this.fieldWidth, 10 );
        bottom.setFill(Color.GRAY);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth -10, 110, 10, this.fieldHeight - 120);
        left.setFill(Color.LIGHTGRAY);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 110, 10, this.fieldHeight - 120);
        right.setFill(Color.LIGHTGRAY);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,

                // Bound Changed Due To New Menu ...
                110,
                this.fieldHeight - 10);
        playerOne.setFill(Color.SILVER);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                100,

                // Bound Changed Due To New Menu ...
                110,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.SILVER);
        addPlayerPaddle(2, playerTwo);

    }

    /**
     * Handler Method For Collisions Using An ID For Collidable Objects.
     * - Code Provided By Professor.
     *
     * @param puck      Starting Puck For The Game.
     * @param collision Collision Object For Determing Type/ID.
     */
    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    puck.reset();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    puck.reset();
                }
                break;
            case "Paddle":
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                puck.setDirection(angle);
                break;
        }
    }

    /**
     * Method Used For Determining Puck Trajectory When Colliding With Paddle.
     * - Code Provided By Professor
     *
     * @param a1    Top of Collision Object
     * @param a2    Bottom of Collision Object
     * @param b1    Degree Of Trajectory (?)
     * @param b2    Degree Of Trajectory (?)
     * @param s     Center Of Puck
     * @return
     */
    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }
}
