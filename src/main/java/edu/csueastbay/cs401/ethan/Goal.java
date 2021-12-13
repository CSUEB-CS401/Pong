package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Collidable;
import edu.csueastbay.cs401.ethan.game.Collision;
import edu.csueastbay.cs401.ethan.game.Entity;
import javafx.beans.property.IntegerProperty;
import javafx.scene.shape.Shape;

/**
 * Goals detect and remove Balls and update scores accordingly
 */
public class Goal extends Entity implements Collidable {
    IntegerProperty score;
    Shape shape;

    /**
     * Creates a goal with the given {@link Goal#getCollisionShape() shape} bound to the given {@link IntegerProperty score}.
     * @param shape the collision shape
     * @param score
     */
    public Goal(Shape shape, IntegerProperty score) {
        this.score = score;
        this.shape = shape;
    }

    @Override
    public Shape getCollisionShape() {
        return shape;
    }

    /** Goal update removes any balls which collide and increment the bound score */
    @Override
    public void update(double delta) {
        for(Collision<Ball> collision : game.getCollisionsWithType(this, Ball.class)) {
            game.remove(collision.other());
            score.set(score.get()+1);
        }
    }
}
