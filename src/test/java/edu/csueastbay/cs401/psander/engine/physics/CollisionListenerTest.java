package edu.csueastbay.cs401.psander.engine.physics;

import edu.csueastbay.cs401.pong.Collision;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CollisionListenerTest {
    static class ListenerMock extends CollisionListener {
        @Override
        public void processCollisions(ArrayList<CollisionInformation> collisions) {}
    }

    // Also a little silly since this is an abstract class with a
    // dummied out method, but it makes the test coverage happy.
    // I possibly need to redesign my architecture a little bit
    // to avoid all of these empty update() methods but I only had
    // so much time for the design.
    @Test
    public void TestCollisionListener() {
        var lis = new ListenerMock();
        assertDoesNotThrow( () -> lis.update(1.0));
    }
}
