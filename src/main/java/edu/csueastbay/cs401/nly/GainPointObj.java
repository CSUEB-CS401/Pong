package edu.csueastbay.cs401.nly;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class GainPointObj extends Circle implements Collidable {

    public static final double STARTING_SPEED = 5.0;
    private final double fieldWidth;
    private final double fieldHeight;
    private Double radius;
    private String id;

    /**
     * This method is the main constructor
     * @param fieldWidth
     * @param fieldHeight
     */
    public GainPointObj(double fieldWidth, double fieldHeight) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }


    /**
     * This method will return the bounds of the shape
     * @param shape the object passed with said properties
     * @return the properties of the object shape
     */
    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Gain2",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    /**
     * This method will return the id of object
     * @return the id of object
     */
    @Override
    public String getID() { return id; }

    /**
     * This method will return the type of object
     * @return the string type of object
     */
    @Override
    public String getType() {
        return "Gain2";
    }
}