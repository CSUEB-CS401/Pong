package edu.csueastbay.cs401.pong;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public abstract class Game {
    private int playerOneScore;
    private Paddle playOnePaddle;
    private int playerTwoScore;
    private Paddle playTwoPaddle;
    private int victoryScore;
    private ArrayList<Collidable> objects;
    private ArrayList<Puckable> pucks;

    public Game(int victoryScore) {
        this.victoryScore = victoryScore;
        this.objects = new ArrayList<>();
        this.pucks = new ArrayList<>();
        this.playerOneScore = 0;
        this.playerTwoScore = 0;

    }


    public int getPlayerScore(int player) {
        if (player == 1) return playerOneScore;
        else if (player == 2) return playerTwoScore;
        return 0;
    }

    public void addPointsToPlayer(int player, int value) {
        if (player == 1)  playerOneScore += value;
        else if (player == 2) playerTwoScore += value;
    }

    public void setVictoryScore(int score) {
        victoryScore = score;
    }

    public int getVictoryScore() {
        return victoryScore;
    }

    public int getVictor() {
        int victor = 0;
        if (playerOneScore >= victoryScore) victor = 1;
        else if (playerTwoScore>= victoryScore) victor =2;
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

    public void addPuck(Puckable ball) {
        this.pucks.add(ball);
    }

    public ArrayList<Puckable> getPucks() {
        // Also shallow copy
        return (ArrayList<Puckable>) pucks.clone();
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
