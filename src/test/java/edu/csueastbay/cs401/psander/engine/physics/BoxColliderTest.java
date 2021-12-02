package edu.csueastbay.cs401.psander.engine.physics;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoxColliderTest {
    @Test
    public void testGettersAndSetters() {
        var col = new BoxCollider(1.0, 2.0, ColliderMode.STATIC);

        assertEquals(1.0, col.getWidth());
        assertEquals(2.0, col.getHeight());

        col.setWidth(3.0);
        assertEquals(3.0, col.getWidth());

        col.setHeight(4.0);
        assertEquals(4.0, col.getHeight());

        col.setMode(ColliderMode.DYNAMIC);
        assertEquals(ColliderMode.DYNAMIC, col.getMode());

        col.setVelocity(new Vector2D(5.0,6.0));
        assertEquals(5.0, col.getVelocity().X());
        assertEquals(6.0, col.getVelocity().Y());

        // Make sure static colliders have zero velocity.
        col.setMode(ColliderMode.STATIC);
        assertEquals(0.0, col.getVelocity().X());
        assertEquals(0.0, col.getVelocity().Y());

        var go = new GameObject("test");
        go.addComponent(col);

        assertEquals(0.0, col.getPreviousPosition().X());
        assertEquals(0.0, col.getPreviousPosition().Y());

        go.Transform().Position().set(7.0, 8.0);
        col.resetPreviousPosition();
        assertEquals(7.0, col.getPreviousPosition().X());
        assertEquals(8.0, col.getPreviousPosition().Y());

        go.Transform().Position().set(9.0, 10.0);
        col.postUpdate();
        assertEquals(9.0, col.getPreviousPosition().X());
        assertEquals(10.0, col.getPreviousPosition().Y());
    }

    @Test
    public void testPhysicsAdvancement() {
        var col = new BoxCollider(1.0, 1.0, ColliderMode.DYNAMIC);
        var go = new GameObject("test");
        go.addComponent(col);

        col.setVelocity(new Vector2D(1.0, 2.0));
        col.advance(1.0);
        assertEquals(1.0, go.Transform().Position().X());
        assertEquals(2.0, go.Transform().Position().Y());
    }
}
