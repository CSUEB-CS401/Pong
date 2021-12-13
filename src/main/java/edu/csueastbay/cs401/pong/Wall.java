package edu.csueastbay.cs401.pong;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Main background of the scene
 */
public class Wall extends Rectangle implements Collidable{

    private final String id;

    /**
     * Constructor
     * @param id Id of the player
     * @param x X-axis in the 2D plane
     * @param y Y-axis in the 2D plane
     * @param width Width of entire plane
     * @param height Height of the entire plane
     */
    public Wall(String id, double x, double y, double width, double height){
        super(x, y, width, height);
        this.id = id;
    }

    /**
     * Check for collison between shapes
     * @param shape Shape to check collision for
     * @return new collision object
     */
    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Wall",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    /**
     * Gettter to getID of the object
     * @return id
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Getter to get type of obejct.
     * @return "Wall"
     */
    @Override
    public String getType() {
        return "Wall";
    }
}
