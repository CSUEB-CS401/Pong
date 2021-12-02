package edu.csueastbay.cs401.psander.demo.demo2collisionTests;

import edu.csueastbay.cs401.psander.engine.physics.BoxCollider;
import edu.csueastbay.cs401.psander.engine.physics.CollisionInformation;
import edu.csueastbay.cs401.psander.engine.physics.CollisionListener;

import java.util.ArrayList;

public class BallListener extends CollisionListener {
    @Override
    public void processCollisions(ArrayList<CollisionInformation> collisions) {
        var collider = getOwner().getComponent(BoxCollider.class);
        if (collider == null) return;
        var vel = collider.getVelocity();

        if (collisions.isEmpty()) return;

        // Just making sure the basis work for now.
        var direction = collisions.get(0).location();

        switch (direction) {
            case NONE -> vel.scale(1, 1);
            case RIGHT -> {
                if (vel.X() > 0) vel.scale(-1, 1);
            }
            case TOP_RIGHT -> {
                if (vel.X() > 0) vel.scale(-1, 1);
                if (vel.Y() < 0) vel.scale(1, -1);
            }
            case TOP -> {
                if (vel.Y() < 0) vel.scale(1, -1);
            }
            case TOP_LEFT -> {
                if (vel.Y() < 0) vel.scale(1, -1);
                if (vel.X() < 0) vel.scale(-1, 1);
            }
            case LEFT -> {
                if (vel.X() < 0) vel.scale(-1, 1);
            }
            case BOTTOM_LEFT -> {
                if (vel.X() < 0) vel.scale(-1, 1);
                if (vel.Y() > 0) vel.scale(1, -1);
            }
            case BOTTOM -> {
                if (vel.Y() > 0) vel.scale(1, -1);
            }
            case BOTTOM_RIGHT -> {
                if (vel.Y() > 0) vel.scale(1, -1);
                if (vel.X() > 0) vel.scale(-1, 1);
            }
        }
        //vel.scale(1.1);
        collider.setVelocity(vel);
        //System.out.println("Ow! " + getOwner().getName() + " impacted with " +
        //        collisions.get(0).other().getOwner().getName() + "!");
    }
}
