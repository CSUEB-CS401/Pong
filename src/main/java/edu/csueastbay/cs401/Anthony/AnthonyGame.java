package edu.csueastbay.cs401.Anthony;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public abstract class AnthonyGame {
    private int playerOneScore;
    private AnthonyPaddle playOnePaddle;
    private int playerTwoScore;
    private AnthonyPaddle playTwoPaddle;
    private int victoryScore;
    private ArrayList<Collidable> objects;
    private ArrayList<Puckable> pucks;

    public AnthonyGame(int victoryScore) {
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

        Puckable puck = pucks.get(0);

        System.out.println( "Game: addPointsToPlayer: " + player );
        if (player == 1)
        {
            playerOneScore += value;

            System.out.println( "Game: player: " + player + " Points: " + playerOneScore );
            // ball changes every time a point is made
            if(playerOneScore % 1 == 0)
                ((AnthonyPuck) puck).changeColor();
            // ball changes every 5 points
            if ( playerOneScore % 5 == 0 ) {
                playOnePaddle.reducePaddleHeight();
                ((AnthonyPuck) puck).reduceRadius();
                ((AnthonyPuck) puck).increaseSpeed();
            }
        }
        else if (player == 2)
        {
            playerTwoScore += value;

            System.out.println( "Game: player: " + player + " Points: " + playerTwoScore );
            // ball changes every time a point is made
            if(playerOneScore % 1 == 0)
                ((AnthonyPuck) puck).changeColor();
            // ball changes every 5 points
            if ( playerTwoScore % 5 == 0 ){
                playTwoPaddle.reducePaddleHeight();
                ((AnthonyPuck) puck).reduceRadius();
                ((AnthonyPuck) puck).increaseSpeed();
                //((AnthonyPuck) puck).IncreaseRadius();
            }
        }
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
        else if (playerOneScore >= victoryScore) victor = 2;
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

    protected void addPlayerPaddle(int player, AnthonyPaddle paddle) {
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
            case A:
                playOnePaddle.moveUp();
                break;
            case Z:
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
            case A, Z:
                playOnePaddle.stop();
                break;
            case UP, DOWN:
                playTwoPaddle.stop();
                break;
        }
    }
}