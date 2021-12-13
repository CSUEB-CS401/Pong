package edu.csueastbay.cs401.psander.engine.scripts;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScriptManagerTest {

    static class ScriptMockup extends Script {
        double Delta;

        @Override
        public void update(double delta) {
            Delta = delta;
        }
    }

    @Test
    public void testUpdateLoop() {
        var script = new ScriptMockup();
        var go = new GameObject("test");
        go.addComponent(script);
        var lst = new ArrayList<GameObject>();
        lst.add(go);

        var scriptManager = new ScriptManager();
        scriptManager.update(1.0, lst);
        assertEquals(1.0, script.Delta);
    }
}
