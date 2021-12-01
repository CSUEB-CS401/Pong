package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

// a rectangle which shows how much boost your paddle has

/**
 * Gage is a rectangle that calculates its desired current size
 * @see FranticPong
 * @see Rectangle
 */
public class Gage extends Rectangle implements Collidable {
    public static final double GUAGE_SIZE = 20;
    private String id;

    /**
     * Constructor
     * @param id
     * @param x x coordinate
     * @param y y coordinate
     * @param width
     * @param height
     */
    public Gage(String id, double x, double y, double width, double height){
        super(x, y, width, height);
        this.id = id;
        setFill(Color.MAGENTA);
        setOpacity(0.75);
    }

    /**
     * @see edu.csueastbay.cs401.pong.Collidable
     * @see edu.csueastbay.cs401.pong.Collision
     * @param shape
     * @return collision
     */
    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "None",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    /**
     * get Id
     * @return String ID
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * get Type
     * @return String "Gage"
     */
    @Override
    public String getType() {
        return "Gage";
    }

    /**
     * sets width to a multiple of given parameter
     * @param x double
     */
    public void grow(double x){setWidth((x*GUAGE_SIZE)+1);}
}
