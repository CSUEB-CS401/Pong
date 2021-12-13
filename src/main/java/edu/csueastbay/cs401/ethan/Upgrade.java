package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Collidable;
import edu.csueastbay.cs401.ethan.game.Collision;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/** Upgrades modify a {@link Ball} or its {@link Ball#owner owner} when struck */
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

    /** Upgrades just check for colliding Balls and, if found, call {@link Upgrade#upgrade(Ball, Paddle)} and remove
     * themselves from the game on success. */
    @Override
    public final void update(double delta) {
        for(Collision<Ball> collision : game.getCollisionsWithType(this, Ball.class)) {
            if(upgrade(collision.other(), collision.other().owner.get())) {
                game.remove(this);
            }
        }
    }

    /**
     * Applies the upgrade, if possible.
     * @param ball The colliding ball
     * @param owner The paddle in possession of the ball, if one exists
     * @return whether the upgrade was applied
     */
    protected abstract boolean upgrade(Ball ball, Paddle owner);

    /** SplitUpgrades divide colliding Balls into two */
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
