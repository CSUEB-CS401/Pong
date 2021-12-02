package edu.csueastbay.cs401.psander.game.scenes;

import edu.csueastbay.cs401.psander.PongWare;
import edu.csueastbay.cs401.psander.engine.common.HorizontalAlignment;
import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.menu.ActionItem;
import edu.csueastbay.cs401.psander.engine.menu.MenuContainer;
import edu.csueastbay.cs401.psander.engine.render.RectangleRenderer;
import edu.csueastbay.cs401.psander.engine.render.TextRenderer;
import edu.csueastbay.cs401.psander.engine.scenes.Scene;
import edu.csueastbay.cs401.psander.game.scripts.TitleScreenManager;
import javafx.scene.paint.Color;

public class TitleScreenScene extends Scene {
    @Override
    public void init() {
        var manager = new GameObject("manager");
        var managerComponent = new TitleScreenManager();
        manager.addComponent(managerComponent);
        addGameObject(manager);

        var title = new GameObject("title");
        title.Transform().Position().set(PongWare.getFieldWidth() / 2, 200);
        title.addComponent(new TextRenderer("PONG", Color.WHITE,
                50, 0, HorizontalAlignment.CENTER));
        addGameObject(title);

        // Menu
        var horizontalMenuPosition = 600;
        var startGameOption = new GameObject("start game");
        startGameOption.Transform().Position().set(horizontalMenuPosition, 500);
        startGameOption.addComponent(new TextRenderer("Start Game", Color.WHITE, 20));
        var startMenuItem = new ActionItem();
        startMenuItem.setAction(managerComponent::TransitionToGameScreen);
        startGameOption.addComponent(startMenuItem);
        addGameObject(startGameOption);

        var optionMenu = new GameObject("option menu");
        optionMenu.Transform().Position().set(horizontalMenuPosition, 550);
        optionMenu.addComponent(new TextRenderer("Options", Color.WHITE, 20));
        var optionMenuItem = new ActionItem();
        optionMenuItem.setAction(managerComponent::TransitionToOptionsMenu);
        optionMenu.addComponent(optionMenuItem);
        addGameObject(optionMenu);

        var cursor = new GameObject("cursor");
        cursor.Transform().Position().set(-50, -50);
        cursor.addComponent(new RectangleRenderer(10, 20, Color.GREEN));
        addGameObject(cursor);

        var menuController = new GameObject("menu controller");
        var menuContainer = new MenuContainer();
        menuContainer.addMenuItem(startGameOption);
        menuContainer.addMenuItem(optionMenu);
        menuContainer.setCursor(cursor);
        menuContainer.setOffset(new Vector2D(-15, 5));
        menuController.addComponent(menuContainer);
        addGameObject(menuController);
    }
}
