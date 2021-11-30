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

    private ArrayList<Collidable> Upgrades = new ArrayList<>();

    UpgradeablePuck puck;
    UpgradeablePaddle playerOne;
    UpgradeablePaddle playerTwo;


    //Adding SpeedItem into GameObjects List
    private UpgradeSpeed SpeedItem;
    private UpgradeHeight HeightItem;

    private Boolean RequestSpeedSpawn = false;
    private Boolean RemoveSpeedSpawn = false;
    private Boolean RequestHeightSpawn = false;
    private Boolean RemoveHeightSpawn = false;

    private Boolean PaddleOneHit = false;
    private Boolean PaddleTwoHit = false;




    public Pong2TheSequel(int victoryScore, double fieldWidth, double fieldHeight) {

        super(victoryScore);
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        SpeedItem = new UpgradeSpeed(
                "UpgradeSpeed",
                500.0,
                500.0,
                40.0,
                40.0,
                (double) this.fieldHeight - 200,
                200.0,
                200.0,
                (double) this.fieldWidth - 200);

        HeightItem = new UpgradeHeight(
                "UpgradeHeight",
                250.0,
                250.0,
                40.0,
                40.0,
                (double) this.fieldHeight - 200,
                200.0,
                200.0,
                (double) this.fieldWidth - 200);





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
                PaddleOneHit = PaddleTwoHit = false;
                NumberofHits++;
                break;
            case "Paddle":
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                    PaddleOneHit = true;
                    PaddleTwoHit = false;
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    PaddleTwoHit = true;
                    PaddleOneHit = false;
                }
                //Increment Number of hits
                NumberofHits++;
                puck.setDirection(angle);
                break;
            case "UpgradeSpeed":
              if(GetSpeedState()) {
                  if (PaddleOneHit) {
                      System.out.println("Player 1 Obtained Speed Upgrade!");
                      playerOne.ModifySpeed(SpeedItem.SpeedModify());
                      RemoveSpeedUpgrade();
                  }
                  if (PaddleTwoHit) {
                      System.out.println("Player 2 Obtained Speed Upgrade!");
                      playerTwo.ModifySpeed(SpeedItem.SpeedModify());
                      RemoveSpeedUpgrade();


                  }
              }
                break;
            case "UpgradeHeight":
                if(GetHeightState()) {
                    if (PaddleOneHit) {
                        System.out.println("Player 1 Obtained Height Upgrade!");
                        playerOne.ModifyHeight(HeightItem.HeightModify());
                        RemoveHeightUpgrade();
                    }
                    if (PaddleTwoHit) {
                        System.out.println("Player 2 Obtained Height Upgrade!");
                        playerTwo.ModifyHeight(HeightItem.HeightModify());
                        RemoveHeightUpgrade();


                    }
                }
                break;



        }

        //Upgrades will Spawn every 4 hits
        if(NumberofHits%4 == 0)
        {
            if(!GetSpeedState())
            {
               AddSpeedUpgrade();
            }
            if(!GetHeightState())
            {
                AddHeightUpgrade();
            }
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
            Collision SpeedCollision = SpeedItem.getCollision((Shape)puck);
            Collision HeightCollision = HeightItem.getCollision((Shape)puck);
            if (SpeedCollision.isCollided())
            {
                collisionHandler(puck, SpeedCollision);
            }
            if(HeightCollision.isCollided())
            {
                collisionHandler(puck,HeightCollision);
            }

        }


    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1) * (b2 - b1)) / (a2 - a1);
    }



    //All my Extra Functions will be defined here
    public UpgradeSpeed getSpeedUpgrades()
    {
        return SpeedItem;
    }
    public UpgradeHeight getHeightUpgrades()
    {
        return HeightItem;
    }

    public void AddSpeedUpgrade()
    {
      if(!SpeedItem.InPlay)
      {
          SpeedItem.ResetPosition();
          RequestSpeedSpawn = true;
          SpeedItem.InPlay();
      }
    }

    public void AddHeightUpgrade()
    {
        if(!HeightItem.InPlay)
        {
            HeightItem.ResetPosition();
            RequestHeightSpawn = true;
            HeightItem.InPlay();
        }

    }

    public void RemoveSpeedUpgrade()
    {
        RemoveSpeedSpawn = true;
        SpeedItem.OutOfPlay();
    }

    public void RemoveHeightUpgrade()
    {
       RemoveHeightSpawn = true;
       HeightItem.OutOfPlay();
    }

    public Boolean GetSpeedState()
    {
        return SpeedItem.PlayState();
    }

    public Boolean GetHeightState()
    {
        return HeightItem.PlayState();
    }

    public Boolean SpawnSpeed()
    {
       Boolean TempReturn = RequestSpeedSpawn;
       RequestSpeedSpawn = false;
       return  TempReturn;
    }
    public Boolean RemoveSpeedSpawn()
    {
        Boolean TempReturn = RemoveSpeedSpawn;
        RemoveSpeedSpawn = false;
        return  TempReturn;
    }
    public Boolean SpawnHeight()
    {
        Boolean TempReturn = RequestHeightSpawn;
        RequestHeightSpawn = false;
        return  TempReturn;
    }
    public Boolean RemoveHeightSpawn()
    {
        Boolean TempReturn = RemoveHeightSpawn;
        RemoveHeightSpawn = false;
        return  TempReturn;
    }


}



