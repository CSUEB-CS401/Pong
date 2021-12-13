package edu.csueastbay.cs401.ethan.game;

import javafx.scene.shape.Shape;

/**
 * A Collidable is an Object which, when added to a {@link Game}, can check for collisions with other Collidables.
 * @see Game#getCollisions(Collidable)
 * @see Game#getCollisionsWithType(Collidable, Class)
 */
public interface Collidable {
    /**
     * Returns the {@link Shape} used for Collisions.
     * @return the collision shape
     */
    Shape getCollisionShape();
}
