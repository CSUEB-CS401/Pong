package edu.csueastbay.cs401.ronan;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Malus extends Circle implements Collidable{
    private String id;
    private Image malusImage = new Image("file:src/main/resources/edu/csueastbay/cs401/ronan/images/donna.png");

    public Malus(String id, double x, double y, double radius){
        setCenterX(x);
        setCenterY(y);
        setRadius(radius);
        setFill(new ImagePattern(malusImage));
        this.id = id;
    }

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Malus",
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
        return "Malus";
    }
}