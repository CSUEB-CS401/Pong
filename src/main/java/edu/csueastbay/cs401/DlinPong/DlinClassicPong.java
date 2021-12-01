package edu.csueastbay.cs401.DlinPong;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is used to add in mostly everything in the game
 *
 * @see Portal
 * @see SpeedBall
 * @see SubPaddle
 * @see PuckFactory
 * @see PongMenuController
 */
public class DlinClassicPong extends Game2{

    private double fieldHeight;
    private double fieldWidth;

    private PuckFactory puckFactory;

    Random random = new Random();
    double leftLocation = random.nextDouble(750);
    double rightLocation = random.nextDouble(750);
    double leftYLocation, rightYLocation;
    private AnchorPane field;

    /**
     * This is the function that adds our objects and pucks
     *
     * @param victoryScore A victory score that's in the game but is not used
     * @param fieldWidth This is the width of our field
     * @param fieldHeight This is the height of our field
     * @param field This is our field
     */
    public DlinClassicPong(int victoryScore, double fieldWidth, double fieldHeight, AnchorPane field) {
        super(victoryScore);
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.field = field;
        this.puckFactory = new PuckFactory(fieldWidth,fieldHeight);


        leftYLocation = this.fieldHeight - leftLocation;
        if(this.fieldHeight - leftLocation > 750) {leftYLocation = 750;}
        rightYLocation = this.fieldHeight - rightLocation;
        if(this.fieldHeight - rightLocation > 750) {rightYLocation = 750;}

        Toggle t = new Toggle();
        if(t.getToggle() == 1) {
            Portal leftportal = new Portal("Left Portal", (this.fieldWidth + 2) / 3, leftYLocation, 5, 50);
            addObject(leftportal);
            Portal rightportal = new Portal("Right Portal", (this.fieldWidth + 2) * 2 / 3, rightYLocation, 5, 50);
            addObject(rightportal);
        }

        Puckable2 puck = puckFactory.createPuck();
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

        double yRange = (this.fieldHeight - 150) / 4;
        double speedBallPlacement = 0;
        for(int i=0; i < 4; i++){
            speedBallPlacement += yRange;
            SpeedBall speedBall = new SpeedBall("SpeedBall",this.fieldWidth/2 - 5, speedBallPlacement, 10,30);
            addObject(speedBall);
        }

        double subY = this.fieldHeight/2;
        double sizeY = random.nextInt(100);
        if(random.nextInt(2) == 0){sizeY *= -1;}
        SubPaddle subPlayerOne = new SubPaddle(
                "Player 1 SubPaddle",
                this.fieldWidth/2 - 100,
                subY+sizeY,
                5,
                50,
                10,
                this.fieldHeight - 10);
        subPlayerOne.setFill(Color.RED);
        addObject(subPlayerOne);

        double sizeY2 = random.nextInt(100);
        if(random.nextInt(2) == 0){sizeY2 *= -1;}
        SubPaddle subPlayerTwo = new SubPaddle(
                "Player 2 SubPaddle",
                this.fieldWidth/2 + 100,
                subY+sizeY2,
                5,
                50,
                10,
                this.fieldHeight - 10);
        subPlayerTwo.setFill(Color.BLUE);
        addObject(subPlayerTwo);

        Paddle2 playerOne = new Paddle2(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        Paddle2 playerTwo = new Paddle2(
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

    /**
     * This function helps with our collisions with different objects and what should be done when said collision occurs in our game
     *
     * @param puck Our puck variable used in this function
     * @param collision
     */
    @Override
    public void collisionHandler(Puckable2 puck, Collision collision) {
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    newPuck();

                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    newPuck();
                }
                break;
            case "Paddle":
                double puckCenter = puck.getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                }
                else if (collision.getObjectID() == "Player 1 SubPaddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                puck.setDirection(angle);
                break;
            case "Portal":
                if (collision.getObjectID() == "Left Portal") {
                    if(puck.getDirection() > 90 && puck.getDirection() < 270) {
                        puck.setSpeed(puck.getSpeed() + 1);
                        puck.set(((this.fieldWidth + 2)*2/3) - 23, rightYLocation);
                    } else if(puck.getDirection() > 270 || puck.getDirection() < 90){
                        puck.setSpeed(puck.getSpeed() + 1);
                        puck.set(((this.fieldWidth + 2)*2/3) + 23, rightYLocation);
                    }
                } else if (collision.getObjectID() == "Right Portal") {
                    if(puck.getDirection() > 90 && puck.getDirection() < 270) {
                        puck.setSpeed(puck.getSpeed() + 1);
                        puck.set(((this.fieldWidth + 2)/3) - 23, leftYLocation);
                    } else if(puck.getDirection() > 270 || puck.getDirection() < 90){
                        puck.setSpeed(puck.getSpeed() + 1);
                        puck.set(((this.fieldWidth + 2)/3) + 23, leftYLocation);
                    }
                }
                break;
            case "SpeedBall":
                int temp = random.nextInt(4);
                if(temp == 0)
                    puck.setSpeed(puck.getSpeed() + 1);
                else if(temp == 1)
                    puck.setSpeed(puck.getSpeed() - 1);
                else if(temp == 2)
                    puck.setSpeed(puck.getSpeed() + .5);
                else
                    puck.setSpeed(puck.getSpeed() - .5);
                break;

        }
    }

    /**
     * This function allows us to remove any old puck that was used and add in a new one
     * Great use for the Puck Factory
     */
    public void newPuck(){
        ArrayList<Puckable2> pucks = getPucks();
        pucks.forEach((old_puck) -> {
            field.getChildren().remove((Node) old_puck);
        });
        clearPucks();

        Puckable2 puck = puckFactory.createPuck();
        puck.setID("Random");
        addPuck(puck);

        field.getChildren().add((Node)puck);
    }

    /**
     * This functions adds in the two portals that are used in the game
     */
    public void newPortal(){
        Portal leftportal = new Portal("Left Portal", (this.fieldWidth + 2) / 3, leftYLocation, 5, 50);
        addObject(leftportal);
        Portal rightportal = new Portal("Right Portal", (this.fieldWidth + 2) * 2 / 3, rightYLocation, 5, 50);
        addObject(rightportal);
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}
