package edu.csueastbay.cs401.psander.engine.scenes;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.physics.BoxCollider;
import edu.csueastbay.cs401.psander.engine.physics.ColliderMode;
import edu.csueastbay.cs401.psander.engine.render.RenderManager;
import edu.csueastbay.cs401.psander.engine.scripts.Script;
import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SceneTest {

    static class SceneMock extends Scene {

        @Override
        public void init() {

        }
    }

    static class ScriptMock extends Script {
        double Delta;

        @Override
        public void update(double delta) {
            Delta = delta;
        }
    }

    @BeforeEach
    public void init() {
        SceneManager.getInstance().init();
    }

    @Test
    public void TestGameOBjectAdditionAndRemoval() {
        var scene = new SceneMock();
        var go = new GameObject("test");

        assertDoesNotThrow( () -> scene.addGameObject(go));
        assertDoesNotThrow( () -> scene.removeGameObject(go));
    }

    @Test
    public void addAndRemoveGameObjects() {
        var scene = new SceneMock();
        var go = new GameObject("test");

        assertDoesNotThrow(() -> scene.addGameObject(go));
        assertDoesNotThrow(() -> scene.removeGameObject(go));
    }

    // This was going to have a collider mock but I couldn't get the
    // update method for BoxCollider to override correctly, and
    // I don't have a base collider class yet.
    @Test
    public void TestScriptExecution() {
        var scene = new SceneMock();
        var go = new GameObject("test");
        var script = new ScriptMock();
        go.addComponent(script);
        var col = new BoxCollider(10.0, 10.0, ColliderMode.STATIC);
        go.addComponent(col);

        scene.addGameObject(go);

        // Extra setup to make sure RenderManager doesn't throw an error
        var canvas = new Canvas();
        var gc = canvas.getGraphicsContext2D();
        RenderManager.getInstance().init(1.0, 1.0, gc);
        scene.update(1.0);

        assertEquals(1.0, script.Delta);
    }
}
