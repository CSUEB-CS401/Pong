package edu.csueastbay.cs401.DlinPong;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * This class is for creating a Portal in the game
 */
public class Portal extends Rectangle implements Collidable {

    private final String id;

    /**
     * This is the constructor for Portal
     * @param id The name of a portal that would be created
     * @param x X axis value
     * @param y Y axis value
     * @param width Width of the portal
     * @param height Height of the portal
     */
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
