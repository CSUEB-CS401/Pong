package edu.csueastbay.cs401.psander.demo.demo4textRendering;

import edu.csueastbay.cs401.psander.demo.demo3renderlayers.FakeBounce2Script;
import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.render.TextRenderer;
import edu.csueastbay.cs401.psander.engine.scenes.Scene;
import javafx.scene.paint.Color;

public class TextRenderingTest extends Scene {
    @Override
    public void init() {
        this.addGameObject(makeStaticText(100, 100, "This is a test of static text",
                Color.WHITE, 99));
        this.addGameObject(makeBouncingText(100, 100, 20, 10, new Vector2D(0, -1),
                "I'm bouncing around!", Color.CYAN, 33));
    }

    private GameObject makeStaticText(double x, double y, String text, Color color, int layer) {
        var go = new GameObject("text");
        go.Transform().Position().set(x, y);
        go.addComponent(new TextRenderer(text, color, layer));
        return go;
    }

    private GameObject makeBouncingText(double x, double y, double width, double height,
                                        Vector2D velocity, String text, Color color, int layer) {
        var go = new GameObject("Bouncing text");
        go.Transform().Position().set(x, y);
        go.addComponent(new FakeBounce2Script(width, height, velocity));
        go.addComponent(new TextRenderer(text, color, layer));
        return go;
    }
}
