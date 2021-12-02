package edu.csueastbay.cs401.psander.engine.scripts;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;

import java.util.List;

/**
 * Executes all game scripts during the processing
 * of a frame of gameplay.
 */
public class ScriptManager {

    /**
     * Processes all updates for game scripts during
     * processing of a frame of gameplay.
     * @param delta The amount of time since the last frame, in seconds.
     * @param gameObjects The list of game objects to have their scripts evaluated.
     */
    public void update(double delta, List<GameObject> gameObjects) {
        for(var go : gameObjects) {
            var s = go.getComponent(Script.class);
            if (s != null)
                s.update(delta);
        }
    }
}
