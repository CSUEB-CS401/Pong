package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/*
My version of pong
1: Added bonus hit paddles. The gage is at the top of the screen, when completely full hitting the puck will double
    its default speed. Being still builds the gage and moving drains it. On a paddle hit all the gage is spent.
2: Added a boosting circle. It multiplies the speed of the puck once per puck hit. It spawns in the corners and moves
    inward until it reaches the edge of the play area.
3: Added moving barriers that bounce the puck unpredictably. The barriers will move when the puck passes the middle of
    the game area, and will always move to the side of the field of the player who last hit the puck. On goal the
    barriers are removed except the two on the sides of the center
4: The paddles now shrink with each successful hit. They start shrinking fast but slow down as the paddle gets smaller.
    If your opponent scores your paddle is returned to its original size
 */

/**
 * My version of Pong. Faster paced with added difficulty
 * @see edu.csueastbay.cs401.pong.Game
 * @see edu.csueastbay.cs401.classic.ClassicPong
 */
public class FranticPong extends Game {
    private double fieldHeight;
    private double fieldWidth;
    private Barrier.Owner lastHit;
    private int barrierCount;
    private Booster booster;
    private Gage playOneGage;
    private Gage playTwoGage;
    private Barrier barrier1;
    private Barrier barrier2;
    private Barrier barrier3;
    private Barrier barrier4;
    private Barrier barrier5;
    private Barrier barrier6;
    private FranticPaddle playOnePaddle;
    private FranticPaddle playTwoPaddle;

    /**
     * Constructor
     * @param victoryScore
     * @param fieldWidth width of the game field
     * @param fieldHeight height of the game field
     */
    public FranticPong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.barrierCount = 1;
        this.lastHit = Barrier.Owner.ORPHAN;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        //All objects in the game are added here
        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("Frantic");
        addPuck(puck);

        booster = new Booster("Booster", this.fieldWidth, this.fieldHeight);
        addObject(booster);

