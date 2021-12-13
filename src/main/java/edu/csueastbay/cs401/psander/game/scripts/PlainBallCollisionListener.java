package edu.csueastbay.cs401.psander.game.scripts;

import edu.csueastbay.cs401.psander.engine.audio.AudioManager;
import edu.csueastbay.cs401.psander.engine.math.Utility;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.physics.BoxCollider;
import edu.csueastbay.cs401.psander.engine.physics.CollisionInformation;
import edu.csueastbay.cs401.psander.engine.physics.CollisionListener;

import java.util.ArrayList;

public class PlainBallCollisionListener extends CollisionListener {

    // Range of reflection in degrees, should be less than 180.
    private final double _reflectionRange = 120;
    private final double _speedIncrement = 0.2;

    public void processCollisions(ArrayList<CollisionInformation> collisions) {
        var collider = getOwner().getComponent(BoxCollider.class);
        if (collider == null) return;
        var vel = collider.getVelocity();

        // Check if the ball is being squeezed on two sides.
        if (collisions.size() > 1) {

            var hasTopCollision = false;
            var hasBottomCollision = false;
            var hasLeftCollision = false;
            var hasRightCollision = false;

            for(var c : collisions) {
                if (c.location().hasTopComponent()) hasTopCollision = true;
                if (c.location().hasBottomComponent()) hasBottomCollision = true;
                if (c.location().hasLeftComponent()) hasLeftCollision = true;
                if (c.location().hasRightComponent()) hasRightCollision = true;
            }

            var pinchedVertically = hasTopCollision && hasBottomCollision;
            var pinchedHorizontally = hasLeftCollision && hasRightCollision;
            var boxedIn = pinchedVertically && pinchedHorizontally;
            if (boxedIn) {
                vel.set(0, 0);
                collider.setVelocity(vel);
                return;
            } else if (pinchedVertically) {
                vel.scale(1, 0);
                collider.setVelocity(vel);
                return;
            } else if (pinchedHorizontally) {
                vel.scale(0, 1);
                collider.setVelocity(vel);
                return;
            }
        }

        for(var i : collisions) {
            var other = i.other();
            var side = i.location();

            // Handling reflections
            var name = other.getOwner().getName();

            double centerAngle = 0.0, ballCenter = 0.0, angleMin = 0.0, angleMax = 0.0,
                    paddleMin = 0.0, paddleMax = 0.0;

            // Handling different types of sound hits
            var pos = getOwner().Transform().getWorldPosition();
            pos.add(new Vector2D(collider.getWidth()/2, collider.getHeight()/2));
            if (name == "wall")
                AudioManager.playSoundEffect("WallHit", pos);
            else if (name == "goal")
                AudioManager.playSoundEffect("GoalHit", pos);
            else if (name == "vertical paddle" || name == "horizontal paddle")
                AudioManager.playSoundEffect("PaddleHit", pos);

            // Speed increses
            if (name == "vertical paddle" || name == "horizontal paddle") {
                var v = collider.getVelocity();
                v.add(new Vector2D(_speedIncrement, _speedIncrement));
                collider.setVelocity(v);
            }

            // Now check for paddles to adjust the angle of reflection
            if (name == "vertical paddle" && side.hasHorizontalComponent()) {
                ballCenter = this.getOwner().Transform().Position().Y() + (collider.getHeight() / 2);

                // Pad the paddle's top and bottom with the ball height to cover when the ball is overlapped.
                var coord = other.getOwner().Transform().Position().Y();
                paddleMin = coord - (collider.getHeight() / 2);
                paddleMax = coord + other.getHeight() + (collider.getHeight() / 2);

                if (side.hasRightComponent()) {
                    centerAngle = 180;
                    angleMin = centerAngle + _reflectionRange / 2;
                    angleMax = centerAngle - _reflectionRange / 2;
                } else {
                    centerAngle = 0;
                    angleMin = centerAngle - _reflectionRange / 2;
                    angleMax = centerAngle + _reflectionRange / 2;
                }
            } else if (name == "horizontal paddle" && side.hasVerticalComponent()) {
                ballCenter = this.getOwner().Transform().Position().X() + (collider.getWidth() / 2);

                // Pad the paddle's top and bottom with the ball height to cover when the ball is overlapped.
                var coord = other.getOwner().Transform().Position().X();
                paddleMin = coord - (collider.getWidth() / 2);
                paddleMax = coord + other.getWidth() + (collider.getWidth() / 2);

                if (side.hasTopComponent()) {
                    centerAngle = 270;
                    angleMin = centerAngle - _reflectionRange / 2;
                    angleMax = centerAngle + _reflectionRange / 2;
                } else {
                    centerAngle = 90;
                    angleMin = centerAngle + _reflectionRange / 2;
                    angleMax = centerAngle - _reflectionRange / 2;
                }
            } else { // Do a regular reflect
                var otherVel = other.getVelocity();
                // Ball hits the top or bottom of the paddle while the paddle is moving
                // against it. We reflect the ball's direction and then add the paddle's velocity
                // to keep the two from being stuck together, as can happen if the paddle is moving
                // faster in the vertical direction than the ball is. (Basically keeps the paddle from
                // overtaking the ball.)
                if ((vel.Y() >= 0 && side.hasBottomComponent() && otherVel.Y() < 0) ||
                        (vel.Y() <= 0 && side.hasTopComponent() && otherVel.Y() > 0)) {
                    var newY = vel.Y() * - 1 + otherVel.Y();
                    vel.set(vel.X(), newY);
                }
                else if (vel.Y() >= 0 && side.hasBottomComponent() ||
                        vel.Y() <= 0 && side.hasTopComponent()) {
                    vel.scale(1, -1);
                }

                // Same as above, prevents the paddle from overtaking the ball in the
                // horizontal direction this time.
                if ((vel.X() >= 0 && side.hasRightComponent() && otherVel.X() < 0) ||
                        (vel.X() <= 0 && side.hasLeftComponent() && otherVel.X() > 0)) {
                    var newX = vel.X() * - 1 + otherVel.X();
                    vel.set(newX, vel.Y());
                }
                else if (vel.X() >= 0 && side.hasRightComponent() ||
                        vel.X() <= 0 && side.hasLeftComponent()) {
                    vel.scale(-1, 1);
                }
                collider.setVelocity(vel);
                return;
            }

            // Now we can map the position of the ball relative to the paddle to an angle of reflection, in radians
            var newAngle = Utility.MapRange(ballCenter, paddleMin, paddleMax, angleMin, angleMax) * Math.PI / 180;
            var h = collider.getVelocity().length();

            var newX = h * Math.cos(newAngle);
            var newY = h * Math.sin(newAngle);
            collider.setVelocity(new Vector2D(newX, newY));
        }



    }
}
