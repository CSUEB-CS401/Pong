package edu.csueastbay.cs401.vnguyen;


import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import java.util.ArrayList;

/**
 * Used to create the game. Added Key S,L to speed up the puck
 *
 * @see Puckable
 * @see Collidable
 * @see Collision
 * @see KeyCode
 * @see Shape
 * @see ArrayList
 *
 */

public abstract class MyGame {
    /**
     * This is the game's activity
     */
    private int playerOneScore;
    protected MyPaddle playOnePaddle;
    private int playerTwoScore;
    protected MyPaddle playTwoPaddle;
    private int victoryScore;
    private ArrayList<Collidable> objects;
    private ArrayList<Puckable> pucks;
    private MovingObject movingObject1;//add new moving object
    private MovingObject movingObject2;//add new moving object

    /**
     * Constructor
     * @param victoryScore
     */
   public  MyGame(int victoryScore) {
        this.victoryScore = victoryScore;
        this.objects = new ArrayList<>();
        this.pucks = new ArrayList<>();
        this.playerOneScore = 0;
        this.playerTwoScore = 0;

    }

    /**
     * Getter for Player Score
     * @param player
     * @return score
     */
    public int getPlayerScore(int player) {
        if (player == 1) return playerOneScore;
        else if (player == 2) return playerTwoScore;
        return 0;
    }

    /**
     * Add points to player
     * @param player
     * @param value
     */
    public void addPointsToPlayer(int player, int value) {
        if (player == 1)  playerOneScore += value;
        else if (player == 2) playerTwoScore += value;
    }

    /**
     * Setter for victory score
     * @param score
     */
    public void setVictoryScore(int score) {
        victoryScore = score;
    }

    /**
     * Getter for victory Score
     * @return victory score
     */
    public int getVictoryScore() {
        return victoryScore;
    }

    /**
     * Getter for victor
     * @return
     */
    public int getVictor() {
        int victor = 0;
        if (playerOneScore >= victoryScore) victor = 1;
        else if (playerTwoScore >= victoryScore) victor = 2;
        return victor;
    }

    /**
     * Add objects to the game
     * @param object
     */
    public void addObject(Collidable object) {
        objects.add(object);
    }

    public ArrayList<Collidable> getObjects() {
        // Shallow copy so the object collection can not be accessed but
        // still breaks encapsulation because the objects in the collection
        // are accessible.
        return (ArrayList<Collidable>) objects.clone();
    }

    /**
     * Add pucks to the game
     * @param ball
     */
    public void addPuck(Puckable ball) {
        this.pucks.add(ball);
    }

    public ArrayList<Puckable> getPucks() {
        // Also shallow copy
        return (ArrayList<Puckable>) pucks.clone();
    }

    /**
     * Move the objects
     */
    public void move() {

        playOnePaddle.move();
        playTwoPaddle.move();
        movingObject1.move();
        movingObject2.move();

        for (Puckable puck : pucks) {
            checkCollision(puck);
            puck.move();
        }
    }


    /**
     * Check collision
     */
    public void checkCollision(Puckable puck) {
        objects.forEach((object) -> {
            Collision collision = object.getCollision((Shape)puck);
            if (collision.isCollided()) {
                collisionHandler(puck, collision);
            }
        });
    }

    /**
     * Add paddle to player
     * @param player
     * @param paddle
     */
    protected void addPlayerPaddle(int player, MyPaddle paddle) {
        if (player == 1) {
            playOnePaddle = paddle;
            addObject(paddle);
        } else if (player == 2) {
            playTwoPaddle = paddle;
            addObject(paddle);
        }
    }

    /**
     * Add moving object
     * @param player
     * @param movingObj
     */

    protected void addMovingObject(int player, MovingObject movingObj)
    {
        if (player == 1) {
            movingObject1 = movingObj;
            addObject(movingObject1);
        } else if (player == 2) {
            movingObject2 = movingObj;
            addObject(movingObject2);
        }
    }

    /**
     * Collision handler
     * @param puck
     * @param collision
     */
    public abstract void collisionHandler(Puckable puck, Collision collision);

    /**
     * Handle Key press
     * @param code
     */
    public void keyPressed(KeyCode code) {
        switch (code) {
            case E:
                playOnePaddle.moveUp();
                break;
            case D:
                playOnePaddle.moveDown();
                break;
            case S:
                playOnePaddle.setSpeedUpCollidedObj(true);
                break;
            case I:
                playTwoPaddle.moveUp();
                break;
            case K:
                playTwoPaddle.moveDown();
                break;
            case L:
                playTwoPaddle.setSpeedUpCollidedObj(true);
                break;


        }
    }

    /**
     * Handle key release
     * @param code
     */
    public void keyReleased(KeyCode code) {
        switch (code) {
            case E, D:
                playOnePaddle.stop();
                break;
            case S:
                playOnePaddle.setSpeedUpCollidedObj(false);
                break;
            case I, K:
                playTwoPaddle.stop();
                break;
            case L:
                playTwoPaddle.setSpeedUpCollidedObj(false);
                break;
        }
    }
}

