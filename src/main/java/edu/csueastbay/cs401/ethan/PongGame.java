package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Entity;
import edu.csueastbay.cs401.ethan.game.Game;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.SetChangeListener;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedHashMap;
import java.util.Map;

public class PongGame extends Game {

    private static final Map<String, KeyCode> controls;
    static {
        controls = new LinkedHashMap<>();
        controls.put("Player 1 Up",   KeyCode.W);
        controls.put("Player 1 Down", KeyCode.S);
        controls.put("Player 2 Up",   KeyCode.UP);
        controls.put("Player 2 Down", KeyCode.DOWN);
    }

    public final IntegerProperty p1Score, p2Score;

    private int ballCount;

    public PongGame() {
        super(1300, 860);

        p1Score = new SimpleIntegerProperty(0);
        p2Score = new SimpleIntegerProperty(0);

        getEntities().addListener((SetChangeListener<? super Entity>) change->{
            if(change.wasAdded() && change.getElementAdded() instanceof Ball) {
                ballCount++;
            } else if(change.wasRemoved() && change.getElementRemoved() instanceof Ball) {
                ballCount--;
                if(ballCount == 0) {
                    schedule(1, this::addBall);
                }
            }
        });


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

        temp = new Goal(new Rectangle(-50, 0, 50, height), p2Score);
        add(temp);

        temp = new Goal(new Rectangle(width, 0, 50, height), p1Score);
        add(temp);

        addBall();
        schedule(10, this::addRandomUpgrade);
        commit();
    }

    private void addBall() {
        Ball ball = new Ball(5, width/2, height/2);
        add(ball);
        schedule(1, ball::launch);
    }

    private void addRandomUpgrade() {
        Upgrade upgrade = new Upgrade.SplitUpgrade();
        upgrade.x = (0.33+0.33*Math.random())*bounds.getWidth();
        upgrade.y = (0.1+0.8*Math.random())*bounds.getHeight();
        add(upgrade);
        schedule(2+3*Math.random(), this::addRandomUpgrade);
    }
}
