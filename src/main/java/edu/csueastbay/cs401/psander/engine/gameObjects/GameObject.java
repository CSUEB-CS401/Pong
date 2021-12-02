package edu.csueastbay.cs401.psander.engine.gameObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an object in the game engine. Functionality
 * is added via composition and the addition of Components.
 */
public class GameObject {
    private String _name;
    private GameObject _parent = null;
    private final List<GameObject> _children = new ArrayList<>();

    private final List<Component> _components = new ArrayList<>();
    private final Transform _transform;

    /**
     * Default constructor.
     * @param name The name of the game object.
     */
    public GameObject(String name) {
        _name = name;

        _transform = new Transform();
        _components.add(_transform);
        _transform.setOwner(this);
    }

    /**
     * Get the name of the game object.
     * @return The game object's name.
     */
    public String getName() { return _name; }

    /**
     * Sets the game object's name.
     * @param name The new name to set.
     */
    public void setName(String name) { _name = name; }

    /**
     * Returns the game object's transforms,
     * which sets its position in the world.
     * @return The game object's transform.
     */
    public Transform Transform() { return _transform; }

    /**
     * Returns the game object's parent, if one is set.
     * @return The game object's parent if it exists, otherwise null.
     */
    public GameObject Parent() { return _parent; }

    /**
     * Adds a child to this game object.
     * @param go The game object to put underneath this one in a scene hierarchy.
     */
    public void addChild(GameObject go) {
        if (go._parent != null) return;

        go._parent = this;
        _children.add(go);
    }

    /**
     * Returns a copy of the list of children for this game object.
     * @return The list of this game object's children.
     */
    public List<GameObject> getChildren() { return new ArrayList<>(_children); }

    /**
     * Removes the specified game object as a child of
     * this game object.
     * @param go The child game object to remove.
     */
    public void removeChild(GameObject go) {
        if (go._parent != this) return;

        go._parent = null;
        _children.remove(go);
    }

    /**
     * Adds a component to this game object.
     * @param c The component to add.
     */
    public void addComponent(Component c) {
        if (c.getClass() == Transform.class) return;

        _components.add(c);
        c.setOwner(this);
    }

    /**
     * Returns the component matching the specified class. Only returns
     * the first instance of that class added, and returns null if no
     * component of that type is present.
     * @param componentClass The component class to fetch a component for.
     * @param <T>            The type of the game object being requested.
     * @return               The component if it exists, otherwise null.
     */
    public <T extends Component> T getComponent(Class<T> componentClass)
    {
        for(Component c : _components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                return componentClass.cast(c);
            }
        }

        return null;
    }

    /**
     * Removes a component of the specified type. Transforms cannot be removed.
     * If multiple components of the same type have been added, only the
     * first one is removed.
     * @param componentClass The class of component to remove.
     * @param <T>            The class of the component being removed.
     */
    public <T extends Component> void removeComponent(Class<T> componentClass)
    {
        if (componentClass == Transform.class) return;

        for(int i = 0; i < _components.size(); i++)
        {
            var c = _components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                _components.remove(i);
                return;
            }
        }
    }
}
