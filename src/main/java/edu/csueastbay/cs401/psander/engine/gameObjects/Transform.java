package edu.csueastbay.cs401.psander.engine.gameObjects;

import edu.csueastbay.cs401.psander.engine.math.Vector2D;

/**
 * A component representing a game object's position in a scene.
 */
public class Transform extends Component {

    private final Vector2D _position = new Vector2D();

    Transform() {}

    @Override
    public void update(double delta) {}

    /**
     * Retrieves the transform's position.
     * @return The Vector2D representing the transform's position.
     */
    public Vector2D Position() { return _position; }

    /**
     * Translates the object's position to be relative
     * to any parent game objects.
     * @return A Vector2D representing the transform's absolute
     * position relative to its parents.
     */
    public Vector2D getWorldPosition() {

        var parent = getOwner().Parent();
        if (parent == null)
            return new Vector2D(_position);
        else
            return Vector2D.add(_position, parent.Transform().getWorldPosition());
    }
}
