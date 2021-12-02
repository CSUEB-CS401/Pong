package edu.csueastbay.cs401.psander.engine.render;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Comparator;
import java.util.List;

/**
 * Manages all of the rendering to screen for the game.
 */
public class RenderManager {

    private static RenderManager _instance;

    private double _screenWidth;
    private double _screenHeight;
    private GraphicsContext _graphicsContext;

    private RenderManager() {}

    public static RenderManager getInstance() {
        if (_instance == null)
            _instance = new RenderManager();
        return _instance;
    }

    /**
     * Reinitializes the render manager. Required to
     * reinitialize the singleton instance.
     * @param width   The width of the play field.
     * @param height  The height of the play field.
     * @param context The graphics context to render graphics to.
     */
    public void init(double width, double height, GraphicsContext context) {
        _screenWidth = width;
        _screenHeight = height;
        _graphicsContext = context;
    }

    /**
     * Render all graphics for the given frame.
     * @param delta       The amount of time since the last frame, in seconds.
     * @param gameObjects A list of game objects whose components we need to render.
     */
    public void update(double delta, List<GameObject> gameObjects) {
        // Blank the screen
        _graphicsContext.setFill(Color.BLACK);
        _graphicsContext.fillRect(0, 0, _screenWidth, _screenHeight);

        // Get render components and invoke them
        var renderables = gameObjects.stream()
                .map( go -> go.getComponent(Renderer.class) )
                .filter( r -> r != null )
                .sorted(Comparator.comparingInt(Renderer::getLayer))
                .toList();

        for(var r : renderables) {
            r.GraphicsContext = _graphicsContext;
            r.update(delta);
        }
    }
}
