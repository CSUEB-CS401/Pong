package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Collidable;
import edu.csueastbay.cs401.ethan.game.Collision;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public abstract class Upgrade extends NeonEntity implements Collidable {

    private final Circle shape;

    public Upgrade(double radius) {
        shape = new Circle(radius);
        shape.setVisible(false);
        addNode(shape);
    }

    @Override
    public final Shape getCollisionShape() {
        return shape;
    }

    @Override
    public final void update(double delta) {
        game.getCollisionsWithType(this, Ball.class).stream().map(Collision::other).forEach(ball -> {
            if(upgrade(ball, ball.owner.get())) {
                game.remove(this);
            }
        });
    }

    /**
     * Applies the upgrade, if possible.
     * @param ball The colliding ball
     * @param owner The paddle in possession of the ball, if one exists
     * @return whether the upgrade was applied
     */
    protected abstract boolean upgrade(Ball ball, Paddle owner);

    public static class SplitUpgrade extends Upgrade {

        public SplitUpgrade() {
            super(15);
            Shape visual = new Circle(15);
            bindStyle(visual);
            addNode(visual);

            visual = Shape.union(new Circle(-5, 0, 8), new Circle(5, 0, 8));
            visual.setFill(Color.WHITE);
            addNode(visual);
        }

        @Override
        protected boolean upgrade(Ball ball, Paddle owner) {
            double direction = Math.atan2(ball.dy, ball.dx);
            double speed = Math.sqrt(ball.dx*ball.dx + ball.dy*ball.dy);

            ball.dx = speed*Math.cos(direction-Math.PI/12);
            ball.dy = speed*Math.sin(direction-Math.PI/12);

            Ball newBall = new Ball(ball);
            newBall.dx = speed*Math.cos(direction+Math.PI/12);
            newBall.dy = speed*Math.sin(direction+Math.PI/12);
            ball.game.add(newBall);

            return true;
        }
    }

}
