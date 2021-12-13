package edu.csueastbay.cs401.psander.engine.input;

import java.security.InvalidParameterException;

/**
 * Describes the various game inputs that can be received by the game.
 */
public enum InputEvent {
    /**
     * Player 1 pressing 'up'
     */
    PLAYER_1_UP,
    /**
     * Player 1 pressing 'down'
     */
    PLAYER_1_DOWN,
    /**
     * Player 1 pressing 'left'
     */
    PLAYER_1_LEFT,
    /**
     * Player 1 pressing 'right'
     */
    PLAYER_1_RIGHT,
    /**
     * Player 2 pressing 'up'
     */
    PLAYER_2_UP,
    /**
     * Player 2 pressing 'down'
     */
    PLAYER_2_DOWN,
    /**
     * Player 2 pressing 'left'
     */
    PLAYER_2_LEFT,
    /**
     * Player 2 pressing 'right'
     */
    PLAYER_2_RIGHT;

    /**
     * Checks if a particular input event belongs to Player 1.
     * @return True if the input belongs to Player 1, otherwise false.
     */
    public boolean isPlayer1Input() {
        return this == PLAYER_1_UP || this == PLAYER_1_DOWN ||
                this == PLAYER_1_LEFT || this == PLAYER_1_RIGHT;
    }

    /**
     * Checks if a particular input event belongs to Player 2.
     * @return True if the input belongs to Player 2, otherwise false.
     */
    public boolean isPlayer2Input() {
        return this == PLAYER_2_UP || this == PLAYER_2_DOWN ||
                this == PLAYER_2_LEFT || this == PLAYER_2_RIGHT;
    }

    /**
     * Returns the corresponding 'up' input for the given player.
     * @param player The player to retrieve the input for. Valid inputs are 1 and 2.
     * @throws InvalidParameterException
     * Throws this error if an input other than '1' or '2' is given.
     * @return The up input event for the indicated player.
     */
    public static InputEvent up(int player) {
        return switch (player) {
            case 1 -> PLAYER_1_UP;
            case 2 -> PLAYER_2_UP;
            default -> throw new InvalidParameterException();
        };
    }

    /**
     * Returns the corresponding 'down' input for the given player.
     * @param player The player to retrieve the input for. Valid inputs are 1 and 2.
     * @throws InvalidParameterException
     * Throws this error if an input other than '1' or '2' is given.
     * @return The down input event for the indicated player.
     */
    public static InputEvent down(int player) {
        return switch (player) {
            case 1 -> PLAYER_1_DOWN;
            case 2 -> PLAYER_2_DOWN;
            default -> throw new InvalidParameterException();
        };
    }

    /**
     * Returns the corresponding 'left' input for the given player.
     * @param player The player to retrieve the input for. Valid inputs are 1 and 2.
     * @throws InvalidParameterException
     * Throws this error if an input other than '1' or '2' is given.
     * @return The left input event for the indicated player.
     */
    public static InputEvent left(int player) {
        return switch (player) {
            case 1 -> PLAYER_1_LEFT;
            case 2 -> PLAYER_2_LEFT;
            default -> throw new InvalidParameterException();
        };
    }

    /**
     * Returns the corresponding 'right' input for the given player.
     * @param player The player to retrieve the input for. Valid inputs are 1 and 2.
     * @throws InvalidParameterException
     * Throws this error if an input other than '1' or '2' is given.
     * @return The right input event for the indicated player.
     */
    public static InputEvent right(int player) {
        return switch (player) {
            case 1 -> PLAYER_1_RIGHT;
            case 2 -> PLAYER_2_RIGHT;
            default -> throw new InvalidParameterException();
        };
    }
}
