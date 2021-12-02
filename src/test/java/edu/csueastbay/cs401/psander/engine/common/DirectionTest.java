package edu.csueastbay.cs401.psander.engine.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {
    @Test
    public void TestDirectionMethods() {

        // Right direction
        assertFalse(Direction.NONE.hasRightComponent());
        assertTrue(Direction.RIGHT.hasRightComponent());
        assertTrue(Direction.TOP_RIGHT.hasRightComponent());
        assertFalse(Direction.TOP.hasRightComponent());
        assertFalse(Direction.TOP_LEFT.hasRightComponent());
        assertFalse(Direction.LEFT.hasRightComponent());
        assertFalse(Direction.BOTTOM_LEFT.hasRightComponent());
        assertFalse(Direction.BOTTOM.hasRightComponent());
        assertTrue(Direction.BOTTOM_RIGHT.hasRightComponent());

        // Left direction
        assertFalse(Direction.NONE.hasLeftComponent());
        assertFalse(Direction.RIGHT.hasLeftComponent());
        assertFalse(Direction.TOP_RIGHT.hasLeftComponent());
        assertFalse(Direction.TOP.hasLeftComponent());
        assertTrue(Direction.TOP_LEFT.hasLeftComponent());
        assertTrue(Direction.LEFT.hasLeftComponent());
        assertTrue(Direction.BOTTOM_LEFT.hasLeftComponent());
        assertFalse(Direction.BOTTOM.hasLeftComponent());
        assertFalse(Direction.BOTTOM_RIGHT.hasLeftComponent());

        // Top direction
        assertFalse(Direction.NONE.hasTopComponent());
        assertFalse(Direction.RIGHT.hasTopComponent());
        assertTrue(Direction.TOP_RIGHT.hasTopComponent());
        assertTrue(Direction.TOP.hasTopComponent());
        assertTrue(Direction.TOP_LEFT.hasTopComponent());
        assertFalse(Direction.LEFT.hasTopComponent());
        assertFalse(Direction.BOTTOM_LEFT.hasTopComponent());
        assertFalse(Direction.BOTTOM.hasTopComponent());
        assertFalse(Direction.BOTTOM_RIGHT.hasTopComponent());

        // Bottom direction
        assertFalse(Direction.NONE.hasBottomComponent());
        assertFalse(Direction.RIGHT.hasBottomComponent());
        assertFalse(Direction.TOP_RIGHT.hasBottomComponent());
        assertFalse(Direction.TOP.hasBottomComponent());
        assertFalse(Direction.TOP_LEFT.hasBottomComponent());
        assertFalse(Direction.LEFT.hasBottomComponent());
        assertTrue(Direction.BOTTOM_LEFT.hasBottomComponent());
        assertTrue(Direction.BOTTOM.hasBottomComponent());
        assertTrue(Direction.BOTTOM_RIGHT.hasBottomComponent());

        // Horizontal direction
        assertFalse(Direction.NONE.hasHorizontalComponent());
        assertTrue(Direction.RIGHT.hasHorizontalComponent());
        assertTrue(Direction.TOP_RIGHT.hasHorizontalComponent());
        assertFalse(Direction.TOP.hasHorizontalComponent());
        assertTrue(Direction.TOP_LEFT.hasHorizontalComponent());
        assertTrue(Direction.LEFT.hasHorizontalComponent());
        assertTrue(Direction.BOTTOM_LEFT.hasHorizontalComponent());
        assertFalse(Direction.BOTTOM.hasHorizontalComponent());
        assertTrue(Direction.BOTTOM_RIGHT.hasHorizontalComponent());

        // Vertical direction
        assertFalse(Direction.NONE.hasVerticalComponent());
        assertFalse(Direction.RIGHT.hasVerticalComponent());
        assertTrue(Direction.TOP_RIGHT.hasVerticalComponent());
        assertTrue(Direction.TOP.hasVerticalComponent());
        assertTrue(Direction.TOP_LEFT.hasVerticalComponent());
        assertFalse(Direction.LEFT.hasVerticalComponent());
        assertTrue(Direction.BOTTOM_LEFT.hasVerticalComponent());
        assertTrue(Direction.BOTTOM.hasVerticalComponent());
        assertTrue(Direction.BOTTOM_RIGHT.hasVerticalComponent());
    }
}
