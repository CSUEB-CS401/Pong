package edu.csueastbay.cs401.psander.engine.render;

import edu.csueastbay.cs401.psander.engine.gameObjects.Component;
import javafx.scene.canvas.GraphicsContext;

/**
 * Base component class for all things rendering to the screen.
 */
public abstract class Renderer extends Component {
    GraphicsContext GraphicsContext;
    private int _layer;

    /**
     * Default constructor.
     */
    public Renderer() {}

    /**
     * Constructor that takes a layer.
     * @param layer The layer to render this component's graphics on.
     */
    public Renderer(int layer) {
        _layer = layer;
    }

    public int getLayer() { return _layer; }

    public void setLayer(int layer) { _layer = layer; }
}
