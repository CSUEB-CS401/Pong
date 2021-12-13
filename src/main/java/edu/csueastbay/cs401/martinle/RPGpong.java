package edu.csueastbay.cs401.martinle;

import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.martinle.game.Game;
import edu.csueastbay.cs401.pong.Goal;
import edu.csueastbay.cs401.pong.Paddle;
import edu.csueastbay.cs401.pong.Puck;
import edu.csueastbay.cs401.pong.Puckable;
import edu.csueastbay.cs401.pong.Wall;

import javafx.scene.paint.Color;

public class RPGpong extends Game {

    private double fieldHeight;
    private double fieldWidth;

    /**
    *
    * Player 2 paddle set to larger height size
    *
     */
    public RPGpong(boolean isGameOver, double fieldWidth, double fieldHeight) {
        super(isGameOver);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("Classic");
        addPuck(puck);


        Wall top = new Wall("Top Wall", 0,0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight -10, this.fieldWidth, 10 );
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth -10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.RED);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.BLUE);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                400,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BROWN);
        addPlayerPaddle(2, playerTwo);

    }

    /*
    *
    * Puck speed increases by 1 at every collision
    * after every puck reset
    *
     */
    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
//        System.out.println(puck.getDirection());
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    hitToPlayer(2, 1);
                    puck.reset();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    hitToPlayer(1, 3);
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
                puck.setSpeed(puck.getSpeed() + 1);
                break;
            case "Restart":
                puck.reset();
                break;

        }
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}