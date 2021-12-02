package edu.csueastbay.cs401.psander.engine.input;

/**
 * A enum representing various menu navigation
 * events.
 */
public enum MenuInputEvent {
    /**
     * Nevigating up in a menu.
     */
    MENU_UP,
    /**
     * Navigating down in a menu.
     */
    MENU_DOWN,
    /**
     * Nevigating left in a menu.
     */
    MENU_LEFT,
    /**
     * Navigating right in a menu.
     */
    MENU_RIGHT,
    /**
     * Confirming a menu option.
     */
    MENU_CONFIRM,
    /**
     * Cancelling out of a menu.
     */
    MENU_CANCEL
}
