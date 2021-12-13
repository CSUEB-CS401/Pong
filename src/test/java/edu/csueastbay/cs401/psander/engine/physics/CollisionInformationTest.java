package edu.csueastbay.cs401.psander.engine.physics;

import edu.csueastbay.cs401.psander.engine.common.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CollisionInformationTest {
    @Test
    // This is honestly kind of silly, since all of the
    // record's code is compiler-generated, but it's still
    // showing up as untested code, so here we are.
    public void TestConstructor() {
        var col = new BoxCollider(10.0, 10.0, ColliderMode.STATIC);
        assertDoesNotThrow( () -> {
            var info = new CollisionInformation(col, Direction.NONE);
        });
    }
}
