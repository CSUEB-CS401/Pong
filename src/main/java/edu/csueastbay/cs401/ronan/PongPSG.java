package edu.csueastbay.cs401.ronan;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class PongPSG extends Game {

    private double fieldHeight;
    private double fieldWidth;
    private Image player1Image = new Image("file:src/main/resources/edu/csueastbay/cs401/ronan/images/messi.png");
    private Image player2Image = new Image("file:src/main/resources/edu/csueastbay/cs401/ronan/images/mbappe.png");
    private Boolean p1Bonus = false;
    private Boolean p2Bonus = false;
    private Boolean p1Malus = false;
    private Boolean p2Malus = false;


    public PongPSG(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        FootballBall ball = new FootballBall(this.fieldWidth, this.fieldHeight);
        ball.setID("PSG");
        addPuck(ball);

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

        Bonus bonus1 = new Bonus("bonus", this.fieldWidth/2, this.fieldHeight/5, 30);
        addObject(bonus1);
        Bonus bonus2 = new Bonus("bonus", this.fieldWidth/2, this.fieldHeight*4/5, 30);
        addObject(bonus2);

        Malus malus1 = new Malus("malus", this.fieldWidth/2, this.fieldHeight*2/5, 30);
        Malus malus2 = new Malus("malus", this.fieldWidth/2, this.fieldHeight*3/5, 30);
        addObject(malus1);
        addObject(malus2);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                0,
                (this.fieldHeight/2) - 50,
                100,
                150,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(new ImagePattern(player1Image));
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 100,
                (this.fieldHeight/2) - 50,
                100,
                150,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(new ImagePattern(player2Image));;
        addPlayerPaddle(2, playerTwo);

    }

    @Override
    public void collisionHandler(Puckable ball, Collision collision) {
//        System.out.println(puck.getDirection());
        switch(collision.getType()) {
            case "Wall":
                ball.setID("bounced");
                ball.setDirection(0 - ball.getDirection());
                break;
            case "Goal":
                ball.setID("noMove");
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    ball.reset();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    ball.reset();
                }
                p1Bonus = false;
                p1Malus = false;
                p2Bonus = false;
                p2Malus = false;
                break;
            case "Paddle":
                double puckCenter = ((FootballBall) ball).getCenterY();
                double angle;
                ball.setID("shot");
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                ball.setDirection(angle);
                break;
            case "Bonus":
                if(ball.getDirection() < 90 && ball.getDirection() > -90 && !p1Bonus){
                    addPointsToPlayer(1, 1);
                    p1Bonus = true;
                }
                else if((ball.getDirection() < -90 || ball.getDirection() > 90) && !p2Bonus){
                    addPointsToPlayer(2, 1);
                    p2Bonus = true;
                }
                break;
            case "Malus":
                if(ball.getDirection() < 90 && ball.getDirection() > -90 && !p1Malus){
                    addPointsToPlayer(1, -1);
                    p1Malus = true;
                }
                else if((ball.getDirection() < -90 || ball.getDirection() > 90) && !p2Malus){
                    addPointsToPlayer(2, -1);
                    p2Malus = true;
                }
        }
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}