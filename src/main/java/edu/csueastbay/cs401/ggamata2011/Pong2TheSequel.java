package edu.csueastbay.cs401.ggamata2011;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javafx.scene.shape.Shape;


/**
 * This is used to instantiate the Game Instance
 * @see Debuff
 * @see UpgradeablePaddle
 * @see UpgradeSpeed
 * @see UpgradeHeight
 */
public class Pong2TheSequel extends Game {

    private double fieldHeight;
    private double fieldWidth;
    private int NumberofHits = 0;
    private Boolean Victor = false;

    private ArrayList<Collidable> Upgrades = new ArrayList<>();

    //Instantiations of the puck and player one and two
    Puck puck;
    UpgradeablePaddle playerOne;
    UpgradeablePaddle playerTwo;

    //Adding SpeedItem into GameObjects List
    private UpgradeSpeed SpeedItem;
    private UpgradeHeight HeightItem;
    private Debuff DebuffItem;

    //Used for Seeing what Upgrade will be applied to
    private Boolean PaddleOneHit = false;
    private Boolean PaddleTwoHit = false;

    //Instantiations of Audio Clips for in game sounds
    AudioClip P1HitSound  = new AudioClip(getClass().getResource("P1Hit.wav").toExternalForm());
    AudioClip P2HitSound  = new AudioClip(getClass().getResource("P2Hit.wav").toExternalForm());
    AudioClip GoalSound = new AudioClip(getClass().getResource("Score.mp3").toExternalForm()) ;
    AudioClip WallHitSound = new AudioClip(getClass().getResource("WallPong.wav").toExternalForm());

    AudioClip SpeedBoostSound = new AudioClip(getClass().getResource("SpeedUpgradeSound.wav").toExternalForm());
    AudioClip HeightBoostSound = new AudioClip(getClass().getResource("HeightUpgradeSound.wav").toExternalForm());
    AudioClip DebuffSound = new AudioClip(getClass().getResource("fart-01.mp3").toExternalForm());

    MediaPlayer SickTunes = new MediaPlayer(new Media(getClass().getResource("MonkeyWarholDelay.mp3").toExternalForm()));


    /**
     * Default Constructor, sets victory score and spawns all other objects into play
     * @param victoryScore Sets Victory Score
     * @param fieldWidth Sets Default Field Width
     * @param fieldHeight Sets Default Field Height
     */
    public Pong2TheSequel(int victoryScore, double fieldWidth, double fieldHeight) {

        super(victoryScore);
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        //Cycle Count of 10 used cause
        //indefinite will bleed over to next game
        SickTunes.stop();
        SickTunes.setCycleCount(10);
        SickTunes.play();

        //Instantiations of Upgrades and Debuffs xcord and ycord are arbitrary values
        //since they are randomized on spawn
        SpeedItem = new UpgradeSpeed(
                "UpgradeSpeed",
                250.0,
                250.0,
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

        DebuffItem = new Debuff(
                "Debuff",
                250.0,
                250.0,
                80.0,
                80.0,
                (double) this.fieldHeight - 200,
                200.0,
                200.0,
                (double) this.fieldWidth - 200);

        puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("ClassicPong");
        addPuck(puck);

        Wall top = new Wall("Top Wall", 0, 0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight - 10, this.fieldWidth, 10);
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth - 10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.PURPLE);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.BLUE);
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




