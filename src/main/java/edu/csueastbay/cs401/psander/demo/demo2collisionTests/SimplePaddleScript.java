package edu.csueastbay.cs401.psander.demo.demo2collisionTests;

import edu.csueastbay.cs401.psander.engine.input.InputManager;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.scripts.Script;
import javafx.scene.input.KeyCode;

public class SimplePaddleScript extends Script {
    @Override
    public void update(double delta) {
        var yvelocity = 0.0;
        var xvelocity = 0.0;
        var steerSpeed = 50.0;

        if (InputManager.getInstance().isKeyPressed(KeyCode.W)) yvelocity -= steerSpeed;
        if (InputManager.getInstance().isKeyPressed(KeyCode.S)) yvelocity += steerSpeed;
        if (InputManager.getInstance().isKeyPressed(KeyCode.A)) xvelocity -= steerSpeed;
        if (InputManager.getInstance().isKeyPressed(KeyCode.D)) xvelocity += steerSpeed;

        var goPos = getOwner().Transform().Position();

        var vel = new Vector2D(xvelocity, yvelocity);
        vel.scale(delta);

        getOwner().Transform().Position().add(vel);
    }
}
