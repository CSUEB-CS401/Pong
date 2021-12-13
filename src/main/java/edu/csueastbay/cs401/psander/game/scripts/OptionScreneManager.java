package edu.csueastbay.cs401.psander.game.scripts;

import edu.csueastbay.cs401.psander.PongWare;
import edu.csueastbay.cs401.psander.engine.scenes.SceneManager;
import edu.csueastbay.cs401.psander.engine.scripts.Script;
import edu.csueastbay.cs401.psander.game.scenes.BasicGameScene;

public class OptionScreneManager extends Script {
    @Override
    public void update(double delta) { }

    /**
     * Transitions to the previous game screne.
     */
    public void ReturnToPreviousScene() {
        SceneManager.getInstance().pop();
    }
}
