package edu.csueastbay.cs401.psander.engine.menu;

import edu.csueastbay.cs401.psander.engine.input.InputEvent;
import edu.csueastbay.cs401.psander.engine.input.InputManager;
import edu.csueastbay.cs401.psander.engine.input.MenuInputEvent;
import edu.csueastbay.cs401.psander.engine.render.TextRenderer;

import java.util.List;

/**
 * Represents a menu option that can update a key binding.
 */
public class SetKeybindingMenuItem extends MenuItem {
    private final InputEvent _input;
    private TextRenderer _renderer;
    private boolean _currentlyBinding;

    /**
     * Creates a keybinding menu item
     * @param input The keybinding to manage
     */
    public SetKeybindingMenuItem(InputEvent input) {
        _input = input;
    }

    @Override
    public void update(double delta) {
        if (_currentlyBinding) {
            _renderer.setText("" + _input + ": " + "<press any key, escape to cancel>");

            var opt = InputManager.getInstance().getCapturedKeystroke();
            if (opt.isEmpty()) return;
            _currentlyBinding = false;
            var code = opt.get();

            InputManager.getInstance().setKeycodeForInput(_input, code);
        }

        var code = InputManager.getInstance().getKeycodeForInput(_input);
        String disp;
        if (code.isEmpty())
            disp = "[none]";
        else
            disp = code.get().toString();

        _renderer.setText("" + _input + ": " + disp);
    }

    @Override
    public void processMenuInput(List<MenuInputEvent> events) {
        for(var event : events) {
            if (!_currentlyBinding && event == MenuInputEvent.MENU_CONFIRM) {
                _currentlyBinding = true;
                InputManager.getInstance().captureNextKeycode();
            } else if (_currentlyBinding && event == MenuInputEvent.MENU_CANCEL) {
                _currentlyBinding = false;
                InputManager.getInstance().getCapturedKeystroke(); // Clear this buffer.
            }
        }
    }

    public void setTextRenderer(TextRenderer renderer) {
        _renderer = renderer;
    }
}
