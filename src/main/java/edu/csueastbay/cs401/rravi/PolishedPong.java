package edu.csueastbay.cs401.rravi;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;
import javafx.scene.media.AudioClip;
/**
 * PolishedPong extends Game class. This class handles how the game is run
 */
public class PolishedPong extends Game {

    private double fieldHeight;
    private double fieldWidth;
    /**
     * Initializes the field of the game
     *
     * @param  victoryScore
     * @param  fieldWidth
     * @param  fieldHeight
     */
    public PolishedPong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        PolishedPuck puck = new PolishedPuck(this.fieldWidth, this.fieldHeight);
        puck.setID("PolishedPong");
        addPuck(puck);

        Wall top = new Wall("Top Wall", 0, 0, this.fieldWidth, 10);
        top.setFill(Color.BLACK);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight - 10, this.fieldWidth, 10);
        bottom.setFill(Color.BLACK);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth - 10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.AQUA);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.AQUA);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.ORANGERED);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.GREEN);
        addPlayerPaddle(2, playerTwo);

    }

    /**
     * Handles collision of the game. Added sounds for each type of collision the puck may hit
     *
     * @param  puck
     * @param  collision
     */
    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
//        System.out.println(puck.getDirection());
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                playAudio("Wall");
                break;
            case "Goal":
                playAudio("Score");
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    puck.reset();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    puck.reset();
                }
                break;
            case "Paddle":
                playAudio("Hit");
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                puck.setDirection(angle);
                puck.setSpeed(puck.getSpeed() + 0.2);
        }
    }
    /**
     * Handles sound for the game
     *
     * @param  type
     */
    public void playAudio(String type){
        switch(type){
            case "Hit":
                AudioClip hitSound = new AudioClip(getClass().getResource("Hit.wav").toExternalForm());
                hitSound.play();
                break;
            case "Wall":
                AudioClip wallSound = new AudioClip(getClass().getResource("Wall.wav").toExternalForm());
                wallSound.play();
                break;
            case "Score":
                AudioClip scoreSound = new AudioClip(getClass().getResource("Score.wav").toExternalForm());
                scoreSound.play();
                break;
        }
    }
    /**
     * Returns random number based on the paddle that was hit
     */
    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }
}
