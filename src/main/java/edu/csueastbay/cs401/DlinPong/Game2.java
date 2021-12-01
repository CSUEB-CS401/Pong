package edu.csueastbay.cs401.DlinPong;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.DlinPong.Paddle2;
import edu.csueastbay.cs401.DlinPong.Puckable2;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public abstract class Game2 {
    private int playerOneScore;
    protected Paddle2 playOnePaddle;
    private int playerTwoScore;
    protected Paddle2 playTwoPaddle;
    private int victoryScore;
    protected ArrayList<Collidable> objects;
    protected ArrayList<Puckable2> pucks;

    public Game2(int victoryScore) {
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
        if (player == 1)  {
            playerOneScore += value;
        }
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

    public void addPuck(Puckable2 ball) {this.pucks.add(ball);}

    public ArrayList<Puckable2> getPucks() {
        // Also shallow copy
        return (ArrayList<Puckable2>) pucks.clone();
    }

    public void clearPucks(){
        pucks.clear();
    }

    public void move() {

        playOnePaddle.move();
        playTwoPaddle.move();

        for (Puckable2 puck : pucks) {
            checkCollision(puck);
            puck.move();
        }
    }

    public void checkCollision(Puckable2 puck) {
        objects.forEach((object) -> {
            Collision collision = object.getCollision((Shape)puck);
            if (collision.isCollided()) {
                collisionHandler(puck, collision);
            }
        });
    }

    protected void addPlayerPaddle(int player, Paddle2 paddle) {
        if (player == 1) {
            playOnePaddle = paddle;
            addObject(paddle);
        } else if (player == 2) {
            playTwoPaddle = paddle;
            addObject(paddle);
        }
    }

    public abstract void collisionHandler(Puckable2 puck, Collision collision);

    public void keyPressed(KeyCode code) {
        switch (code) {
            case W:
                playOnePaddle.moveUp();
                break;
            case S:
                playOnePaddle.moveDown();
                break;
            case UP:
                playTwoPaddle.moveUp();
                break;
            case DOWN:
                playTwoPaddle.moveDown();
                break;
        }
    }

    public void keyReleased(KeyCode code) {
        switch (code) {
            case W, S:
                playOnePaddle.stop();
                break;
            case UP, DOWN:
                playTwoPaddle.stop();
                break;
        }
    }
}