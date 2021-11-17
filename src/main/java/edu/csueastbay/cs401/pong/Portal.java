package edu.csueastbay.cs401.pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Portal extends Rectangle implements Collidable{

    private final String id;

    public Portal(String id, double x, double y, double width, double height){
        super(x, y, width, height);
        this.id = id;
        setFill(Color.YELLOW);
    }

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Portal",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    @Override
    public String getID() { return id;}

    @Override
    public String getType() { return "Portal";}


}
