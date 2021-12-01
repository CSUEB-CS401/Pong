package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


// basically a wall with a different ID
//It's invisible in the middle of the game area and is for detecting when the
//puck has passed the midway point to assist barriers

/**
 *  A wall with a different ID. Used to Signal to other classes
 *
 * @see FranticPong
 * @see edu.csueastbay.cs401.pong.Wall
 * @see Collidable
 */
public class Sensor extends Rectangle implements Collidable {

    private final String id;

    /**
     * Constructor
     * @param id Identifier
     * @param x  X coordinate
     * @param y  Y coordinate
     * @param width
     * @param height
     */
    public Sensor(String id, double x, double y, double width, double height){
        super(x, y, width, height);
        this.id = id;
    }

    /**
     * collision behavior
     * @see edu.csueastbay.cs401.pong.Collidable
     * @param shape
     * @return
     */
    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Sensor",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    /**
     * get function
     * @return the ID
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * get function
     * @return the type "Sensor"
     */
    @Override
    public String getType() {
        return "Sensor";
    }
}
