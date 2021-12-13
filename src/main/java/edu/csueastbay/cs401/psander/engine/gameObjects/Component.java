package edu.csueastbay.cs401.psander.engine.gameObjects;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;

/**
 * Base abstract for a component. Components
 * can be added to GameObjects to give them
 * added functionality.
 */
abstract public class Component {

    private GameObject _owner = null;

    /**
     * Initializes the component. Derived
     * classes can override this if they
     * require specific set up sequences.
     */
    public void init() {}

    /**
     * Updates this component in the current frame.
     * @param delta The amount of time since the last frame, in seconds.
     */
    public abstract void update(double delta);

    /**
     * Sets the owner for this component. Called
     * automatically when added to a GameObject.
     * @param owner The GameObject owning this class.
     */
    void setOwner(GameObject owner) {
        _owner = owner;
        onRegisterOwner();
    }

    /**
     * Retrieves the GameObject that owns this class.
     * @return The GameObject that owns this class.
     */
    public GameObject getOwner() {
        return _owner;
    }

    protected void onRegisterOwner() {}
}
