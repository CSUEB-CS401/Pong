package edu.csueastbay.cs401.psinha;
import edu.csueastbay.cs401.pong.*;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public abstract class MyGame {
    private int playerOneScore;
    protected MyPaddle playOnePaddle;
    private int playerTwoScore;
    protected MyPaddle playTwoPaddle;
    private int victoryScore;
    protected ArrayList<Collidable> objects;
    protected ArrayList<Puckable> pucks;
    /**
     * Returns a new Game
     * @param  victoryScore  integer that decides maximum points in the game
     * @return      a new Game
     * @see         Image
     */

    public MyGame(int victoryScore) {
        this.victoryScore = victoryScore;
        this.objects = new ArrayList<>();
        this.pucks = new ArrayList<>();
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        System.out.println("Running Pyush Pong!");


    }

    /**
     * Returns a player's score of a two player game
     * @param  player  integer that decides which player to evaluate
     * @return      the score of a player
     */
    public int getPlayerScore(int player) { // can work with this
        if (player == 1) return playerOneScore;
        else if (player == 2) return playerTwoScore;
        return 0;
    }
    /**
     * Adds points to player of a two player game
     * @param  player  integer that decides which player to evaluate
     * @return      void
     */
    public void addPointsToPlayer(int player, int value) {
        if (player == 1)  playerOneScore += value;
        else if (player == 2) playerTwoScore += value;
    }
    /**
     * Sets the victory score of the game
     * @param  victoryScore  integer that sets victoryScore
     * @return      nothing
     */

    public void setVictoryScore(int score) {
        victoryScore = score;
    }

    /**
     * Returns the victory score of the game
     * @return     the victory score of the game
     */

    public int getVictoryScore() {
        return victoryScore;
    }

    /**
     * Adds object to list of collidables
     * @param  object  that is added
     * @return      void
     */
    public void addObject(Collidable object) {
        objects.add(object);
    }

    /**
     * Returns list of collidables
     * @return    the list of collidables
     */

    public ArrayList<Collidable> getObjects() {
        // Shallow copy so the object collection can not be accessed but
        // still breaks encapsulation because the objects in the collection
        // are accessible.
        return (ArrayList<Collidable>) objects.clone();
    }
    /**
     * Adds object to list of puckables
     * @param  object  that is added
     * @return      void
     */
    public void addPuck(Puckable ball) {
        this.pucks.add(ball);
    }
    /**
     * Returns list of pucks
     * @return    the list of pucks
     */

    public ArrayList<Puckable> getPucks() {
        // Also shallow copy
        return (ArrayList<Puckable>) pucks.clone();
    }

    /**
     * Speeds up a puck to a max speed of 20
     * @param a a puckable object ( a puck )
     * @return    void
     */
    public void speedUp(Puckable a)
    {
        if (a.getSpeed() < 20) {
            a.setSpeed(a.getSpeed() + 1);
        }

    }
    /**
     * Slows down a puck
     * @param a a puckable object ( a puck )
     * @return    void
     */
    public void slowDown (Puckable a)
    {
        if (a.getSpeed() > 0 ) {
            a.setSpeed(a.getSpeed() - 1);
        }

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

    protected void addPlayerPaddle(int player, MyPaddle paddle) {
        if (player == 1) {
            playOnePaddle = paddle;
            addObject(paddle);
        } else if (player == 2) {
            playTwoPaddle = paddle;
            addObject(paddle);
        }
    }

    public abstract void collisionHandler(Puckable puck, Collision collision);
    /**
     * Controls of the game
     * Player one move : W , A , S , D
     * Player two Move: I , J, L, K
     * Speed up little puck: z
     * Slow down little puck: x
     * Paddle1 speedup/slowdown, T, Y
     * Paddle2 speedup/slowdown, G,H
     * @param code a keycode from keyboard
     * @return   void
     */
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
            case J:
                playTwoPaddle.moveLeft();
                break;
            case L:
                playTwoPaddle.moveRight();
                break;
           case A:
               playOnePaddle.moveLeft();
               break;
            case D:
                playOnePaddle.moveRight();
                break;
            case Z:
                this.speedUp(this.pucks.get(0));
                break;
            case X:
                this.slowDown(this.pucks.get(0));
                break;
            case T:
                playOnePaddle.speedUp();
                break;
            case Y:
                playOnePaddle.slowDown();
                break;
            case G:
                playTwoPaddle.speedUp();
                break;
            case H:
                playTwoPaddle.slowDown();
                break;

        }
    }

    public void keyReleased(KeyCode code) {
        switch (code) {
            case W,A,S,D:
                playOnePaddle.stop();
                break;
            case I, K, J, L:
                playTwoPaddle.stop();
                break;

        }
    }
}
