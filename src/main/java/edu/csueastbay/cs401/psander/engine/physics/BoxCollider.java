package edu.csueastbay.cs401.psander.engine.physics;

import edu.csueastbay.cs401.psander.engine.gameObjects.Component;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;

/**
 * A simple axis-aligned bounding box to be used
 * in game collisions.
 */
public class BoxCollider extends Component {

    private ColliderMode _mode;

    private double _width;
    private double _height;

    private Vector2D _velocity = new Vector2D();
    private final Vector2D _prevPosition = new Vector2D();

    /**
     * Basic box collider constructor.
     * @param width The collider's width.
     * @param height The collider's height.
     * @param mode The mode the collider should operate in.
     */
    public BoxCollider(double width, double height, ColliderMode mode)
    {
        _width = width;
        _height = height;
        _mode = mode;
    }

    public double getWidth() { return _width; }

    public void setWidth(double width) { _width = width; }

    public double getHeight() { return _height; }

    public void setHeight(double height) { _height = height; }

    public Vector2D getVelocity() { return new Vector2D(_velocity); }

    public void setVelocity(Vector2D v1) {
        if (_mode != ColliderMode.STATIC)
            _velocity = v1;
    }

    public ColliderMode getMode() { return _mode; }

    public void setMode(ColliderMode mode) {
        _mode = mode;
        if (mode == ColliderMode.STATIC) _velocity = new Vector2D(0.0, 0.);
    }

    Vector2D getPreviousPosition() { return _prevPosition; }

    /**
     * <p>Updates the collider during frame updates.</p>
     *
     * <p>This is empty as updates
     * are handled in other places. Colliders set to static
     * will not move, and colliders marked dynamic are controlled by their
     * velocity and listener scripts. Kinematic colliders are controlled by
     * script.</p>
     * @param delta The amount of time since the last frame, in seconds.
     */
    @Override
    public void update(double delta) { }

    void postUpdate() {
        _prevPosition.set(getOwner().Transform().Position());
    }

    /**
     * Resets the collider's last known position. Used
     * for kinematic colliders that want to change a game
     * object's position without interpolating between the
     * old and new locations.
     */
    public void resetPreviousPosition() {
        _prevPosition.set(getOwner().Transform().Position());
    }

    void advance(double delta) {
        var step = Vector2D.scale(_velocity, delta);
        getOwner().Transform().Position().add(step);
    }

    @Override
    protected void onRegisterOwner() {
        _prevPosition.set(getOwner().Transform().Position());
    }
}
