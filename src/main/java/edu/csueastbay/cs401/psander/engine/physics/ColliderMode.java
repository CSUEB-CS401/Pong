package edu.csueastbay.cs401.psander.engine.physics;

/**
 * Controls how colliders operate.
 */
public enum ColliderMode {
    /**
     * Static colliders do not move and will remain stationary.
     */
    STATIC,
    /**
     * Dynamic colliders will be updated according to their velocity
     * and listener scripts.
     */
    DYNAMIC,
    /**
     * Kinematic scripts are updated by a game script, then
     * collisions are calculated as if the object was evenly
     * interpolating between the old and new positions.
     */
    KINEMATIC
}
