package edu.csueastbay.cs401.pyae;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/***
 *
 *  Extends PGame class.
 *  Level difficulty and Sound fx is added:
 *      each time the ball bounces on paddle, the ball speed is increased by 1
 *      and the ball size is decreased by 1.
 *
 * ***/

public class PyaePong extends PGame {

    private double fieldWidth;
    private double fieldHeight;

    public PyaePong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        Ball ball = new Ball(this.fieldWidth, this.fieldHeight);
        ball.setID("Organic");
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
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);

    }

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
                    MediaInput();       // Bounce sound fx
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                    Difficulty(puck);   // Level difficulty

                    System.out.println(puck.getSpeed());
                } else {
                    MediaInput();       // Bounce sound fx
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    Difficulty(puck);   // Level difficulty
                    System.out.println(puck.getSpeed());
                }
                puck.setDirection(angle);
        }
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

    // Function: Ball Bounce sfx.
    public void MediaInput() {
        String audioPath = "src/main/resources/edu/csueastbay/cs401/pyae/SFX/paddle.wav";
        Media sfx = new Media(new File(audioPath).toURI().toString());
        MediaPlayer sfxPlayer = new MediaPlayer(sfx);
        sfxPlayer.setAutoPlay(true);
    }

    // Function: Difficulty: Increasing ball speed for each hit without scoring
    public void Difficulty(Puckable puck) {
        double speed = puck.getSpeed();
        puck.setSpeed(speed + 1);
        double size = ((Puck) puck).getRadius();
        ((Puck) puck).setRadius(size - 1);
    }

}
