package edu.csueastbay.cs401.psander.engine.menu;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.input.InputManager;
import edu.csueastbay.cs401.psander.engine.input.MenuInputEvent;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.scripts.Script;

import java.util.ArrayList;
import java.util.List;

/**
 * A menu container acts as a coordinator for a list of
 * menu items. A list of game objects, a cursor, and an offset
 * can be provided. The menu items should all have
 * a script component deriving from MenuItem cn them, while the
 * cursor should be an object with a graphical component of
 * some kind.
 */
public class MenuContainer extends Script {

    private List<GameObject> _menuItems = new ArrayList<>();
    private GameObject _cursor;
    private Vector2D _cursorOffset;

    private int _index = 0;

    @Override
    public void update(double delta) {
        if (_menuItems.isEmpty()) return;

        setCursorPosition();

        // Updates of all the states (for animations and whatnot)
        var menuItems = _menuItems.stream()
                .map(go -> go.getComponent(MenuItem.class))
                .filter( m -> m != null)
                .toList();
        menuItems.forEach( mi -> mi.update(delta) );

        // So the main thing to do here is to check for input,
        // and then process that input in the appropriate way,
        // either by processing it or dispatching it to a menu item.

        if (_menuItems.isEmpty()) return; // Nothing to process.

        // Scrolling through the menu

        var moveAmount = 0;
        var instance = InputManager.getInstance();
        if (instance.isMenuInputPressed(MenuInputEvent.MENU_UP))
            moveAmount -= 1;
        if (instance.isMenuInputPressed(MenuInputEvent.MENU_DOWN))
            moveAmount += 1;

        var newIndex = _index + moveAmount;
        if (newIndex < 0)
            _index = 0;
        else if (newIndex >= _menuItems.size())
            _index = _menuItems.size() - 1;
        else
            _index = newIndex;

        // Now set the cursor to the right position
        setCursorPosition();

        // All other inputs dispatched by the menu item: left, right, enter, cancel
        var comp = _menuItems.get(_index).getComponent(MenuItem.class);
        if (comp != null)
        {
            var inputs = new ArrayList<MenuInputEvent>();
            if (instance.isMenuInputPressed(MenuInputEvent.MENU_LEFT)) inputs.add(MenuInputEvent.MENU_LEFT);
            if (instance.isMenuInputPressed(MenuInputEvent.MENU_RIGHT)) inputs.add(MenuInputEvent.MENU_RIGHT);
            if (instance.isMenuInputPressed(MenuInputEvent.MENU_CONFIRM)) inputs.add(MenuInputEvent.MENU_CONFIRM);
            if (instance.isMenuInputPressed(MenuInputEvent.MENU_CANCEL)) inputs.add(MenuInputEvent.MENU_CANCEL);
            comp.processMenuInput(inputs);
        }

    }

    /**
     * Adds a game object to the menu's list of menu items. Items
     * will be processed in the order added.
     * @param go The game object to add.
     */
    public void addMenuItem(GameObject go) {
        _menuItems.add(go);
    }

    /**
     * Sets the cursor to ue.
     * @param go A game object representing the cursor.
     */
    public void setCursor(GameObject go) {
        _cursor = go;
    }

    /**
     * Sets the offset amount of the cursor from the menu item.
     * @param offset The amount to offset the cursor by.
     */
    public void setOffset(Vector2D offset) {
        _cursorOffset = offset;
    }

    private void setCursorPosition() {
        if (_cursor == null) return;
        System.out.println("Current index is " + _index);
        var menuItemPos = _menuItems.get(_index).Transform().getWorldPosition();
        var newCursorPos = Vector2D.add(menuItemPos, _cursorOffset);
        _cursor.Transform().Position().set(newCursorPos);
    }
}
