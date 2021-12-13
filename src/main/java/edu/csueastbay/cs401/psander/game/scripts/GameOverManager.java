package edu.csueastbay.cs401.psander.game.scripts;

import edu.csueastbay.cs401.psander.PongWare;
import edu.csueastbay.cs401.psander.engine.input.InputManager;
import edu.csueastbay.cs401.psander.engine.scenes.SceneManager;
import edu.csueastbay.cs401.psander.engine.scripts.Script;
import edu.csueastbay.cs401.psander.game.scenes.BasicGameScene;
import edu.csueastbay.cs401.psander.game.scenes.TitleScreenScene;
import javafx.scene.input.KeyCode;

public class GameOverManager extends Script {
    @Override
    public void update(double delta) {
        if (InputManager.getInstance().isKeyPressed(KeyCode.ESCAPE)) {
            var titleScreen = new TitleScreenScene();
            titleScreen.init();
            SceneManager.getInstance().swap(titleScreen);
        }
    }
}
