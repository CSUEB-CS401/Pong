package edu.csueastbay.cs401.pong;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

/**
 * Main model of the Game. All the logic of the game goes in this class.
 * Establishes "has a" relationship with Paddle, Puck, Puckable, PuckFactory, SquarePuck Classes
 * @see Paddle
 * @see Puck
 * @see Puckable
 * @see edu.csueastbay.cs401.classic.PuckFactory
 * @see edu.csueastbay.cs401.classic.SquarePuck
 *
 */
public abstract class Game {
    /**
     * Variables to manipulate game variables
     */
    private int playerOneScore;
    protected Paddle playOnePaddle;
    private int playerTwoScore;
    protected Paddle playTwoPaddle;
    private int victoryScore;
    protected ArrayList<Collidable> objects;
    protected ArrayList<Puckable> pucks;

    /**
     * Constructor to instantiate Game Object
     * @param victoryScore Score needed to win the game
     */
    public Game(int victoryScore) {
        this.victoryScore = victoryScore;
        this.objects = new ArrayList<>();
        this.pucks = new ArrayList<>();
        this.playerOneScore = 0;
        this.playerTwoScore = 0;

        private
    }

    /**
     * Getter to get the playerScore of player 1 or 2
     * @param player Player to get the score of
     * @return  either Player 1 or 2's score or return 0 if game just started
     */



    public int getPlayerScore(int player) {
        if (player == 1) return playerOneScore;
        else if (player == 2) return playerTwoScore;
        return 0;
    }

    /**
     * Function to add points to the player
     * @param player player to add the score to
     * @param value points earned by the player
     */
    public void addPointsToPlayer(int player, int value) {
        if (player == 1)  playerOneScore += value;
        else if (player == 2) playerTwoScore += value;
    }

    /**
     * Setter to set the victory score
     * @param score score to win the game
     */
    public void setVictoryScore(int score) {
        victoryScore = score;
    }

    /**
     * Getter to get the victory score
     * @return victory score
     */
    public int getVictoryScore() {
        return victoryScore;
    }

    /**
     * Getter to get the winner
     * @return Player 1 or 2.
     */
    public int getVictor() {
        int victor = 0;
        if (playerOneScore >= victoryScore) victor = 1;

        else if(playerTwoScore >= victoryScore) victor = 2;

        else if (playerTwoScore >= victoryScore) victor = 2;
        return victor;
    }

    /**
     * Adds Collidable objects
     * @param object addded object
     */
    public void addObject(Collidable object) {
        objects.add(object);
    }

    /**
     * Gets Shallow copy of the objects
     * @return arraylist of copied objects
     */
    public ArrayList<Collidable> getObjects() {
        // Shallow copy so the object collection can not be accessed but
        // still breaks encapsulation because the objects in the collection
        // are accessible.
        return (ArrayList<Collidable>) objects.clone();
    }


    /**
     * Adds puck to the game
     * @param ball puck to add
     */
    public void addPuck(Puckable ball) {
        this.pucks.add(ball);
    }

    public void addPuck(Puckable ball) {this.pucks.add(ball);}


    /**
     * Gets shallow copy of arraylist of puckable objects
     * @return shallow copy of copied object.
      */
    public ArrayList<Puckable> getPucks() {
        // Also shallow copy
        return (ArrayList<Puckable>) pucks.clone();
    }


    /**
     * clears pucks
     */
    public void clearPucks() {
        pucks.clear();
    }

    /**
     * Detects collison of paddle and puck
     */


    public void clearPucks() {

        pucks.clear();
    }

    public void move() {

        playOnePaddle.move();
        playTwoPaddle.move();

        for (Puckable puck : pucks) {
            checkCollision(puck);
            puck.move();
        }
    }

    /**
     * Check for Collsion of objects.
     * @param puck a puckable object
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
     * Assigns player a paddle
     * @param player palyer to assign paddle to
     * @param paddle Paddle to assign.
     */
    protected void addPlayerPaddle(int player, Paddle paddle) {
        if (player == 1) {
            playOnePaddle = paddle;
            addObject(paddle);
        } else if (player == 2) {
            playTwoPaddle = paddle;
            addObject(paddle);
        }
    }

    /**
     * Abstract method to handle collision
     * @param puck puckable object
     * @param collision Collision between 2 objectse
     */

    public abstract void collisionHandler(Puckable puck, Collision collision);

    /**
     * Event handler to listen if E,D,I,K are pressed in keyboard
     * @param code keys to listen to
     */
    public void keyPressed(KeyCode code) {
        switch (code) {
            case E:
                playOnePaddle.moveUp();
                break;
            case D:
                playOnePaddle.moveDown();
                break;

            case I:
                playTwoPaddle.moveUp();
                break;
            case K:
                playTwoPaddle.moveDown();
                break;
        }
    }

    /**
     * Event handler to listen to key Release
     * @param code keys to listen to release of
     */
    public void keyReleased(KeyCode code) {
        switch (code) {
            case E, D:
                playOnePaddle.stop();
                break;
            case I, K:
                playTwoPaddle.stop();
                break;
        }
    }

}

