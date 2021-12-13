package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Entity;
import edu.csueastbay.cs401.ethan.game.Game;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.SetChangeListener;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * PongGame has two {@link Paddle Paddles} and various {@link Ball Balls}, and adds {@link Upgrade Upgrades} to the
 * field intermittently.
 */
public class PongGame extends Game {

    /** The player's score */
    public final IntegerProperty p1Score, p2Score;

    /** The current number of balls in play */
    private int ballCount;

    public PongGame() {
        super(1300, 860);

        p1Score = new SimpleIntegerProperty(0);
        p2Score = new SimpleIntegerProperty(0);

        getEntities().addListener((SetChangeListener<Entity>) change->{
            // Count balls as they're added or removed, add one if the last ball was removed
            if(change.wasAdded() && change.getElementAdded() instanceof Ball) {
                ballCount++;
            } else if(change.wasRemoved() && change.getElementRemoved() instanceof Ball) {
                ballCount--;
                if(ballCount == 0) {
                    schedule(1, this::addBall);
                }
            }
        });


        // Create the Paddles
        Entity temp;
        temp = new Paddle("Player 1 Up", "Player 1 Down", Color.MAGENTA);
        temp.x = 50;
        temp.y = height/2;
        add(temp);

        temp = new Paddle("Player 2 Up", "Player 2 Down", Color.CYAN);
        temp.x = width-50;
        temp.y = height/2;
        temp.rotation = 180;
        add(temp);

        // Create the Goals
        temp = new Goal(new Rectangle(-50, 0, 50, height), p2Score);
        add(temp);

        temp = new Goal(new Rectangle(width, 0, 50, height), p1Score);
        add(temp);

        addBall();
        schedule(10, this::addRandomUpgrade);
    }

    /** Add a ball to the center of the screen, and launch it after a delay */
    private void addBall() {
        Ball ball = new Ball(5, width/2, height/2);
        add(ball);
        schedule(1, ball::launch);
    }

    /** Adds a random {@link Upgrade} to the game, and {@link Game#schedule(double, Runnable) schedules} the next call. */
    private void addRandomUpgrade() {
        // TODO more upgrade variety
        Upgrade upgrade = new Upgrade.SplitUpgrade();
        upgrade.x = (0.33+0.33*Math.random())*bounds.getWidth();
        upgrade.y = (0.1+0.8*Math.random())*bounds.getHeight();
        add(upgrade);
        schedule(2+3*Math.random(), this::addRandomUpgrade);
    }
}
