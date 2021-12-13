package edu.csueastbay.cs401.ethan.game;

/**
 * Interface for providing simple controls for a {@link Game}.
 * @see InputHandler#isHeld(String)
 */
public interface InputHandler {
    /**
     * Returns whether the given control is being held.
     * @param control The control to check
     * @return if the control is being held
     */
    boolean isHeld(String control);
}
