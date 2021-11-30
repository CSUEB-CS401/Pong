package edu.csueastbay.cs401.StarWarsPong;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class PowerPointsGain extends Circle implements Collidable {

    public static final int STARTING_RADIOUS = 5;
    private final double fieldWidth;
    private final double fieldHeight;
    private String id;


    public PowerPointsGain(double fieldWidth, double fieldHeight) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "PointsGain",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getType() {
        return "Size";
    }
}
