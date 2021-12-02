package edu.csueastbay.cs401.lbernard;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;

public class LucasPong extends Game {

    private double fieldHeight;
    private double fieldWidth;

    public LucasPong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        BonusablePuck puck = new BonusablePuck(this.fieldWidth, this.fieldHeight);
        puck.setID("Classic");
        addPuck(puck);

        // Adding the Bonus to the Pong
        SuperBonus PaddSize_Plus = new SuperBonus(this.fieldWidth, this.fieldHeight, "PaddSize_Plus", fieldWidth/2, 1*fieldHeight/6, Color.GREEN);
        addObject(PaddSize_Plus);

        // Adding the Bonus to the Pong
        SuperBonus PaddSize_Minus = new SuperBonus(this.fieldWidth, this.fieldHeight, "PaddSize_Minus", fieldWidth/2, 2*fieldHeight/6, Color.RED);
        addObject(PaddSize_Minus);

        // Adding the Bonus to the Pong
        SuperBonus Obstacle_Toggled = new SuperBonus(this.fieldWidth, this.fieldHeight, "Obstacle_Toggled", fieldWidth/2, 3*fieldHeight/6, Color.YELLOW);
        addObject(Obstacle_Toggled);

        // Adding the Bonus to the Pong
        SuperBonus WallsTP = new SuperBonus(this.fieldWidth, this.fieldHeight, "WallsTP", fieldWidth/2, 4*fieldHeight/6, Color.DARKBLUE);
        addObject(WallsTP);

        // Adding the Bonus to the Pong
        SuperBonus BallSize_Plus = new SuperBonus(this.fieldWidth, this.fieldHeight, "BallSize_Plus", fieldWidth/2, 5*fieldHeight/6, Color.ORANGE);
        addObject(BallSize_Plus);

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

        BonusablePaddle playerOne = new BonusablePaddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        BonusablePaddle playerTwo = new BonusablePaddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);

    }

    public void setPlayerLastHit(int i) {
        if(i == 1) {
            playOnePaddle.setLastHit(true);
            playTwoPaddle.setLastHit(false);
        }
        else if(i == 2) {
            playOnePaddle.setLastHit(false);
            playTwoPaddle.setLastHit(true);
        }
        System.out.println("incorrect player id given.");
    }

    public int getPlayerLastHit() {
        if(playOnePaddle.getLastHit() && !playTwoPaddle.getLastHit()) {
            return 1;
        }
        else if(!playOnePaddle.getLastHit() && playTwoPaddle.getLastHit()) {
            return 2;
        }
        return -1;
    }

    @Override
    public void collisionHandler(BonusPuckable puck, Collision collision) {
//        System.out.println(puck.getDirection());
        switch(collision.getType()) {
            case "SuperBonus":
                System.out.println(getPlayerLastHit());
                switch (collision.getObjectID()) {
                    case "PaddSize_Plus":
                        if(getPlayerLastHit() == 1) {
                            playOnePaddle.setSizeBonus();
                        }
                        else if(getPlayerLastHit() == 2) {
                            playTwoPaddle.setSizeBonus();
                        }
                        else {
                            System.out.println("Cannot identify the last player to hit the ball");
                        }
                        break;
                    case "PaddSize_Minus":
                        if(getPlayerLastHit() == 1) {
                            playOnePaddle.setSizeMalus();
                        }
                        else if(getPlayerLastHit() == 2) {
                            playTwoPaddle.setSizeMalus();
                        }
                        else {
                            System.out.println("Cannot identify the last player to hit the ball");
                        }
                        break;
                    case "WallsTP":
                        if(getPlayerLastHit() == 1) {
                            playOnePaddle.setMoveBonus(true);
                        }
                        else if(getPlayerLastHit() == 2) {
                            playTwoPaddle.setMoveBonus(true);
                        }
                        else {
                            System.out.println("Cannot identify the last player to hit the ball");
                        }
                        break;
                    case "BallSize_Plus":
                        puck.multiplyRadius(1.2);
                    break;
                    case "BallSize_Minus":
                        puck.multiplyRadius(0.8);
                        break;
                    default:
                }

            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(2, 1);
                    puck.reset();
                    playOnePaddle.resetPaddle();
                    playTwoPaddle.resetPaddle();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(1, 1);
                    puck.reset();
                    playOnePaddle.resetPaddle();
                    playTwoPaddle.resetPaddle();
                }
                break;
            case "Paddle":
                double puckCenter = ((BonusablePuck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    playOnePaddle.setLastHit(true);
                    playTwoPaddle.setLastHit(false);
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    playOnePaddle.setLastHit(false);
                    playTwoPaddle.setLastHit(true);
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                puck.setDirection(angle);
            default:
        }
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}