        Wall top = new Wall("Top Wall", 0, 0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight - 10, this.fieldWidth, 10);
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Sensor middle = new Sensor("Sensor", (this.fieldWidth / 2) - 5, 0, 1, this.fieldHeight);
        middle.setFill(Color.TRANSPARENT);
        addObject(middle);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth - 10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.RED);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.BLUE);
        addObject(right);

        barrier1 = new Barrier("Barrier", 550, 100, 200, 2, fieldWidth, fieldHeight, 10);
        barrier1.setFill(Color.CYAN);
        addObject(barrier1);

        barrier2 = new Barrier("Barrier", 550, fieldHeight - 100, 200, 2, fieldWidth, fieldHeight, 100);
        barrier2.setFill(Color.CYAN);
        addObject(barrier2);

        barrier3 = new Barrier("Barrier", 550, 100, 200, 2, fieldWidth, fieldHeight, 100);
        barrier3.setFill(Color.CYAN);
        addObject(barrier3);

        barrier4 = new Barrier("Barrier", 550, fieldHeight - 100, 200, 2, fieldWidth, fieldHeight, 100);
        barrier4.setFill(Color.CYAN);
        addObject(barrier4);

        barrier5 = new Barrier("Barrier", 550, 100, 200, 2, fieldWidth, fieldHeight, 100);
        barrier5.setFill(Color.CYAN);
        addObject(barrier5);

        barrier6 = new Barrier("Barrier", 550, fieldHeight - 100, 200, 2, fieldWidth, fieldHeight, 100);
        barrier6.setFill(Color.CYAN);
        addObject(barrier6);

        playOneGage = new Gage("One Gage", 400, 10, 100, 20);
        playOneGage.setFill(Color.MAGENTA);
        addObject(playOneGage);

        playTwoGage = new Gage("Two Gage", fieldWidth - 500, 10, 100, 20);
        playTwoGage.setFill(Color.MAGENTA);
        addObject(playTwoGage);

        FranticPaddle playerOne = new FranticPaddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10,
                0.05);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        FranticPaddle playerTwo = new FranticPaddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10,
                0.05);
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);
    }

    //the paddles have been changed to frantic paddles, this function is otherwise the same

    /**
     *  Sets paddles to players
     * @param player int player number
     * @param paddle created paddle
     */
    protected void addPlayerPaddle(int player, FranticPaddle paddle) {
        if (player == 1) {
            playOnePaddle = paddle;
            addObject(paddle);
        } else if (player == 2) {
            playTwoPaddle = paddle;
            addObject(paddle);
        }
    }

    //Overriding Move function to add the booster as well as the paddle gages

    /**
     * Determines what happens each framer of the game
     */
    @Override
    public void move() {
        playOnePaddle.move();
        playTwoPaddle.move();
        booster.move();
        playOneGage.grow(playOnePaddle.getTotalBonus());
        playTwoGage.grow(playTwoPaddle.getTotalBonus());
        for (Puckable puck : pucks) {
            checkCollision(puck);
            puck.move();
        }
    }

    //Needs to be overridden since paddles have been changed to frantic paddles

    /**
     *  determines what keys do
     * @param code key pressed
     */
    @Override
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
     * determines what key releases do
     * @param code key released
     */
    @Override
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


    /**
     * Determines the behavior of collisions with pucks detected based on the type of collision
     * @see edu.csueastbay.cs401.pong.Collidable
     * @see edu.csueastbay.cs401.pong.Puckable
     * @param puck the puck in the collision
     * @param collision the collision created by the collidable object
     */
    @Override
    public void collisionHandler(Puckable puck, Collision collision) {

        switch (collision.getType()) {

            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;

                //Divvies out points and additionally resets paddle size and barrier placement
            case "Goal":
                lastHit = Barrier.Owner.ORPHAN;
                booster.setIsBoosted(false);
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


                /*
                This section handles Boosts being reset after hitting paddles, paddles shrinking and
                barriers only spawning behind the puck after it passes through the center
                 */
            case "Paddle":
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                if (booster.getIsBoosted()) {
                    booster.setIsBoosted(false);
                }
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                    puck.setSpeed(((Puck) puck).STARTING_SPEED + playOnePaddle.getTotalBonus());
                    playOnePaddle.resetBonus();
                    playOnePaddle.shrink();
                    lastHit = Barrier.Owner.PLAYER_ONE;
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    puck.setSpeed(((Puck) puck).STARTING_SPEED + playTwoPaddle.getTotalBonus());
                    playTwoPaddle.resetBonus();
                    playTwoPaddle.shrink();
                    lastHit = Barrier.Owner.PLAYER_TWO;
                }
                puck.setDirection(angle);
                break;

                //barriers strange angle lets the bounce unpredictably
            case "Barrier":
                double direction = puck.getDirection();
                direction += 140;
                puck.setDirection(direction);

                //Sensor will only activate once after a paddle is hit
            case "Sensor":
                if (lastHit == Barrier.Owner.ORPHAN) break;
                else shuffleBarrier(lastHit);
                lastHit = Barrier.Owner.ORPHAN;
                break;

                //boosts speed of the puck when hitting booster
            case "Booster":
                booster.reset();
                if (booster.getIsBoosted()) {
                    break;
                }
                booster.setIsBoosted(true);
                puck.setSpeed(booster.boost(puck.getSpeed()));
                break;
        }
    }

    //Shuffles 1 barrier and increases barrier count, looping through all barriers sequentially

    /**
     * Cycles through barriers to be moved
     * @param owner the player who last hit the puck
     */
    public void shuffleBarrier(Barrier.Owner owner) {
        switch (barrierCount) {
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
        barrierCount = (barrierCount % 6) + 1;
    }

    //Simply returns Barriers to their original non-random positions

    /**
     * returns all barriers to starting positions
     */
    public void resetBarrier(){
        barrier1.reset();
        barrier2.reset();
        barrier3.reset();
        barrier4.reset();
        barrier5.reset();
        barrier6.reset();
        return;
    }

    /**
     * calculates angle of the puck after colliding with paddle
     * @param a1
     * @param a2
     * @param b1
     * @param b2
     * @param s
     * @return double angle value
     */
    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }
}
