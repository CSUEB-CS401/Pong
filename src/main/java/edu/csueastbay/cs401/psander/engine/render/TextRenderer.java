package edu.csueastbay.cs401.psander.engine.render;

import edu.csueastbay.cs401.psander.engine.common.HorizontalAlignment;
import edu.csueastbay.cs401.psander.engine.common.VerticalAlignment;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Rendering component that renders text to the screen.
 */
public class TextRenderer extends Renderer {

    private String _text;
    private Color _color = Color.WHITE;
    private double _fontSize = 16;
    private HorizontalAlignment _hAlign = HorizontalAlignment.LEFT;
    private VerticalAlignment _vAlign = VerticalAlignment.TOP;

    /**
     * Constructor that takes the text to render.
     * @param text The text to render.
     */
    public TextRenderer(String text) {
        _text = text;
    }

    /**
     * Constructor that takes the text and the color to render it in.
     * @param text  The text to render.
     * @param color The color to render the text in.
     */
    public TextRenderer(String text, Color color) {
        _text = text;
        _color = color;
    }

    /**
     * A constructor that takes the text and the color and font size to render it in.
     * @param text     The text to render.
     * @param color    The color to render the text in.
     * @param fontSize The size to render the text at.
     */
    public TextRenderer(String text, Color color, int fontSize) {
        _text = text;
        _color = color;
        _fontSize = fontSize;
    }

    /**
     * A constructor that takes the text and the color, font size,
     * and layer to render text with.
     * @param text     The text to render.
     * @param color    The color to render the text in.
     * @param fontSize The size to render the text at.
     * @param layer    The layer to render the text on.
     */
    public TextRenderer(String text, Color color, double fontSize, int layer) {
        super(layer);
        _text = text;
        _color = color;
        _fontSize = fontSize;
    }

    /**
     * A constructor that takes the text and the color, font size,
     * layer, and horizontal alignment to render text with.
     * @param text     The text to render.
     * @param color    The color to render the text in.
     * @param fontSize The size to render the text at.
     * @param layer    The layer to render the text on.
     * @param hAlign   The horizontal alignment to render the text with.
     */
    public TextRenderer(String text, Color color, double fontSize, int layer, HorizontalAlignment hAlign) {
        super(layer);
        _text = text;
        _color = color;
        _fontSize = fontSize;
        _hAlign = hAlign;
    }

    /**
     * A constructor that takes the text and the color, font size,
     * layer, horizontal and vertical alignment to render text with.
     * @param text     The text to render.
     * @param color    The color to render the text in.
     * @param fontSize The size to render the text at.
     * @param layer    The layer to render the text on.
     * @param hAlign   The horizontal alignment to render the text with.
     * @param vAlign   The vertical alignment to render the text with.
     */
    public TextRenderer(String text, Color color, double fontSize, int layer, HorizontalAlignment hAlign,
                        VerticalAlignment vAlign) {
        super(layer);
        _text = text;
        _color = color;
        _fontSize = fontSize;
        _hAlign = hAlign;
        _vAlign = vAlign;
    }

    @Override
    public void update(double delta) {
        var gc = this.GraphicsContext;

        var pos = getOwner().Transform().getWorldPosition();
        gc.setFill(_color);
        gc.setFont(new Font(_fontSize));

        TextAlignment hAlign;
        switch(_hAlign) {
            case LEFT -> hAlign = TextAlignment.LEFT;
            case CENTER -> hAlign = TextAlignment.CENTER;
            case RIGHT -> hAlign = TextAlignment.RIGHT;
            default -> throw new IllegalArgumentException();
        }
        gc.setTextAlign(hAlign);

        VPos vAlign;
        switch(_vAlign) {
            case TOP -> vAlign = VPos.TOP;
            case CENTER -> vAlign = VPos.CENTER;
            case BOTTOM -> vAlign = VPos.BOTTOM;
            default -> throw new IllegalArgumentException();
        }
        gc.setTextBaseline(vAlign);

        gc.fillText(_text, pos.X(), pos.Y());
    }

    public String getText() { return _text; }

    public void setText(String text) { _text = text; }

    public Color getColor() { return _color; }

    public void setColor(Color color) { _color = color; }

    public double getFontSize() { return _fontSize; }

    public void setFontSize(double fontSize) { _fontSize = fontSize; }

    public HorizontalAlignment getHorizontalAlignment() { return _hAlign; }

    public void setHorizontalAlignment(HorizontalAlignment hAlign) { _hAlign = hAlign; }

    public VerticalAlignment getVerticalAlignment() { return _vAlign; }

    public void setVerticalAlignment(VerticalAlignment vAlign) { _vAlign = vAlign; }
}
