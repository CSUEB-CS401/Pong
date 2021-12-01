package edu.csueastbay.cs401.pong;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Goal extends Rectangle implements Collidable {
    private final String id;

    public Goal(String id, double x, double y, double width, double height){
        super(x, y, width, height);
        this.id = id;
    }

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

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getType() {
        return "Goal";
    }
}
