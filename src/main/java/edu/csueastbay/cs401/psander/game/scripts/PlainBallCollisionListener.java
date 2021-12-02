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
                if ( (vel.X() > 0 && side.hasRightComponent()) ||
                        (vel.X() < 0 && side.hasLeftComponent()) )
                    vel.scale(-1, 1);
                if ( (vel.Y() > 0 && side.hasBottomComponent()) ||
                        (vel.Y() < 0 && side.hasTopComponent()) )
                    vel.scale(1, -1);
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
