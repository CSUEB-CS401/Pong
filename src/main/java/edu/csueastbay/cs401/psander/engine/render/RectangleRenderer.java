package edu.csueastbay.cs401.psander.engine.render;

import javafx.scene.paint.Color;

/**
 * A renderer that draws a single flat-color rectangle to the screen.
 */
public class RectangleRenderer extends Renderer {

    private Double _height;
    private Double _width;
    private Color _color;

    /**
     * Constructor for a rectangle renderer that takes width,
     * height, and color.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @param color  The color of the rectangle.
     */
    public RectangleRenderer(double width, double height, Color color) {
        _height = height;
        _width = width;
        _color = color;
    }

    /**
     * Constructor for the rectangle renderer that takes width,
     * height, color, and layer.
 * @param width      The width of the rectangle.
     * @param height The height of the rectangle.
     * @param color  The color of the rectangle.
     * @param layer  The layer to render the rectangle on.
     */
    public RectangleRenderer(double width, double height, Color color, int layer) {
        super(layer);
        _height = height;
        _width = width;
        _color = color;
    }

    public double getWidth() { return _width; }

    public void setWidth(double width) { _width = width; }

    public double getHeight() { return _height; }

    public void setHeight(double height) { _height = height; }

    public Color getColor() { return _color; }

    public void setColor(Color color) { _color = color; }

    @Override
    public void update(double delta) {
        var gc = this.GraphicsContext;

        var pos = getOwner().Transform().getWorldPosition();

        gc.setFill(_color);
        gc.fillRect(pos.X(), pos.Y(), _width, _height);
    }
}
