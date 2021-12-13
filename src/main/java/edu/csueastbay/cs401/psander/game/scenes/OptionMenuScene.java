package edu.csueastbay.cs401.psander.game.scenes;

import edu.csueastbay.cs401.psander.PongWare;
import edu.csueastbay.cs401.psander.engine.common.HorizontalAlignment;
import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.input.InputEvent;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.menu.ActionItem;
import edu.csueastbay.cs401.psander.engine.menu.MenuContainer;
import edu.csueastbay.cs401.psander.engine.menu.SetKeybindingMenuItem;
import edu.csueastbay.cs401.psander.engine.render.RectangleRenderer;
import edu.csueastbay.cs401.psander.engine.render.TextRenderer;
import edu.csueastbay.cs401.psander.engine.scenes.Scene;
import edu.csueastbay.cs401.psander.game.scripts.OptionScreneManager;
import javafx.scene.paint.Color;

public class OptionMenuScene extends Scene {
    @Override
    public void init() {
        var manager = new GameObject("option screen manager");
        var managerComponent = new OptionScreneManager();
        manager.addComponent(managerComponent);
        addGameObject(manager);



        var cursor = new GameObject("cursor");
        cursor.Transform().Position().set(-50, -50);
        cursor.addComponent(new RectangleRenderer(10, 20, Color.GREEN));
        addGameObject(cursor);

        var menuController = new GameObject("menu controller");
        var menuContainer = new MenuContainer();
        menuContainer.addMenuItem(generateMenuOption(300, 200, InputEvent.PLAYER_1_UP));
        menuContainer.addMenuItem(generateMenuOption(300, 250, InputEvent.PLAYER_1_DOWN));
        menuContainer.addMenuItem(generateMenuOption(300, 300, InputEvent.PLAYER_2_UP));
        menuContainer.addMenuItem(generateMenuOption(300, 350, InputEvent.PLAYER_2_DOWN));
        menuContainer.setCursor(cursor);
        menuContainer.setOffset(new Vector2D(-15, 5));
        menuController.addComponent(menuContainer);
        addGameObject(menuController);

        // Back item
        var goBackOption = new GameObject("back");
        goBackOption.Transform().Position().set(300, 400);
        goBackOption.addComponent(new TextRenderer("Go Back", Color.WHITE, 20));
        var goBackMenuItem = new ActionItem();
        goBackMenuItem.setAction(managerComponent::ReturnToPreviousScene);
        goBackOption.addComponent(goBackMenuItem);
        menuContainer.addMenuItem(goBackOption);
        addGameObject(goBackOption);

        var title = new GameObject("title");
        title.Transform().Position().set(PongWare.getFieldWidth() / 2, 100);
        title.addComponent(new TextRenderer("Options", Color.WHITE,
                50, 0, HorizontalAlignment.CENTER));
        addGameObject(title);
    }

    private GameObject generateMenuOption(double x, double y, InputEvent input ) {
        var go = new GameObject("keybinding menu item");
        go.Transform().Position().set(new Vector2D(x, y));
        var menuItem = new SetKeybindingMenuItem(input);
        var renderer = new TextRenderer("", Color.WHITE, 20);
        menuItem.setTextRenderer(renderer);
        go.addComponent(renderer);
        go.addComponent(menuItem);
        addGameObject(go);
        return go;
    }
}
