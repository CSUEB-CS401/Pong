package edu.csueastbay.cs401.psander.game.scenes;

import edu.csueastbay.cs401.psander.PongWare;
import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.render.TextRenderer;
import edu.csueastbay.cs401.psander.engine.scenes.Scene;
import edu.csueastbay.cs401.psander.game.scripts.GameOverManager;
import edu.csueastbay.cs401.psander.game.scripts.TitleScreenManager;
import javafx.scene.paint.Color;

public class GameOverScene extends Scene {
    @Override
    public void init() {
        var manager = new GameObject("manager");
        manager.addComponent(new GameOverManager());
        addGameObject(manager);

        var title = new GameObject("winner");
        title.Transform().Position().set(300, 300);
        title.addComponent(new TextRenderer("Player " + PongWare.State().get_winningPlayer() + " won!",
                Color.WHITE, 50, 0));
        addGameObject(title);

        var instruction = new GameObject("instruction");
        instruction.Transform().Position().set(300, 500);
        instruction.addComponent(new TextRenderer("Press [Escape] to quit", Color.WHITE, 25, 0));
        addGameObject(instruction);
    }
}
