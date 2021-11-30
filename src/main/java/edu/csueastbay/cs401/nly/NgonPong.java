package edu.csueastbay.cs401.nly;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;

import java.util.Random;

public class NgonPong extends Game {

    private double fieldH;
    private double fieldW;

    public NgonPong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.fieldH = fieldHeight;
        this.fieldW = fieldWidth;

        SizeablePuck puck = new SizeablePuck(this.fieldW, this.fieldH);
        puck.setID("Ngon");
        addPuck(puck);

        RandomObject L2 = new RandomObject(this.fieldW, this.fieldH);
        L2.setId("Lost 2");
        L2.setCenterX(725);
        L2.setCenterY(620);
        L2.setRadius(5);
        L2.setFill(Color.BROWN);
        addObject(L2);


        Wall top = new Wall("Top Wall", 0,0, this.fieldW, 10);
        top.setFill(Color.BLACK);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldH -10, this.fieldW, 10 );
        bottom.setFill(Color.BLACK);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldW -10, 10, 10, this.fieldH - 20);
        left.setFill(Color.TEAL);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldH - 20);
        right.setFill(Color.ORANGE);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldH/2) - 50,
                10,
                100,
                10,
                this.fieldH - 10);
        playerOne.setFill(Color.ORANGE);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldW - 50,
                (this.fieldH/2) - 50,
                10,
                100,
                10,
                this.fieldH - 10);
        playerTwo.setFill(Color.TEAL);
        addPlayerPaddle(2, playerTwo);

    }

    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
        System.out.println(puck.getDirection());
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                changeOnScore(puck);
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
                double puckCenter = ((SizeablePuck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                changeOnScore(puck);
                puck.setDirection(angle);
                break;
            case "Lost2":
                Random player = new Random();
                int p = player.nextInt(2);
                addPointsToPlayer(p, -2);
        }
    }

    public void changeOnScore(Puckable puck){
        int a = getPlayerScore(1);
        if (a % 2 == 0){
            ((SizeablePuck) puck).changeRadius(.9);
        }
        else{
            puck.setSpeed(puck.getSpeed() * 1.25);
        }
    }


    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}

// expanding puck on collision, reset when goal / center still the same check
// if x score, increase speed x times until goal check
// if x % 2 score, random shapes? or more pucks
// have background image check