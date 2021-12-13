package edu.csueastbay.cs401.psander.engine.scenes;

import java.util.Optional;
import java.util.Stack;

/**
 * <p>The Scene Manager controls and organizes the currently running
 * 'scene', allowing different game screens or modes to be
 * differentiated from each other.</p>
 *
 * <p>This works as a stack, with newly-added scenes becoming
 * the active scene while other previously-added ones
 * become dormant.</p>
 */
public class SceneManager {

    private static SceneManager _instance = null;

    private final Stack<Scene> _scenes = new Stack<>();

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (_instance == null)
            _instance = new SceneManager();

        return _instance;
    }

    /**
     * Reinitialize the Scene Manager instance. Required
     * because it is implemented as a singleton.
     */
    public void init() {
        _scenes.clear();
    }

    /**
     * Performs the update step, triggering any
     * processing that needs to be done once a frame.
     * @param delta The elapsed time since the previous
     *              frame, in seconds.
     */
    public void update(double delta) {
        if (_scenes.isEmpty()) return;

        var curr = _scenes.peek();
        curr.update(delta);
    }

    /**
     * Performs any steps or processing
     * required after the main update step
     * is performed.
     */
    public void postUpdate() {}

    /**
     * Pushes a scene to the top of the
     * stack, making it the currently active
     * scene.
     * @param scene The scene to make the active
     *              scene.
     */
    public void push(Scene scene) {
        _scenes.push(scene);
    }


    /**
     * Returns the top scene in the scene stack
     * without removing it from execution.
     * @return An optional containing the current
     * scene if there is one, or an empty one if there
     * isn't.
     */
    public Optional<Scene> peek() {
        if (_scenes.isEmpty())
            return Optional.empty();
        var scene = _scenes.peek();
        return Optional.of(scene);
    }

    /**
     * Removes the top (currently running) scene
     * from the stack of scenes.
     * @throws UnsupportedOperationException
     * Thrown if an attempt is made to pop the stack
     * while it is empty.
     */
    public void pop() {
        if (_scenes.isEmpty())
            throw new UnsupportedOperationException();
        _scenes.pop();
    }

    /**
     * Swaps the top (currently running) scene
     * for a new one, which will become the new
     * active scene.
     * @param scene The scene to place at the top
     *              of the stack.
     * @throws UnsupportedOperationException
     * Thrown if an attempt is made to pop the stack
     * while it is empty.
     */
    public void swap(Scene scene) {
        if (_scenes.isEmpty())
            throw new UnsupportedOperationException();
        _scenes.pop();
        _scenes.push(scene);
    }
}
