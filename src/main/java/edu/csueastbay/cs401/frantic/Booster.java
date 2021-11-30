package edu.csueastbay.cs401.frantic;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import edu.csueastbay.cs401.pong.Collidable;
/*
public class Booster extends Circle implements Collidable {

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Barrier",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }
}
*/