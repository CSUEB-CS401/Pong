package edu.csueastbay.cs401.psander.engine.scenes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SceneManagerTest {
    private class SceneMock extends Scene {
        double Delta;

        @Override
        public void init() {

        }

        @Override
        public void update(double delta) {
            Delta = delta;
        }
    }

    @BeforeEach
    public void Clear() { SceneManager.getInstance().init(); }

    @Test
    public void TestSceneStackOperations() {
        var instance = SceneManager.getInstance();
        var scene1 = new SceneMock();
        var scene2 = new SceneMock();
        var scene3 = new SceneMock();

        instance.update(1.0);
        assertEquals(0.0, scene1.Delta);
        assertEquals(0.0, scene2.Delta);
        assertEquals(0.0, scene3.Delta);


        instance.push(scene1);
        instance.update(1.0);
        assertEquals(1.0, scene1.Delta);
        assertEquals(0.0, scene2.Delta);
        assertEquals(0.0, scene3.Delta);

        instance.push(scene2);
        instance.update(2.0);
        assertEquals(1.0, scene1.Delta);
        assertEquals(2.0, scene2.Delta);
        assertEquals(0.0, scene3.Delta);

        instance.swap(scene3);
        instance.update(3.0);
        assertEquals(1.0, scene1.Delta);
        assertEquals(2.0, scene2.Delta);
        assertEquals(3.0, scene3.Delta);

        instance.pop();
        instance.update(4.0);
        assertEquals(4.0, scene1.Delta);
        assertEquals(2.0, scene2.Delta);
        assertEquals(3.0, scene3.Delta);

        // Testing retrieval methods
        var val1 = instance.peek();
        assertTrue(val1.isPresent());
        assertEquals(scene1, val1.get());
        instance.pop();
        var val2 = instance.peek();
        assertTrue(val2.isEmpty());

        // Now testing for exceptions
        assertThrows(UnsupportedOperationException.class, instance::pop);
        assertThrows(UnsupportedOperationException.class, () -> instance.swap(scene2));

        // And finally test that init clears the stack.
        instance.push(scene1);
        instance.init();
        var val3 = instance.peek();
        assertTrue(val3.isEmpty());
    }
}
