package edu.csueastbay.cs401.nly;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class RandomObject extends Circle implements Collidable {

    public static final double STARTING_SPEED = 5.0;
    private final double fieldWidth;
    private final double fieldHeight;
    private Double radius;
    private String id;
    private Double speed;
    private Double direction;

    public RandomObject(double fieldWidth, double fieldHeight) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }


    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Lost2",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    @Override
    public String getID() { return id; }

    @Override
    public String getType() {
        return "Lost2";
    }
}

//random object who will -2 from score of random