package edu.csueastbay.cs401.psander.engine.render;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RectangleRendererTest {

    @Test
    public void TestConstructors() {
        var rect1 = new RectangleRenderer(1.0, 2.0, Color.RED);
        assertEquals(1.0, rect1.getWidth());
        assertEquals(2.0, rect1.getHeight());
        assertEquals(Color.RED, rect1.getColor());
        assertEquals(0, rect1.getLayer());

        var rect2 = new RectangleRenderer(3.0, 4.0, Color.PURPLE, 5);
        assertEquals(3.0, rect2.getWidth());
        assertEquals(4.0, rect2.getHeight());
        assertEquals(Color.PURPLE, rect2.getColor());
        assertEquals(5, rect2.getLayer());
    }

    @Test
    public void TestGettersAndSetters() {
        var rect = new RectangleRenderer(1.0, 2.0, Color.RED);

        assertEquals(1.0, rect.getWidth());
        assertEquals(2.0, rect.getHeight());
        assertEquals(Color.RED, rect.getColor());

        rect.setWidth(3.0);
        assertEquals(3.0, rect.getWidth());
        rect.setHeight(4.0);
        assertEquals(4.0, rect.getHeight());
        rect.setColor(Color.BLUE);
        assertEquals(Color.BLUE, rect.getColor());
    }

    // Not testing the update method, since it involves rendering to the screen.
    @Test
    public void TestUpdates() {

    }
}
