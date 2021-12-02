package edu.csueastbay.cs401.psander.engine.common;

/**
 * An enum indicating the direction of something.
 */
public enum Direction {
    /**
     * No direction.
     */
    NONE,
    /**
     * Right direction.
     */
    RIGHT,
    /**
     * Top-right direction.
     */
    TOP_RIGHT,
    /**
     * Top direction.
     */
    TOP,
    /**
     * Top-left direction.
     */
    TOP_LEFT,
    /**
     * Left direction.
     */
    LEFT,
    /**
     * Bottom-left direction.
     */
    BOTTOM_LEFT,
    /**
     * Bottom direction.
     */
    BOTTOM,
    /**
     * Bottom-right direction.
     */
    BOTTOM_RIGHT;

    /**
     * Determines whether the direction has a horizontal component.
     * @return True if the direction has a horizontal component, otherwise false.
     */
    public boolean hasHorizontalComponent() {
        return hasRightComponent() || hasLeftComponent();
    }

    /**
     * Determines whether the direction has a vertical component.
     * @return True if the direction has a vertical component, otherwise false.
     */
    public boolean hasVerticalComponent() {
        return hasTopComponent() || hasBottomComponent();
    }

    /**
     * Determines if the direction has a right component.
     * @return True if the direction has a right component, otherwise false.
     */
    public boolean hasRightComponent() {
        return (this == TOP_RIGHT || this == RIGHT || this == BOTTOM_RIGHT);
    }

    /**
     * Determines if the direction has a left component.
     * @return True if the direction has a left component, otherwise false.
     */
    public boolean hasLeftComponent() {
        return (this == TOP_LEFT || this == LEFT || this == BOTTOM_LEFT);
    }

    /**
     * Determines if the direction has a top component.
     * @return True if the direction has a top component, otherwise false.
     */
    public boolean hasTopComponent() {
        return (this == TOP_RIGHT || this == TOP || this == TOP_LEFT);
    }

    /**
     * Determines if the direction has a bottom component.
     * @return True if the direction has a bottom component, otherwise false.
     */
    public boolean hasBottomComponent() {
        return (this == BOTTOM_RIGHT || this == BOTTOM || this == BOTTOM_LEFT);
    }
}