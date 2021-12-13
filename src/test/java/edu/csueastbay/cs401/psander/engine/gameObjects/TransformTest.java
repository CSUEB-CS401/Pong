package edu.csueastbay.cs401.psander.engine.gameObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransformTest {

    @Test
    public void testPositions() {
        var go1 = new GameObject("test 1");
        var go2 = new GameObject("test 2");
        go1.addChild(go2);
        go2.Transform().Position().set(1.0, 2.0);

        assertEquals(1.0, go2.Transform().Position().X());
        assertEquals(2.0, go2.Transform().Position().Y());
        assertEquals(1.0, go2.Transform().getWorldPosition().X());
        assertEquals(2.0, go2.Transform().getWorldPosition().Y());

        go1.Transform().Position().set(3.0, 4.0);
        assertEquals(1.0, go2.Transform().Position().X());
        assertEquals(2.0, go2.Transform().Position().Y());
        assertEquals(4.0, go2.Transform().getWorldPosition().X());
        assertEquals(6.0, go2.Transform().getWorldPosition().Y());
    }
}
