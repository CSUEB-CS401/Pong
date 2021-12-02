package edu.csueastbay.cs401.vnguyen;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Used to create the scene with objects and their characteristics.
 * Handle collision for each type of objects
 */

public class ViPong extends MyGame {
    /**
     *This is the interface of the game.
     * Added moving object and how to handle them.
     * Added new features for paddles.
     */
    
    private double fieldHeight;
    private double fieldWidth;

    /**
     * Constructor to create the scene and elements
     * @param victoryScore
     * @param fieldWidth
     * @param fieldHeight
     */
    public ViPong(int victoryScore, double fieldWidth, double fieldHeight) {

        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("Vi");
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
        right.setFill(Color.DARKGREEN);
        addObject(right);

        //add moving object
        //the object will move horizontally and start at random x and y
        Random random = new Random();
        double randomX1 = random.nextDouble(110,(int)this.fieldHeight-110);
        double randomY1 = random.nextInt(110,(int)this.fieldHeight-110);


        MovingObject movingObject1 = new MovingObject(
                "myObject", randomX1, randomY1, 80, 10, 65, this.fieldWidth-60);
        movingObject1.setFill(Color.DARKORCHID);
        addMovingObject(1,(movingObject1));

        random = new Random();
        double randomY2 = random.nextDouble(110,(int)this.fieldHeight-110);
        double randomX2 = random.nextInt(10,(int)this.fieldWidth-110);
        while(randomY2>randomY1-20&&randomY2<randomY1+20)
        {
            randomY2 = random.nextInt(110,(int)this.fieldHeight-110);
            randomX2 = random.nextInt(10,(int)this.fieldWidth-110);
        }

        MovingObject movingObject2 = new MovingObject(
                "myObject", randomX2, randomY2, 80, 10, 65, this.fieldWidth-60);
        movingObject2.setFill(Color.DARKORCHID);
        addMovingObject(2,(movingObject2));

        MyPaddle playerOne = new MyPaddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        playerOne.setArcHeight(20.0d);
        playerOne.setArcWidth(20.0d);
        addPlayerPaddle(1, playerOne);

        MyPaddle playerTwo = new MyPaddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.DARKGREEN);
        playerTwo.setArcHeight(20.0d);
        playerTwo.setArcWidth(20.0d);
        addPlayerPaddle(2, playerTwo);
        playSound("src/main/java/edu/csueastbay/cs401/vnguyen/start.wav");

    }

    /**
     * Play the sound with parameter filePath
     */

    public void playSound(String filePath)
    {
        try {
            Sound audioPlayer = new Sound(filePath);

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }

    }

    /**
     * Handle collision
     * @param puck
     * @param collision
     */
    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
//        System.out.println(puck.getDirection());
        String bounceFilePath = "src/main/java/edu/csueastbay/cs401/vnguyen/bounce.wav";
        String scoredFilePath = "src/main/java/edu/csueastbay/cs401/vnguyen/bell.wav";
        switch(collision.getType()) {
            case "Wall":
                playSound(bounceFilePath);
                puck.setDirection(0 - puck.getDirection());

                break;
            case "Goal":
                playSound(scoredFilePath);
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    puck.reset();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    puck.reset();
                }

                break;
            case "Moving Object":
                playSound(bounceFilePath);
                Random random = new Random();
                double deltaDirection = random.nextDouble(-45,45);
                puck.setDirection(0 - puck.getDirection());


            case "Paddle":
                playSound(bounceFilePath);
                double puckCenter = ((Puck) puck).getCenterY();

                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    //if the player one is holding S, speed up the ball
                    if(playOnePaddle.getSpeedUpCollidedObj()) {
                        double newSpeed = playOnePaddle.getSpeed();
                       puck.setSpeed(playOnePaddle.getSpeed()+newSpeed/4);
                    }
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else  {
                    //if the player two is holding L, speed up the ball
                    if(playTwoPaddle.getSpeedUpCollidedObj()) {
                        double newSpeed = playTwoPaddle.getSpeed();
                        puck.setSpeed(playTwoPaddle.getSpeed()+newSpeed/4);
                    }
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                puck.setDirection(angle);

        }
    }

    /**
     * Map the direction
     * Return angle
     */
    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}
