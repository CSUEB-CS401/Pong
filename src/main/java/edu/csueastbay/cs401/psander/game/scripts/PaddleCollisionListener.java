package edu.csueastbay.cs401.psander.game.scripts;

import edu.csueastbay.cs401.psander.engine.physics.BoxCollider;
import edu.csueastbay.cs401.psander.engine.physics.ColliderMode;
import edu.csueastbay.cs401.psander.engine.physics.CollisionInformation;
import edu.csueastbay.cs401.psander.engine.physics.CollisionListener;

import java.util.ArrayList;

public class PaddleCollisionListener extends CollisionListener {
    @Override
    public void processCollisions(ArrayList<CollisionInformation> collisions) {
        for(var collision : collisions) {
            var other = collision.other();
            var side = collision.location();

            var collider = this.getOwner().getComponent(BoxCollider.class);

            if (other.getMode() == ColliderMode.STATIC) {
                if (side.hasHorizontalComponent()) {
                    var v = collider.getVelocity();
                    v.setX(0.0);
                    collider.setVelocity(v);
                }

                if (side.hasVerticalComponent()) {
                    var v = collider.getVelocity();
                    v.setY(0.0);
                    collider.setVelocity(v);
                }
            }
        }
    }
}
