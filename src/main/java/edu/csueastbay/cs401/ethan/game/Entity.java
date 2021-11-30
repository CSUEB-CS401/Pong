package edu.csueastbay.cs401.ethan.game;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.Objects;

public abstract class Entity {

    private static long next_id = 0;

    public final long id;

    protected final Group root;
    final Translate posTform;
    final Rotate rotTform;

    /** The {@link Game} containing this entity. */
    public Game game;

    /** The horizontal position of this Entity */
    public double x;
    /** The vertical position of this Entity */
    public double y;
    /** The rotation of this Entity about its center */
    public double rotation;

    public Entity() {
        id = next_id++;
        root = new Group();
        root.setAutoSizeChildren(true);
        posTform = new Translate();
        rotTform = new Rotate();
    }

    public final void addNode(Node node) {
        root.getChildren().add(node);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
