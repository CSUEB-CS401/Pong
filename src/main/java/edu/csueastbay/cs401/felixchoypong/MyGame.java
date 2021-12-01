package edu.csueastbay.cs401.felixchoypong;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

/**
 * MyGame class, used to handle game mechanics
 */
public abstract class MyGame {
    private int playerOneScore;
    private Paddle playOnePaddle;
    private int playerTwoScore;
    private Paddle playTwoPaddle;
    private int victoryScore;
    private boolean playerOnePowerUp;
    private boolean playerTwoPowerUp;
    private ArrayList<Collidable> objects;
    private ArrayList<Puckable> pucks;
    private Sounds sound;

    /**
     * Constructor, sets all fields in the class to default values.
     * @param victoryScore
     */
    public MyGame(int victoryScore) {
        this.victoryScore = victoryScore;
        this.objects = new ArrayList<>();
        this.pucks = new ArrayList<>();
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.playerOnePowerUp = false;
        this.playerTwoPowerUp = false;
        this.sound = new Sounds();
    }

    /**
     * Setter for the playerOnePowerUp field
     * @param playerOnePowerUp keeps track of if the first player is currently using the power up
     */
    public void setPlayerOnePowerUp(boolean playerOnePowerUp){
        this.playerOnePowerUp = playerOnePowerUp;
    }

    /**
     * Setter for the playerTwoPowerUp field
     * @param playerTwoPowerUp keeps track of if the second player is currently using the power up
     */
    public void setPlayerTwoPowerUp(boolean playerTwoPowerUp){
        this.playerTwoPowerUp = playerTwoPowerUp;
    }

    /**
     * @return the playerOnePowerUp field
     */
    public boolean getPlayerOnePowerUp(){
        return playerOnePowerUp;
    }

    /**
     * @return the playerTwoPowerUp field
     */
    public boolean getPlayerTwoPowerUp(){
        return playerTwoPowerUp;
    }

    /**
     * @param player the player number (player 1 or 2)
     * @return the score of the player
     */
    public int getPlayerScore(int player) {
        if (player == 1) return playerOneScore;
        else if (player == 2) return playerTwoScore;
        return 0;
    }

    /**
     * Adds points to the specified player's score
     * @param player the current player
     * @param value the current score of the current player
     */
    public void addPointsToPlayer(int player, int value) {
        if (player == 1)  playerOneScore += value;
        else if (player == 2) playerTwoScore += value;
    }

    /**
     * Sets the maximum score at which the game ends at
     * @param score the score for which the game ends once a player's point total passes or equals it
     */
    public void setVictoryScore(int score) {
        victoryScore = score;
    }

    /**
     * @return the victory score
     */
    public int getVictoryScore() {
        return victoryScore;
    }

    /**
     * Gets the victor of the game
     * @return an integer based on who wins the game
     */
    public int getVictor() {
        int victor = 0;
        if (playerOneScore >= victoryScore) victor = 1;
        else if(playerTwoScore >= victoryScore) victor = 2;
        return victor;
    }

    /**
     * @param object a collidable object
     */
    public void addObject(Collidable object) {
        objects.add(object);
    }

    /**
     * @return a shallow copy of an arrayList of collidable objects
     */
    public ArrayList<Collidable> getObjects() {
        // Shallow copy so the object collection can not be accessed but
        // still breaks encapsulation because the objects in the collection
        // are accessible.
        return (ArrayList<Collidable>) objects.clone();
    }

    /**
     * Adds a ball to the puck ArrayList
     * @param ball an object that implements Puckable
     */
    public void addPuck(Puckable ball) {
        this.pucks.add(ball);
    }

    /**
     * Returns an arrayList used to store Puckable objects
     * @return a shallow copy of an arraylist
     */
    public ArrayList<Puckable> getPucks() {
        // Also shallow copy
        return (ArrayList<Puckable>) pucks.clone();
    }

    /**
     * Adds a paddle for each player to the arrayList
     * @param player the player specified
     * @param paddle a paddle for the player
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
     * Used to detect movement and collision for each paddle and puck
     */
    public void move() {

        playOnePaddle.move();
        playTwoPaddle.move();

        for (Puckable puck : pucks) {
            checkCollision(puck);
            puck.move();
        }
    }

    /**
     * Checks for collisions
     * @param puck a puckable object
     */
    public void checkCollision(Puckable puck) {
        objects.forEach((object) -> {
            Collision collision = object.getCollision((Shape)puck);
            if (collision.isCollided()) {
                if(playerOnePowerUp || playerTwoPowerUp){ //Check for active power up if activated on collision
                    puck.setSpeed(7.5);
                }
                collisionHandler(puck, collision);
            }
            else{ //Even if no collisions, check for if player one or two has a power up active
                if(playerOnePowerUp || playerTwoPowerUp){
                    puck.setSpeed(7.5);
                }
            }
        });
    }

    /**
     * Handles collisions
     * @param puck a puckable object
     * @param collision collision between two objects
     */
    public abstract void collisionHandler(Puckable puck, Collision collision);

    /**
     * Handles actions based on what key is pressed by either player
     * @param code the key pressed on the player's keyboard
     */
    public void keyPressed(KeyCode code) {
        switch (code) {
            case E:
                playOnePaddle.moveUp();
                break;
            case D:
                playOnePaddle.moveDown();
                break;
            case F: //player 1 powerup key
                if(playerOnePowerUp == false && (playerTwoScore - playerOneScore) >= 3){ //if player 1 is not on power up mode, and they are trailing by 3 or more points
                    playerOnePowerUp = true; //set to true
                    sound.playPowerUpSound();
                }
                else if(playerOnePowerUp == true){
                    //do nothing
                }
                else{
                    sound.playDeniedSound();
                }
                break;
            case I:
                playTwoPaddle.moveUp();
                break;
            case K:
                playTwoPaddle.moveDown();
                break;
            case J: //player2 powerup key
                if(playerTwoPowerUp == false && (playerOneScore - playerTwoScore) >= 3){ //if player 1 is not on power up mode, and they are trailing by 3 or more points
                    playerTwoPowerUp = true; //set to true
                    sound.playPowerUpSound();
                }
                else if(playerTwoPowerUp == true){
                    //do nothing
                }
                else{
                    sound.playDeniedSound();
                }
                break;
        }
    }

    /**
     * Detects when a key is released
     * @param code the key released on the player's keyboard
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
