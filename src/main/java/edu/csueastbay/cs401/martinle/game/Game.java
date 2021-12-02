package edu.csueastbay.cs401.martinle.game;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Paddle;
import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import java.util.Random;

import java.util.ArrayList;

/**
*
* Game change to Health Point Based RPG style game
* player goals decrease Health Points at every hit
* player 2 is CPU with mirrored movements by random generated chance
* game has a game over status flag
* game has a victor record
*
 **/

public abstract class Game {
    private int playerOneHP;
    protected Paddle playOnePaddle;
    private int playerTwoHP;
    protected Paddle playTwoPaddle;
    private boolean isGameOver;
    private int victor;
    protected ArrayList<Collidable> objects;
    protected ArrayList<Puckable> pucks;

    public Game(boolean isGameOver) {
        this.isGameOver = isGameOver;
        this.objects = new ArrayList<>();
        this.pucks = new ArrayList<>();
        this.playerOneHP = 20;
        this.playerTwoHP = 20;
    }

    /**
     *  Game returns player Health Points of respective player
     * @param player
     * @return
     */
    public int getPlayerHP(int player) {
        if (player == 1) return playerOneHP;
        else if (player == 2) return playerTwoHP;
        return 0;
    }

    /**
    *
    * Hits to player decrease player or CPU Health points
    * when the other goal is collided with puck
    *
    * */
    public void hitToPlayer(int player, int value) {
        if (player == 1){
            playerOneHP -= value;
            checkGameStatus();
        }
        else if (player == 2){
            playerTwoHP -= value;
            checkGameStatus();
        }
    }

    /**
     * Game ends and pucks are cleared
     */
    public void gameEnd() {
        if(isGameOver){
            clearPucks();
        }else{
            System.err.printf("GAME END ? ERROR HAS OCCURED...");
        }
    }

    public void setGameStatus(boolean gameStatus) { this.isGameOver = gameStatus; }

    public boolean getGameStatus() {
        return isGameOver;
    }

    public void setVictor(int victorPlayer ) { this.victor = victorPlayer; }

    public int getVictor( ) { return this.victor; }

    /**
     * check game status, at every hit to a player/CPU
     * @return
     */
    public int checkGameStatus() {
        if (playerOneHP <= 0) {
            setVictor(2);
            setGameStatus(true);
            gameEnd();
        }
        else if (playerTwoHP <= 0) {
            setVictor(1);
            setGameStatus(true);
            gameEnd();
        }
        return 0;
    }

    public void addObject(Collidable object) {
        objects.add(object);
    }

    public ArrayList<Collidable> getObjects() {
        // Shallow copy so the object collection can not be accessed but
        // still breaks encapsulation because the objects in the collection
        // are accessible.
        return (ArrayList<Collidable>) objects.clone();
    }

    public void addPuck(Puckable ball) {this.pucks.add(ball);}

    public ArrayList<Puckable> getPucks() {
        // Also shallow copy
        return (ArrayList<Puckable>) pucks.clone();
    }


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

    public void checkCollision(Puckable puck) {
        objects.forEach((object) -> {
            Collision collision = object.getCollision((Shape)puck);
            if (collision.isCollided()) {
                collisionHandler(puck, collision);
            }
        });
    }

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
     * CPU (AKA player 2)
     * will move at a 50% chance the same direction
     * as player and announce decision in console
     * @param code
     * @return
     */
    public boolean moveCPU(KeyCode code) {
        Random rand = new Random();
        int upperbound = 100;
        int random_number = rand.nextInt(upperbound);
        if(random_number >= 50) {
            switch (code) {
                case W:
                    playTwoPaddle.moveUp();
                    System.out.println("CPU decided to move up");
                    break;
                case S:
                    playTwoPaddle.moveDown();
                    System.out.println("CPU decided to move down");
                    break;
            }
        }
        return true;
    }

    public abstract void collisionHandler(Puckable puck, Collision collision);


    /**
     * only player one can move
     * cpu (player 2) will move the same direction as player
     * based on random number chance generated at each move
     * @param code
     */
    public void keyPressed(KeyCode code) {
        switch (code) {
            case W:
                playOnePaddle.moveUp();
                moveCPU(code);
                break;
            case S:
                playOnePaddle.moveDown();
                moveCPU(code);
                break;
/*
            case I:
                playTwoPaddle.moveUp();
                break;
            case K:
                playTwoPaddle.moveDown();
                break;
 */
        }
    }

    public void keyReleased(KeyCode code) {
        switch (code) {
            case W, S:
                playOnePaddle.stop();
                break;
                /*
            case I, K:
                playTwoPaddle.stop();
                break;
                 */
        }
    }

}