    /**
     *Collision Handler, checks what object has been collided
     * and calls the appropriate behavior
     * @param puck What is being used to be collided with
     * @param collision Collision handles what puck intersecs with
     * @see Collision
     * @see Puckable
     *
     */
    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
//        System.out.println(puck.getDirection());
        switch (collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                WallHitSound.play();
                NumberofHits++;
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    //Checks for Victor, if the Victor is player 1 game will halt
                    if(getVictor() != 1)
                    {
                        puck.reset();

                    }
                    if(getVictor() == 1)
                    {
                        //Halt Game Assets
                        AllUpgradesOutOfPlay();
                        puck.reset();
                        puck.setSpeed(0);
                    }

                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    //Checks for Victor, if the Victor is player 2 game will halt
                    if(getVictor() != 2)
                    {
                        puck.reset();

                    }
                    if(getVictor() == 2)
                    {
                        //Halt Game assets
                        AllUpgradesOutOfPlay();
                        puck.reset();
                        puck.setSpeed(0);
                    }
                }
                GoalSound.play();
                PaddleOneHit = PaddleTwoHit = false;
                NumberofHits++;
                break;
            case "Paddle":
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                //Collision with paddle will set Either PaddleOnehit/PaddleTwohit
                //to true depending on which object it collided with
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                    P1HitSound.play();
                    PaddleOneHit = true;
                    PaddleTwoHit = false;
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    P2HitSound.play();
                    PaddleTwoHit = true;
                    PaddleOneHit = false;
                }
                //Increment Number of hits
                NumberofHits++;
                puck.setDirection(angle);
                break;
            case "UpgradeSpeed":
                //Upgrade checks if speedItem is in play, will then
                //see which paddle last hit the puck then modify speed
              if(SpeedItem.PlayState()) {
                  if (PaddleOneHit) {
                      System.out.println("Player 1 Obtained Speed Upgrade!");
                      playerOne.ModifySpeed(SpeedItem.SpeedModify());
                  }
                  if (PaddleTwoHit) {
                      System.out.println("Player 2 Obtained Speed Upgrade!");
                      playerTwo.ModifySpeed(SpeedItem.SpeedModify());
                  }
                  SpeedBoostSound.play();
              }
                break;
            case "UpgradeHeight":
                //Upgrade checks if HeightItem is in play, will then
                //see which paddle last hit the puck then modify speed
                if(HeightItem.PlayState()) {
                    if (PaddleOneHit) {
                        System.out.println("Player 1 Obtained Height Upgrade!");
                        playerOne.ModifyHeight(HeightItem.HeightModify());
                    }
                    if (PaddleTwoHit) {
                        System.out.println("Player 2 Obtained Height Upgrade!");
                        playerTwo.ModifyHeight(HeightItem.HeightModify());

                    }
                    HeightBoostSound.play();
                }

                break;
            case "Debuff":
                //Upgrade checks if HeightItem is in play, will then randomly
                //generate which debuff to use as well as which paddle hit last
                //to apply debuff to opposing opponent
                if(DebuffItem.PlayState()) {
                    if (PaddleOneHit)
                    {
                        System.out.println("Player 1 Debuffed Player 2");
                        //Generate Random Number to pick which debuff
                        if(DebuffItem.DebuffRandomizer() >= 5)
                        {
                            System.out.println("Player 2 Height Debuff");

                            playerTwo.ModifyHeight(DebuffItem.DebuffHeight());
                        }
                        else
                        {
                            System.out.println("Player 2 Speed Debuff");

                            playerTwo.ModifySpeed(DebuffItem.DebuffSpeed());
                        }

                    }
                    if(PaddleTwoHit)
                    {

                        System.out.println("Player 2 Debuffed Player 1");
                        //Generate Random Number to pick which debuff
                        if(DebuffItem.DebuffRandomizer() >= 5)
                        {
                            System.out.println("Player 1 Height Debuff");
                            playerOne.ModifyHeight(DebuffItem.DebuffHeight());
                        }
                        else
                        {
                            System.out.println("Player 1 Speed Debuff");
                            playerOne.ModifySpeed(DebuffItem.DebuffSpeed());
                        }
                    }
                    DebuffSound.play();
                }
                break;

        }

        //Upgrades will Spawn every 4 hits
        if(NumberofHits%4 == 0 && NumberofHits >= 4)
        {
               AddSpeedUpgrade();
               AddHeightUpgrade();
               AddDebuff();
        }

    }


    /**
     * Detects for Collisions in code with puck, if any puck collides with an item
     * it will hand it over to the collision handler
     * @param puck
     * @see UpgradeSpeed
     * @see UpgradeHeight
     * @see Debuff
     *
     */
    @Override
    public void checkCollision(Puckable puck)
    {
        super.checkCollision(puck);
            Collision SpeedCollision = SpeedItem.getCollision((Shape)puck);
            Collision HeightCollision = HeightItem.getCollision((Shape)puck);
            Collision DebuffCollision = DebuffItem.getCollision((Shape)puck);

            //Check Collision for all three items
            if (SpeedCollision.isCollided())
            {
                collisionHandler(puck, SpeedCollision);
            }
            if(HeightCollision.isCollided())
            {
                collisionHandler(puck,HeightCollision);
            }
            if(DebuffCollision.isCollided())
            {
                collisionHandler(puck,DebuffCollision);
            }

        }

    /**
     * Returns the range of the map
     * @param a1
     * @param a2
     * @param b1
     * @param b2
     * @param s
     * @return
     */
    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1) * (b2 - b1)) / (a2 - a1);
    }


    /**
     * This Function Returns a SpeedItem Upgrade for use to spawn in GameController
     * @return Returns a SpeedItem for use with GameController to spawn
     * @see UpgradeSpeed
     */
    //All my Extra Functions will be defined here
    public UpgradeSpeed getSpeedUpgrades()
    {
        return SpeedItem;
    }

    /**
     * This Function Returns a HeightItem Upgrade for use to spawn in GameController
     * @return Returns a HeightItem for use with Game Controller
     * @see UpgradeHeight
     */
    public UpgradeHeight getHeightUpgrades()
    {
        return HeightItem;
    }

    /**
     * This Function Returns a DebuffItem Upgrade for use to spawn in GameController
     * @return Returns a DebuffItem for use with Game Controller
     */
    public Debuff getDebuff()
    {
        return DebuffItem;
    }

    /**
     * Adds a DebuffItem Into play, calls Inplay to spawn object
     * @see Debuff
     */
    public void AddDebuff()
    {
        //if Item is not in play, reset to new position on field
        //then set Inplay to spawn on field
        if(!DebuffItem.InPlay)
        {
            DebuffItem.ResetPosition();
            DebuffItem.InPlay();
        }

    }

    /**
     * Adds a SpeedUpgrade Into play, calls Inplay to spawn object
     * @see UpgradeSpeed
     */
    public void AddSpeedUpgrade()
    {
        //if Item is not in play, reset to new position on field
        //then set Inplay to spawn on field
      if(!SpeedItem.InPlay)
      {
          SpeedItem.ResetPosition();
          SpeedItem.InPlay();
      }
    }

    /**
     * Adds a HeightUpgrade Into play, calls Inplay to spawn object
     * @see UpgradeHeight
     */
    public void AddHeightUpgrade()
    {
        //if Item is not in play, reset to new position on field
        //then set Inplay to spawn on field
        if(!HeightItem.InPlay)
        {
            HeightItem.ResetPosition();
            HeightItem.InPlay();
        }

    }

    /**
     * Sets All Items to Out of Play state, despawning all instances
     * @see UpgradeHeight
     * @see UpgradeSpeed
     * @see Debuff
     */
    public void AllUpgradesOutOfPlay()
    {
        HeightItem.OutOfPlay();
        SpeedItem.OutOfPlay();
        DebuffItem.OutOfPlay();
    }


}



