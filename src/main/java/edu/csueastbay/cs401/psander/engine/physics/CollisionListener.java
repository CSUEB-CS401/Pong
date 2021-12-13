package edu.csueastbay.cs401.psander.engine.physics;

import edu.csueastbay.cs401.psander.engine.gameObjects.Component;

import java.util.ArrayList;

/**
 * Collision listeners are called on to react to an impact
 * between two colliders.
 */
public abstract class CollisionListener extends Component {
    /**
     * Updates the component for the game frame being processed.
     * @param delta The amount of time since the last frame, in seconds.
     */
    @Override
    public void update(double delta) {  }

    /**
     * Reacts to an impact in whatever way is necessary: updating velocity,
     * changing the object's state, and so on.
     * @param collisions The object and side this collider will impact with.
     */
    abstract public void processCollisions(ArrayList<CollisionInformation> collisions);
}
