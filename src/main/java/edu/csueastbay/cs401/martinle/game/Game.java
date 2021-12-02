package edu.csueastbay.cs401.martinle.game;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Paddle;
import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public abstract class Game {
    private int playerOneHP;
    protected Paddle playOnePaddle;
    private int playerTwoHP;
    protected Paddle playTwoPaddle;
    private int victoryScore;
    protected ArrayList<Collidable> objects;
    protected ArrayList<Puckable> pucks;

    public Game(int victoryScore) {
        this.victoryScore = victoryScore;
        this.objects = new ArrayList<>();
        this.pucks = new ArrayList<>();
        this.playerOneHP = 20;
        this.playerTwoHP = 20;
    }

    public int getPlayerHP(int player) {
        if (player == 1) return playerOneHP;
        else if (player == 2) return playerTwoHP;
        return 0;
    }

    public void hitToPlayer(int player, int value) {
        if (player == 1)  playerOneHP -= value;
        else if (player == 2) playerTwoHP -= value;
    }

    public void setVictoryScore(int score) {
        victoryScore = score;
    }

    public int getVictoryScore() {
        return victoryScore;
    }

    public int getVictor() {
        int victor = 0;
        if (playerOneHP == 0) victor = 2;
        else if (playerTwoHP == 0) victor = 1;
        return victor;
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

    public abstract void collisionHandler(Puckable puck, Collision collision);

    public void keyPressed(KeyCode code) {
        switch (code) {
            case W:
                playOnePaddle.moveUp();
                break;
            case S:
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

    public void keyReleased(KeyCode code) {
        switch (code) {
            case W, S:
                playOnePaddle.stop();
                break;
            case I, K:
                playTwoPaddle.stop();
                break;
        }
    }

}

