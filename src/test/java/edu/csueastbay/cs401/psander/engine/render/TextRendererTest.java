package edu.csueastbay.cs401.psander.engine.render;

import edu.csueastbay.cs401.psander.engine.common.HorizontalAlignment;
import edu.csueastbay.cs401.psander.engine.common.VerticalAlignment;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextRendererTest {

    @Test
    public void TestConstructors() {
        var r1 = new TextRenderer("test1");
        assertEquals("test1", r1.getText());

        var r2 = new TextRenderer("test2", Color.BLUE);
        assertEquals("test2", r2.getText());
        assertEquals(Color.BLUE, r2.getColor());

        var r3 = new TextRenderer("test3", Color.BLUE, 17);
        assertEquals("test3", r3.getText());
        assertEquals(Color.BLUE, r3.getColor());
        assertEquals(17, r3.getFontSize());

        var r4 = new TextRenderer("test4", Color.BLUE, 17, 23);
        assertEquals("test4", r4.getText());
        assertEquals(Color.BLUE, r4.getColor());
        assertEquals(17, r4.getFontSize());
        assertEquals(23, r4.getLayer());

        var r5 = new TextRenderer("test5", Color.BLUE, 17, 23,
                HorizontalAlignment.CENTER);
        assertEquals("test5", r5.getText());
        assertEquals(Color.BLUE, r5.getColor());
        assertEquals(17, r5.getFontSize());
        assertEquals(23, r5.getLayer());
        assertEquals(HorizontalAlignment.CENTER, r5.getHorizontalAlignment());

        var r6 = new TextRenderer("test6", Color.BLUE, 17, 23,
                HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        assertEquals("test6", r6.getText());
        assertEquals(Color.BLUE, r6.getColor());
        assertEquals(17, r6.getFontSize());
        assertEquals(23, r6.getLayer());
        assertEquals(HorizontalAlignment.CENTER, r6.getHorizontalAlignment());
        assertEquals(VerticalAlignment.CENTER, r6.getVerticalAlignment());
    }

    // No tests for this, since this involves drawing graphics
    @Test
    public void TestUpdate() {

    }

    @Test
    public void TestGettersAndSetters() {
        var rend = new TextRenderer("test");

        rend.setText("new text");
        assertEquals("new text", rend.getText());

        rend.setColor(Color.BLUE);
        assertEquals(Color.BLUE, rend.getColor());

        rend.setFontSize(31);
        assertEquals(31, rend.getFontSize());

        rend.setLayer(47);
        assertEquals(47, rend.getLayer());

        rend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        assertEquals(HorizontalAlignment.CENTER, rend.getHorizontalAlignment());

        rend.setVerticalAlignment(VerticalAlignment.CENTER);
        assertEquals(VerticalAlignment.CENTER, rend.getVerticalAlignment());
    }
}
