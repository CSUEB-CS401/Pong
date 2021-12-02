package edu.csueastbay.cs401.psander.engine.render;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RenderManagerTest {

    static class RenderMock extends Renderer {
        double Delta;

        @Override
        public void update(double delta) {
            Delta = delta;
        }
    }

    @Test
    public void TestInitDoesNotThrow() {
        assertDoesNotThrow( () -> {
            var canvas = new Canvas(); // Needed to get an instance of GraphicsContext;
            RenderManager.getInstance().init(1.0, 2.0,
                    canvas.getGraphicsContext2D());
        });
    }

    @Test
    public void TestUpdate() {
        var rend = new RenderMock();
        var go = new GameObject("test");
        go.addComponent(rend);
        var lst = new ArrayList<GameObject>();
        lst.add(go);


        var instance = RenderManager.getInstance();
        instance.update(1.0, lst);

        assertEquals(1.0, rend.Delta);
    }
}
