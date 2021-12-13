package edu.csueastbay.cs401.ethan.game;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Abstract class representing all objects within the {@link Game}.
 */
public abstract class Entity {

    final Group root;
    private final Translate posTform;
    private final Rotate rotTform;

    /** The {@link Game} containing this entity. */
    public Game game;

    /** The horizontal position of this Entity */
    public double x;
    /** The vertical position of this Entity */
    public double y;
    /** The rotation of this Entity about its center */
    public double rotation;

    /**
     * Constructs an Entity at the origin with no rotation.
     */
    public Entity() {
        root = new Group();
        root.setAutoSizeChildren(true);
        posTform = new Translate();
        rotTform = new Rotate();
    }

    /**
     * Adds a {@link Node} to this Entity's root. This is necessary if the node should inherit the Entity's position
     * (such as {@link Collidable#getCollisionShape() collision shapes} or visible nodes).
     * @param node the node to add
     */
    public final void addNode(Node node) {
        root.getChildren().add(node);
    }

    /**
     * Removes a {@link Node} from this Entity.
     * @param node the node to remove
     */
    public final void removeNode(Node node) {
        root.getChildren().remove(node);
    }

    /** Updates scene related transforms to match this Entity's {@link Entity#x}, {@link Entity#y}, and {@link Entity#rotation}. */
    final void commit() {
        posTform.setX(x);
        posTform.setY(y);
        rotTform.setAngle(rotation);
        root.getTransforms().setAll(posTform, rotTform);
    }

    /**
     * This method is where game logic for this Entity should occur.
     * @see Game#getCollisions(Collidable)
     * @param delta elapsed time since last call, in seconds. Used to normalize motion.
     */
    public abstract void update(double delta);

}
