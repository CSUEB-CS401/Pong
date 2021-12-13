package edu.csueastbay.cs401.psander.engine.input;

/**
 * An enum tracking the state of a key being pressed.
 */
public enum InputState {
    /**
     * A key that is not currently being pressed.
     * The key's initial state and the state one
     * frame after being released.
     */
    UNPRESSED,
    /**
     * A key htat has just been pressed. Only
     * set for the initial frame the key was
     * pressed.
     */
    PRESSED,
    /**
     * A key being held for more than one frame.
     */
    HELD,
    /**
     * Set the frame a key is relased. Afterwards,
     * will be set to unpressed.
     */
    RELEASED
}
