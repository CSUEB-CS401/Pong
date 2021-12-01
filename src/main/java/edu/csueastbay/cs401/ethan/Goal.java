package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Collidable;
import edu.csueastbay.cs401.ethan.game.Collision;
import edu.csueastbay.cs401.ethan.game.Entity;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.shape.Shape;

public class Goal extends Entity implements Collidable {
    IntegerProperty score;
    Shape shape;
    public Goal(Shape shape, IntegerProperty score) {
        this.score = score;
        this.shape = shape;
    }

    @Override
    public Shape getCollisionShape() {
        return shape;
    }

    @Override
    public void update(double delta) {
        for(Collision collision : game.getCollisions(this)) {
            if(collision.other() instanceof Ball ball) {
                game.remove(ball);
                score.set(score.get()+1);
            }
        }
    }
}
