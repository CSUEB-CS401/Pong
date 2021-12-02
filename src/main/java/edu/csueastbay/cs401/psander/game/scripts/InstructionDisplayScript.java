package edu.csueastbay.cs401.psander.game.scripts;

import edu.csueastbay.cs401.psander.engine.input.InputEvent;
import edu.csueastbay.cs401.psander.engine.input.InputManager;
import edu.csueastbay.cs401.psander.engine.render.TextRenderer;
import edu.csueastbay.cs401.psander.engine.scripts.Script;

public class InstructionDisplayScript extends Script {
    private final TextRenderer _textRenderer;

    public InstructionDisplayScript(TextRenderer renderer) {
        _textRenderer = renderer;
    }

    @Override
    public void update(double delta) {
        var text = "Instructions\n";
        text += "Menu Up/Down: UP/DOWN\n";
        text += "Menu Confirm: ENTER\n";

        var p1upCode = InputManager.getInstance().getKeycodeForInput(InputEvent.PLAYER_1_UP);
        var p1upText = p1upCode.isPresent() ? p1upCode.get().toString() : "[None]";
        var p1downCode = InputManager.getInstance().getKeycodeForInput(InputEvent.PLAYER_1_DOWN);
        var p1downText = p1downCode.isPresent() ? p1downCode.get().toString() : "[None]";
        text += "Player 1 Up/Down: " + p1upText + "/" + p1downText + "\n";

        var p2upCode = InputManager.getInstance().getKeycodeForInput(InputEvent.PLAYER_2_UP);
        var p2upText = p2upCode.isPresent() ? p2upCode.get().toString() : "[None]";
        var p2downCode = InputManager.getInstance().getKeycodeForInput(InputEvent.PLAYER_2_DOWN);
        var p2downText = p2downCode.isPresent() ? p2downCode.get().toString() : "[None]";
        text += "Player 2 Up/Down: " + p2upText + "/" + p2downText + "\n";
        _textRenderer.setText(text);
    }
}
