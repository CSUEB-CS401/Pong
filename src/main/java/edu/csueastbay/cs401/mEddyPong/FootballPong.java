package edu.csueastbay.cs401.mEddyPong;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;

public class FootballPong extends FootballGame {

    private double fieldHeight;
    private double fieldWidth;


    public FootballPong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("Classic");
        addPuck(puck);

        Wall top = new Wall("Top Wall", 0,0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight -10, this.fieldWidth, 10 );
        bottom.setFill(Color.BLACK);
        addObject(bottom);


        Goal left = new Goal("Player 1 Goal", this.fieldWidth -50, 295, 10, this.fieldHeight - 575);
        left.setFill(Color.RED);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 50, 295, 10, this.fieldHeight - 575);
        right.setFill(Color.BLUE);
        addObject(right);



        Paddle player1GoalPaddle = new Paddle(
                "Player 1 Paddle",
                5,
                (this.fieldHeight/2) - 400,
                10,
                800,
                10,
                this.fieldHeight - 10);
        player1GoalPaddle.setFill(Color.PINK);

        Paddle player2GoalPaddle = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth-10,
                (this.fieldHeight/2) - 400,
                10,
                800,
                10,
                this.fieldHeight - 10);
        player2GoalPaddle.setFill(Color.PINK);

        addPlayerPaddle(1, player1GoalPaddle, player2GoalPaddle);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                300,
                (this.fieldHeight/2) - 50,
                10,
                200,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);

        Paddle playerOneP2 = new Paddle(
                "Player 1 Paddle",
                75,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOneP2.setFill(Color.RED);
        addPlayerPaddle(1, playerOne, playerOneP2);
        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 300,
                (this.fieldHeight/2) - 50,
                10,
                200,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BLUE);

        Paddle playerTwo2 = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 75,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo2.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo, playerTwo2);

    }

    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
//        System.out.println(puck.getDirection());
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

        }
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}
