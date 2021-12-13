package edu.csueastbay.cs401.mattsPong;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A pong game where you receive points for blocking pucks with your paddle. If you
 * collect 10 points blocking you can double the height of your paddle for 20 seconds.
 * Collect 20 points to have your paddle give a speed boost to all pucks it touches for 20 seconds.
 */
public class MattsGame extends Game {
    public long startTime;
    private long playerOneTime;
    private long playerTwoTime;
    private long playerOneTime2;
    private long playerTwoTime2;
    private boolean paddleOneYellow = false;
    private boolean paddleTwoYellow = false;
    private boolean paddleOneGreen = false;
    private boolean paddleTwoGreen = false;
    private double fieldHeight;
    private double fieldWidth;
    private int playerOneBlocks;
    private int playerTwoBlocks;
    private Paddle playOnePaddle;
    private Paddle playTwoPaddle;

    /**
     * This is the Constructor for class MattsGame.
     * @param victoryScore contains the victoryScore.
     * @param fieldWidth contains the width of the field.
     * @param fieldHeight contains the height of the field.
     */
    public MattsGame(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        // The starting time for the in-game timer.
        this.startTime = System.currentTimeMillis();
        this.playerOneBlocks = 0;
        this.playerTwoBlocks = 0;


        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
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

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);
        this.playOnePaddle = playerOne;

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);
        this.playTwoPaddle = playerTwo;
    }

    // Added a call to function playAudio in each case.
    // Case paddle calls function addBlockPointsToPlayer and if a paddle is yellow changes the state of the puck.
    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
        switch(collision.getType()) {
            case "Wall":
                playAudio("Wall");
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                playAudio("Goal");
                if (puck.getID() == "Powered Up") {
                    puck.setID("Classic");
                    puck.setSpeed(5.0);
                    ((Puck) puck).setFill(Color.WHITE);
                }
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    puck.reset();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    puck.reset();
                }
                break;
            case "Paddle":
                playAudio("Paddle");
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                    addBlockPointsToPlayer(1,1);
                    if (paddleOneYellow){
                        puck.setID("Powered Up");
                        puck.setSpeed(8.0);
                        ((Puck) puck).setFill(Color.YELLOW);
                    }
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    addBlockPointsToPlayer(2,1);
                    if (paddleTwoYellow){
                        puck.setID("Powered Up");
                        puck.setSpeed(8.0);
                        ((Puck) puck).setFill(Color.YELLOW);
                    }
                }
                puck.setDirection(angle);

        }
    }

    /**
     * Will make a paddle perform an in the game depending on the key pressed.
     * @param code contains the key pressed by user.
     */
    // Pressing W or O will set the paddles to Yellow .
    // Pressing Q or P will double the height of the paddles and change the color to green.
    @Override
    public void keyPressed(KeyCode code) {
        switch (code) {
            case E:
                this.playOnePaddle.moveUp();
                break;
            case D:
                this.playOnePaddle.moveDown();
                break;
            case I:
                this.playTwoPaddle.moveUp();
                break;
            case K:
                this.playTwoPaddle.moveDown();
                break;
            case W:
                if(playerOneBlocks >= 20) {
                    this.playOnePaddle.setFill(Color.YELLOW);
                    playerOneBlocks -= 20;
                    this.playerOneTime = System.currentTimeMillis();
                    paddleOneYellow = true;
                }
                break;
            case O:
                if(playerTwoBlocks >= 20){
                    this.playTwoPaddle.setFill(Color.YELLOW);
                    playerTwoBlocks -= 20;
                    this.playerTwoTime = System.currentTimeMillis();
                    paddleTwoYellow = true;
                }
                break;
            case Q:
                if(playerOneBlocks >=10){
                    if(!this.playOnePaddle.getFill().equals(Color.YELLOW)) {
                        this.playOnePaddle.setFill(Color.GREEN);
                    }
                    this.playOnePaddle.setHeight(200);
                    playerOneBlocks -= 10;
                    this.playerOneTime2 = System.currentTimeMillis();
                    paddleOneGreen = true;
                }
                break;
            case P:
                if(playerTwoBlocks >= 10){
                    if(!this.playTwoPaddle.getFill().equals(Color.YELLOW)) {
                        this.playTwoPaddle.setFill(Color.GREEN);
                    }
                    this.playTwoPaddle.setHeight(200);
                    playerTwoBlocks -= 10;
                    this.playerTwoTime2 = System.currentTimeMillis();
                    paddleTwoGreen = true;
                }
        }
    }

    /**
     * Reverts paddles back to base form after 20 seconds.
     */
    // If a paddle has a power-up checkPowerDuration will check if their time is up and revert
    // the paddle to base form.
    public void checkPowerUpDuration(){
        if(paddleOneYellow && getTime(playerOneTime) >= 20){
            this.playOnePaddle.setFill(Color.RED);
            paddleOneYellow = false;
        }
        if(paddleTwoYellow && getTime(playerTwoTime) >= 20) {
            this.playTwoPaddle.setFill(Color.BLUE);
            paddleTwoYellow = false;
        }
        if(paddleOneGreen && getTime(playerOneTime2) >= 20){
            this.playOnePaddle.setFill(Color.RED);
            this.playOnePaddle.setHeight(100);
            paddleOneGreen = false;
        }
        if(paddleTwoGreen && getTime(playerTwoTime2) >= 20){
            this.playTwoPaddle.setFill(Color.BLUE);
            this.playTwoPaddle.setHeight(100);
            paddleTwoGreen = false;
        }
    }


    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

    // Will return the current time that has elapsed since the game started in seconds.

    /**
     *
     * @param time contains the starting time of when an event occurred.
     * @return returns the elapsed time in seconds.
     */
    public long getTime(long time){
        long elapsedTime = System.currentTimeMillis() - time;
        return elapsedTime / 1000;
    }

    /**
     * Adds block points to a player.
     * @param player An integer value that can be 1 or 2 to select a player.
     * @param value The amount of points that are added to a player.
     */
    public void addBlockPointsToPlayer(int player, int value){
        if (player == 1){
            this.playerOneBlocks += value;
        }else if(player == 2){
            this.playerTwoBlocks += value;
        }
    }

    /**
     *
     * @param player An integer value that can be 1 or 2 to select a player.
     * @return returns the players total blocks.
     */
    public int getPlayerBlockScore(int player){
        if (player == 1){
            return this.playerOneBlocks;
        }else if(player == 2){
            return this.playerTwoBlocks;
        }
        return 0;
    }


    /**
     *
     * @param type A string that represents the object that will play audio.
     */
    public void playAudio(String type){
        switch(type){
            case "Wall":
                AudioClip wallSound = new AudioClip(getClass().getResource("PongWall.wav").toExternalForm());
                wallSound.play();
                break;
            case "Goal":
                AudioClip goalSound = new AudioClip(getClass().getResource("PongGoal3.wav").toExternalForm());
                goalSound.play();
                break;
            case "Paddle":
                AudioClip paddleSound = new AudioClip(getClass().getResource("PongPaddle.wav").toExternalForm());
                paddleSound.play();
                break;
        }
    }

    /**
     * Removes puck from puck ArrayList.
     * @param puck An object puck from data type Puckable.
     */
    public void removePuck(Puckable puck){
        pucks.remove(puck);
    }

    // All code bellow used for tests.
    public Paint getPaddleColor(int x){
        if (x == 1){
            return this.playOnePaddle.getFill();
        }else{
            return this.playTwoPaddle.getFill();
        }
    }

    public Paddle getPaddle(int x){
        if (x == 1){
            return this.playOnePaddle;
        }
        return this.playTwoPaddle;
    }

    public long getStartTime(String time){

        if(time == "startTime") {
            return startTime;
        }
        if(time == "playerOneTime") {
            return playerOneTime;
        }
        if(time == "playerTwoTime") {
            return playerTwoTime;
        }
        if(time == "playerOneTime2") {
            return playerOneTime2;
        }
        return playerTwoTime2;

    }
}
