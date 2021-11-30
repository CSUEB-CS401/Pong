package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.*;
import edu.csueastbay.cs401.frantic.Barrier.Owner;
import edu.csueastbay.cs401.pong.Sensor;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Random;



public class FranticGame {
    private int playerOneScore;
    private FranticPaddle playOnePaddle;
    private int playerTwoScore;
    private FranticPaddle playTwoPaddle;
    private int victoryScore;
    private ArrayList<Collidable> objects;
    private ArrayList<Puckable> pucks;
    private double fieldHeight;
    private double fieldWidth;
    private Owner lastHit;
    private int barrierCount;

    private Barrier barrier1;
    private Barrier barrier2;
    private Barrier barrier3;
    private Barrier barrier4;
    private Barrier barrier5;
    private Barrier barrier6;

    public FranticGame(int victoryScore, double fieldWidth, double fieldHeight) {
        this.victoryScore = victoryScore;
        this.objects = new ArrayList<>();
        this.pucks = new ArrayList<>();
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.barrierCount=1;
        this.lastHit=Owner.ORPHAN;


        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("Classic");
        addPuck(puck);

        Wall top = new Wall("Top Wall", 0,0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight -10, this.fieldWidth, 10 );
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Sensor middle = new Sensor("Sensor", (this.fieldWidth/2)-5, 0, 1, this.fieldHeight);
        middle.setFill(Color.TRANSPARENT);
        addObject(middle);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth -10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.RED);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.BLUE);
        addObject(right);

        barrier1 = new Barrier("Barrier",550, 400, 200, 2, fieldWidth, fieldHeight, 10 );
        barrier1.setFill(Color.CYAN);
        addObject(barrier1);

        barrier2 = new Barrier("Barrier",550, 400, 200, 2, fieldWidth, fieldHeight, 100 );
        barrier2.setFill(Color.CYAN);
        addObject(barrier2);

        barrier3 = new Barrier("Barrier",550, 400, 200, 2, fieldWidth, fieldHeight, 100 );
        barrier3.setFill(Color.CYAN);
        addObject(barrier3);

        barrier4 = new Barrier("Barrier",550, 400, 200, 2, fieldWidth, fieldHeight, 100 );
        barrier4.setFill(Color.CYAN);
        addObject(barrier4);

        barrier5 = new Barrier("Barrier",550, 400, 200, 2, fieldWidth, fieldHeight, 100 );
        barrier5.setFill(Color.CYAN);
        addObject(barrier5);

        barrier6 = new Barrier("Barrier",550, 400, 200, 2, fieldWidth, fieldHeight, 100 );
        barrier6.setFill(Color.CYAN);
        addObject(barrier6);

        FranticPaddle playerOne = new FranticPaddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        FranticPaddle playerTwo = new FranticPaddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);

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

    protected void addPlayerPaddle(int player, FranticPaddle paddle) {
        if (player == 1) {
            playOnePaddle = paddle;
            addObject(paddle);
        } else if (player == 2) {
            playTwoPaddle = paddle;
            addObject(paddle);
        }
    }

    public void collisionHandler(Puckable puck, Collision collision) {
//        System.out.println(puck.getDirection());
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                lastHit = Owner.ORPHAN;
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    puck.reset();
                    playTwoPaddle.resetHeight();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    puck.reset();
                    playOnePaddle.resetHeight();
                }
                resetBarrier();
                break;
            case "Paddle":
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                    playOnePaddle.shrink();
                    lastHit = Owner.PLAYER_ONE;
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    playTwoPaddle.shrink();
                    lastHit = Owner.PLAYER_TWO;
                }
                puck.setDirection(angle);
                break;
            case "Barrier":
                double direction = puck.getDirection();
                direction +=500 ;
                puck.setDirection(direction);
            case "Sensor":
                if (lastHit == Owner.ORPHAN) break;
                else shuffleBarrier(lastHit);
                lastHit = Owner.ORPHAN;
                break;
        }
    }

    public void shuffleBarrier(Owner owner){
        switch(barrierCount){
            case 1:
                barrier1.shuffle(owner);
                break;
            case 2:
                barrier2.shuffle(owner);
                break;
            case 3:
                barrier3.shuffle(owner);
                break;
            case 4:
                barrier4.shuffle(owner);
                break;
            case 5:
                barrier5.shuffle(owner);
                break;
            case 6:
                barrier6.shuffle(owner);
                break;
        }
        barrierCount = (barrierCount%6)+1;
    }

    public void resetBarrier(){
        barrier1.reset();
        barrier2.reset();
        barrier3.reset();
        barrier4.reset();
        barrier5.reset();
        barrier6.reset();
        return;
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }


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
