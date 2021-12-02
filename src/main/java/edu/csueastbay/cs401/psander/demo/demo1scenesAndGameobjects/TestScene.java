package edu.csueastbay.cs401.psander.demo.demo1scenesAndGameobjects;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.render.RectangleRenderer;
import edu.csueastbay.cs401.psander.engine.scenes.Scene;
import javafx.scene.paint.Color;

public class TestScene extends Scene {

    @Override
    public void init() {
        var go1 = new GameObject("Test GO");
        go1.addComponent(new FakeBounceScript());
        go1.addComponent(new RectangleRenderer(10, 10, Color.WHITE));

        this.addGameObject(go1);

        var go2 = new GameObject("Satellite");
        go2.addComponent(new RectangleRenderer(50, 50, Color.RED));
        go2.Transform().Position().set(10, 10);
        go1.addChild(go2);
        this.addGameObject(go2);
    }
}
