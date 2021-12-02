package edu.csueastbay.cs401.psander.engine.physics;

import edu.csueastbay.cs401.psander.engine.common.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TimeOfImpactResultTest {
    // Kind of silly since this is a record, but it makes the
    // coverage happy.
    @Test
    public void TestConstructor() {
        assertDoesNotThrow( () -> {
            var res = new TimeOfImpactResult(Span.Infinite,
                    new BoxCollider(10.0, 10.0, ColliderMode.STATIC),
                    Direction.LEFT,
                    new BoxCollider(10.0, 10.0, ColliderMode.STATIC),
                    Direction.RIGHT);
        });
    }
}
