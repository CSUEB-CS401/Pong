package edu.csueastbay.cs401.HanishPatel;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * this method gets random collision items for power ups
 *
 */

public class RandomObjects extends Circle implements Collidable {
    public static final double STARTING_SPEED=3.0;
    public final double fieldWidth;
    public final double fieldHeight;
    private String ID;
    private Double speed;
    private Double direction;

    /**
     * this method calls the Circle and sets width and height
     * @param fieldWidth
     * @param fieldHeight
     *
     */
    public RandomObjects(double fieldWidth,double fieldHeight)
    {
        super();
        this.fieldWidth=fieldWidth;
        this.fieldHeight=fieldHeight;
    }

    /**
     * this method sets up Collision
     * @param shape
     * @return new Collision
     */

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "POWERUPS",
                this.ID,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    /**
     * this method gets random collision items for power ups
     * @return ID
     */

    @Override
    public String getID() {
        return ID;
    }

    /**
     * this method gets random collision items for power ups
     * @return "POWERUPS" type
     */
    @Override
    public String getType() {
        return "POWERUPS";
    }
}
