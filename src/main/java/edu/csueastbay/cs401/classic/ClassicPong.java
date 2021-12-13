package edu.csueastbay.cs401.classic;

import edu.csueastbay.cs401.pong.*;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

import static javafx.application.Platform.exit;
import static javafx.application.Platform.setImplicitExit;



public class ClassicPong extends Game {
    private double fieldHeight;
    private double fieldWidth;
    private PuckFactory puckFactory;
    private AnchorPane field;
    private Timeline timeLine;

    @FXML
    private Label winOneText;
    @FXML
    private Label winTwoText;

    public ClassicPong(int victoryScore, double fieldWidth, double fieldHeight, AnchorPane field,
                       Label winOneText, Label winTwoText) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.field = field;
        this.winOneText = winOneText;
        this.winTwoText = winTwoText;
        this.puckFactory = new PuckFactory(fieldWidth, fieldHeight);

        Puckable puck = puckFactory.createPuck();
        puck.setID("Suraj's Pong");
        addPuck(puck);

        Wall top = new Wall("Top Wall", 0, 0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight - 10, this.fieldWidth, 10);
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth - 10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.BLUE);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.BLUE);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.PAPAYAWHIP);


        addPlayerPaddle(2, playerTwo);

        Random random = new Random();
        double randomX1 = random.nextDouble(110,(int)this.fieldHeight-110);
        double randomY1 = random.nextInt(110,(int)this.fieldHeight-110);


        addedObjects addedObject1 = new addedObjects(
                "myObject", randomX1, randomY1, 80, 10, 65, this.fieldWidth-60);
        addedObject1.setFill(Color.DARKORCHID);
        addMovingObject(addedObject1);

        random = new Random();
        double randomY2 = random.nextDouble(110,(int)this.fieldHeight-110);
        double randomX2 = random.nextInt(10,(int)this.fieldWidth-110);
        while(randomY2>randomY1-20&&randomY2<randomY1+20)
        {
            randomY2 = random.nextInt(110,(int)this.fieldHeight-110);
            randomX2 = random.nextInt(10,(int)this.fieldWidth-110);
        }

        addedObjects addedObject2 = new addedObjects(
                "myObject", randomX2, randomY2, 80, 10, 65, this.fieldWidth-60);
        addedObject2.setFill(Color.DARKORCHID);
        addMovingObject(2,(addedObject2));

    }

    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
        int diff;
//        System.out.println(puck.getDirection());
        switch (collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    newPuck();
                    puck.reset();
                    diff = getPlayerScore(1) - getPlayerScore(2);

                    //If the score difference is less than 2 than game is competitive increase the game speed
                    if (diff < 2) {
                        puck.setSpeed(25);
                    }
                    if (diff > 5) {
                        Paddle playerOne = new Paddle(
                                "Player 1 Paddle",
                                50,
                                (this.fieldHeight / 2) - 50,
                                10,
                                400,
                                10,
                                this.fieldHeight - 10);
                        playerOne.setFill(Color.ORANGE);
                        addPlayerPaddle(1, playerOne);

                    }
                     if(super.getVictor() == 1){
                        winOneText.setText("Player One Wins");
                        timeLine.stop();
                    }
                    else {
                        puck.reset();
                    }

                    } else if (collision.getObjectID() == "Player 2 Goal") {
                        addPointsToPlayer(2, 1);
                        newPuck();
                        if (super.getVictor() == 2) {
                            winTwoText.setText("Player Two Wins");
                            timeLine.stop();
                        } else {
                            puck.reset();
                        }
                    break;
                    }
            case "Paddle":
                double puckCenter = puck.getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                puck.setDirection(angle);
                break;

        }
    }

    /**
     * Timeline function to stop the game
     */

    public void newPuck() {

        ArrayList<Puckable> pucks = getPucks();

        pucks.forEach((old_puck) -> {
            field.getChildren().remove((Node) old_puck);
        });
//        clearPucks();
        Puckable puck = puckFactory.createPuck();
        puck.setID("Random");
//        clearPucks();
        addPuck(puck);
        field.getChildren().add((Node) puck);


    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1) * (b2 - b1)) / (a2 - a1);
    }
}

