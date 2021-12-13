package edu.csueastbay.cs401.psander.game.scripts;

import edu.csueastbay.cs401.psander.engine.input.InputEvent;
import edu.csueastbay.cs401.psander.engine.input.InputManager;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.scripts.Script;
import javafx.scene.input.KeyCode;

public class VerticalPaddle extends Script {
    private int _player;
    private final double _steerSpeed = 250.0;

    public VerticalPaddle(int player) {
        _player = player;
    }

    @Override
    public void update(double delta) {
        var moveAmt = 0;

        if (InputManager.getInstance().isInputActive(InputEvent.down(_player)))
            moveAmt += _steerSpeed;
        if (InputManager.getInstance().isInputActive(InputEvent.up(_player)))
            moveAmt -= _steerSpeed;
        var vel = new Vector2D(0, moveAmt);
        vel.scale(delta);

        getOwner().Transform().Position().add( new Vector2D(0, moveAmt * delta));
    }
}
