package edu.csueastbay.cs401.pong;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape;

/**
 * sets the background of the game consisting of 2 rectangle and a line.
 */
public class Goal extends Rectangle implements Collidable {
    private final String id;

    /**
     * Constructor for Goal
     * @param id id of the player
     * @param x x-axis
     * @param y y-axis
     * @param width
     * @param height
     */
    public Goal(String id, double x, double y, double width, double height){
        super(x, y, width, height);
        this.id = id;
    }

    /**
     * Checks if there is a collison between two shpaes
     * @param shape shape to check collision for
     * @return new collision object
     */
    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Goal",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    /**
     * Getter to getID/ String
     * @return id
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Setter to set id of a player
     * @return
     */
    @Override
    public String getType() {
        return "Goal";
    }
}
