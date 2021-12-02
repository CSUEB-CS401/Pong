package edu.csueastbay.cs401.HanishPatel;


import edu.csueastbay.cs401.pong.*;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import edu.csueastbay.cs401.pong.Game;
import java.util.Random;

import static javafx.application.Platform.exit;

public class ClassicPong extends Game {

    private double fieldHeight;
    private double fieldWidth;

    /**
     * This inherits Game and calls super for victory score
     * @param victoryScore
     * @param fieldWidth
     * @param fieldHeight
     */
    public ClassicPong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;


        /**
         * this method gets random collision items for power ups
         * @param this.fieldWidth
         * @param this.fieldHeight
         *
         */
        RandomObjects Random = new RandomObjects(this.fieldWidth,this.fieldHeight);
        Random.setId("Boost");
        Random.setCenterX(620);
        Random.setCenterY(300);
        Random.setRadius(5);
        Random.setFill(Color.LIGHTGREEN);
        addObject(Random);


        /**
         * this method gets puck collision items
         * @param this.fieldWidth
         * @param this.fieldHeight
         *
         */
        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("Classic");
        addPuck(puck);

        /**
         * this method gets Wall items for boundry
         * @param "Top Wall"
         * @param 0
         * @param 0
         * @param this.fieldWidth
         * @param 10
         *
         */
        Wall top = new Wall("Top Wall", 0,0, this.fieldWidth, 10);
        top.setFill(Color.LIGHTGREEN);
        addObject(top);

        /**
         * this method gets Wall items for boundry
         * @param "Bottom Wall"
         * @param 0
         * @param this.fieldHeight-10
         * @param this.fieldWidth
         * @param 10
         */
        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight -10, this.fieldWidth, 10 );
        bottom.setFill(Color.LIGHTGREEN);
        addObject(bottom);


        /**
         * this method gets Goal items for Score
         * @param this.fieldWidth - 10
         * @param 10
         * @param 10
         * @param this.fieldHeight - 20
         *
         */
        Goal left = new Goal("Player 1 Goal", this.fieldWidth -10, 10, 10, this.fieldHeight - 20);
        addObject(left);

        /**
         * this method gets Goal items for Score
         * @param 0
         * @param 10
         * @param 10
         * @param this.fieldHeight - 20
         *
         */

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.ORANGE);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.GREEN);
        addPlayerPaddle(2, playerTwo);
    }

    /**
     * this method handles collision
     * @param puck
     * @param collision
     * @
     *
     */
    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                /**
                 * This resets puck and paddle
                 */
                puck.reset();
                playOnePaddle.setWidth(10);
                playOnePaddle.setHeight(100);
                playTwoPaddle.setWidth(10);
                playTwoPaddle.setHeight(100);

                /**
                 * This function attempts to play audio
                 */
                //Music();

                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
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
            case "POWERUPS":

                /**
                 * This deals with PowerUps in game by taking
                 * random player values,booleans, and paddle width and height.
                 * It then sets the paddle size, speeds the ball or changes
                 * color based on the booleans.
                 * Random player is selected as the one getting a powerup.
                 *
                 */
                Random rand = new Random();
                int bool = rand.nextInt(6);
                int getPlayer = rand.nextInt(2);
                double getWidth=rand.nextInt(5)+10;
                double getHeight=rand.nextInt(20)+100;


                /**
                 * This changes paddle size
                 */
                if (bool == 1) {
                    if (getPlayer==1)
                    {
                        playOnePaddle.setWidth(getWidth);
                        playOnePaddle.setHeight(getHeight);
                    }
                    else
                    {
                        playTwoPaddle.setWidth(getWidth);
                        playTwoPaddle.setHeight(getHeight);
                    }
                }

                /**
                 * This changes ball speed
                 */
                if (bool == 2) {
                    puck.setSpeed(puck.getSpeed() * 1.15);
                }

                /**
                 * This changes paddle colors
                 */
                if (bool==3)
                {
                    float red=rand.nextFloat();
                    float green= rand.nextFloat();
                    float blue= rand.nextFloat();
                    if (getPlayer==1)
                    {
                        playOnePaddle.setFill(Color.color(red,green,blue));
                    }
                    if (getPlayer==2)
                    {
                        playTwoPaddle.setFill(Color.color(red,green,blue));
                    }
                }

                break;
        }
    }


    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

    /**
     * Plays audio file
     */

    // Credits to Alejandro Magaña
    public void Music()
    {
        AudioClip song=new AudioClip(getClass().getResource("src/main/java/edu/csueastbay/cs401/HanishPatel/music2.mp3").toExternalForm());
        song.play();
    }

    /**
     * This function takes an input and controls the paddle
     * If the user inputs 'Q' it will quit the program
     * @param code
     */

    @Override
    public void keyPressed(KeyCode code)
    {
        switch (code)
        {
            case W:
                playOnePaddle.moveUp();
                break;
            case S:
                playOnePaddle.moveDown();
                break;
            case UP:
                playTwoPaddle.moveUp();
                break;
            case DOWN:
                playTwoPaddle.moveDown();
                break;
            case Q:
                exit();
                break;

        }
    }

    /**
     * This stops the paddle from continuously moving
     * @param code
     */

    @Override
    public void keyReleased(KeyCode code) {
        switch (code) {
            case W, S:
                playOnePaddle.stop();
                break;
            case UP, DOWN:
                playTwoPaddle.stop();
                break;
        }
    }
}

