package edu.csueastbay.cs401.psander.engine.input;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputManagerTest {

    @BeforeEach
    void clearState() { InputManager.getInstance().init(); }

    @Test
    public void InitClearsInputState() {
        var instance = InputManager.getInstance();
        instance.keyDown(KeyCode.W);
        instance.init();
        assertFalse(instance.isKeyPressed(KeyCode.W));
    }

    @Test
    public void UpdateProcessesAllInputCorrectly() {
        var instance = InputManager.getInstance();
        instance.keyDown(KeyCode.W);
        assertFalse(instance.isKeyPressed(KeyCode.W), "Input should not be active.");
        instance.update(1/60.0);
        assertTrue(instance.isKeyPressed(KeyCode.W), "Input should be active.");

        // Check double inputs
        instance.keyUp(KeyCode.W);
        instance.keyDown(KeyCode.W);
        instance.update(1/60.0);
        assertTrue(instance.isKeyPressed(KeyCode.W), "Input should be active.");
        assertTrue(instance.isInputActive(InputEvent.PLAYER_1_UP), "Player 1 Up should be active");
    }

    @Test
    public void checkSettingKeyMappings() {
        // Setup
        var instance = InputManager.getInstance();
        instance.setKeycodeForInput(InputEvent.PLAYER_1_UP, KeyCode.W);
        instance.setKeycodeForInput(InputEvent.PLAYER_1_DOWN, KeyCode.S);

        // Retrieve starting value
        var opt = instance.getKeycodeForInput(InputEvent.PLAYER_1_UP);
        assertTrue(opt.isPresent(), "Keycode should not be empty.");
        assertEquals(KeyCode.W, opt.get(), "Default mapping should be W.");

        // Check setting a key that is not currently in use
        instance.setKeycodeForInput(InputEvent.PLAYER_1_UP, KeyCode.Q);
        assertEquals(KeyCode.Q, instance.getKeycodeForInput(InputEvent.PLAYER_1_UP).get());

        // Check setting a key already in use
        instance.setKeycodeForInput(InputEvent.PLAYER_1_UP, KeyCode.S);
        var up = instance.getKeycodeForInput(InputEvent.PLAYER_1_UP);
        assertTrue(up.isPresent());
        assertEquals(up.get(), KeyCode.S);
        assertTrue(instance.getKeycodeForInput(InputEvent.PLAYER_1_DOWN).isEmpty());
    }

    @Test
    public void checkKeyCapture() {
        var instance = InputManager.getInstance();

        // Check status before starting
        assertTrue(instance.getCapturedKeystroke().isEmpty());

        // Start a capture
        instance.captureNextKeycode();
        assertTrue(instance.getCapturedKeystroke().isEmpty());

        instance.keyDown(KeyCode.W);
        instance.keyDown(KeyCode.S);

        // Check that we captured the first input...
        var opt1 = instance.getCapturedKeystroke();
        assertTrue(opt1.isPresent());
        assertEquals(KeyCode.W, opt1.get());

        // Check that it blanks after being accessed
        var opt2 = instance.getCapturedKeystroke();
        assertTrue(opt2.isEmpty());
    }

    @Test
    public void MenuInputTests() {
        var instance = InputManager.getInstance();

        // Should be unpressed state, return false
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_UP));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_DOWN));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_LEFT));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_RIGHT));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_CONFIRM));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_CANCEL));

        // Should be pressed state, return true
        instance.keyDown(KeyCode.UP);
        instance.keyDown(KeyCode.DOWN);
        instance.keyDown(KeyCode.LEFT);
        instance.keyDown(KeyCode.RIGHT);
        instance.keyDown(KeyCode.ENTER);
        instance.keyDown(KeyCode.ESCAPE);
        instance.update(1.0);

        assertTrue(instance.isMenuInputPressed(MenuInputEvent.MENU_UP));
        assertTrue(instance.isMenuInputPressed(MenuInputEvent.MENU_DOWN));
        assertTrue(instance.isMenuInputPressed(MenuInputEvent.MENU_LEFT));
        assertTrue(instance.isMenuInputPressed(MenuInputEvent.MENU_RIGHT));
        assertTrue(instance.isMenuInputPressed(MenuInputEvent.MENU_CONFIRM));
        assertTrue(instance.isMenuInputPressed(MenuInputEvent.MENU_CANCEL));

        // Should be held state, return false
        instance.update(2.0);
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_UP));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_DOWN));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_LEFT));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_RIGHT));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_CONFIRM));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_CANCEL));

        // Should be released state, return false
        instance.keyUp(KeyCode.UP);
        instance.keyUp(KeyCode.DOWN);
        instance.keyUp(KeyCode.LEFT);
        instance.keyUp(KeyCode.RIGHT);
        instance.keyUp(KeyCode.ENTER);
        instance.keyUp(KeyCode.ESCAPE);
        instance.update(3.0);
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_UP));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_DOWN));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_LEFT));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_RIGHT));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_CONFIRM));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_CANCEL));

        // Should be unpressed state again, return false
        instance.update(4.0);
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_UP));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_DOWN));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_LEFT));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_RIGHT));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_CONFIRM));
        assertFalse(instance.isMenuInputPressed(MenuInputEvent.MENU_CANCEL));
    }
}
