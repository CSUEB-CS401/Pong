package edu.csueastbay.cs401.DlinPong;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * This class is for creating the SpeedBall Object which will either increase or decrease the puck's speed
 */
public class SpeedBall extends Rectangle implements Collidable {

    private String id;

    public SpeedBall(String id, double x, double y, double width, double height){
        super(x,y,width,height);
        this.id = id;
        setFill(Color.BLACK);
    }

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "SpeedBall",
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
        return "SpeedBall";
    }
}
