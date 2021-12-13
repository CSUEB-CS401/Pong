package edu.csueastbay.cs401.psander.engine.render;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RendererTest {
    static class RendererMock extends Renderer {

        @Override
        public void update(double delta) {

        }
    }
    @Test
    public void testGettersAndSetters() {
        var rend = new RendererMock();

        assertEquals(0, rend.getLayer());

        rend.setLayer(13);
        assertEquals(13, rend.getLayer());
    }
}
