package edu.csueastbay.cs401.ggamata2011;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Pong2TheSequel extends Game {

    private double fieldHeight;
    private double fieldWidth;
    private int NumberofHits = 0;
    private int NumUpgradesOnField = 0;
    private ArrayList<Collidable> Upgrades = new ArrayList<>();

    UpgradeablePuck puck;
    UpgradeablePaddle playerOne;
    UpgradeablePaddle playerTwo;


    private boolean Upgradeobtained = false;
    private boolean UpgradeInPlay = true;





    //Adding SpeedItem into GameObjects List
    private UpgradeSpeed SpeedItem = new UpgradeSpeed("UpgradeSpeed", 500.0, 500.0, 80.0, 80.0, (double) this.fieldHeight - 30, (double) this.fieldHeight - 30, (double) this.fieldWidth - 100, (double) this.fieldWidth - 100);


    public Pong2TheSequel(int victoryScore, double fieldWidth, double fieldHeight) {


        super(victoryScore);

        addUpgrade(SpeedItem);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        puck = new UpgradeablePuck(this.fieldWidth, this.fieldHeight);
        puck.setID("Classic");
        addPuck(puck);

        Wall top = new Wall("Top Wall", 0, 0, this.fieldWidth, 10);
        top.setFill(Color.RED);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight - 10, this.fieldWidth, 10);
        bottom.setFill(Color.RED);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth - 10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.GREEN);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.GREEN);
        addObject(right);


        playerOne = new UpgradeablePaddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.WHITE);
        addPlayerPaddle(1, playerOne);

        playerTwo = new UpgradeablePaddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.WHITE);
        addPlayerPaddle(2, playerTwo);

    }

    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
//        System.out.println(puck.getDirection());
        switch (collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                NumberofHits++;
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    puck.reset();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    puck.reset();
                }
                NumberofHits++;
                break;
            case "Paddle":
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                //Increment Number of hits
                NumberofHits++;
                puck.setDirection(angle);
                break;
            case "UpgradeSpeed":
                Upgradeobtained = true;
                UpgradeInPlay = false;
                if(puck.getDirection() < 0)
                {
                    System.out.println("Player 1 Obtained Speed Upgrade!");
                    playerOne.SpeedBoost();
                }
                if(puck.getDirection() > 0)
                {
                    System.out.println("Player 2 Obtained Speed Upgrade!");
                    playerTwo.SpeedBoost();
                }

                break;


        }



    }

    @Override
    public void move()
    {
        super.move();
    }

    @Override
    public void checkCollision(Puckable puck)
    {
        super.checkCollision(puck);
        Upgrades.forEach((object) -> {
            Collision collision = object.getCollision((Shape)puck);
            if (collision.isCollided()) {
                collisionHandler(puck, collision);
            }
        });
    }



    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1) * (b2 - b1)) / (a2 - a1);
    }

    /**
     * Returns list of upgrades
     * @return
     */
    public ArrayList<Collidable> getUpgrades()
    {
        return (ArrayList<Collidable>)Upgrades.clone();
    }

    /**
     * object will be added to Upgrades List
     * @param object
     */
    public void addUpgrade(Collidable object)
    {
      Upgrades.add(object);

    }

    /**
     * Remove Upgrades from List
     * @param object
     */
    public void RemoveUpgrade(Collidable object)
    {
        Upgrades.remove(object);

    }

    public boolean UpgradeStatus()
    {

        return UpgradeInPlay;

    }





}



