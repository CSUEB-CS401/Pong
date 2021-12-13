package edu.csueastbay.cs401.psander.engine.menu;

import edu.csueastbay.cs401.psander.engine.input.MenuInputEvent;

import java.util.List;

public class ActionItem extends MenuItem {

    private Runnable _runnable;

    @Override
    public void update(double delta) {

    }

    @Override
    public void processMenuInput(List<MenuInputEvent> events) {
        for (var event : events) {
            if (event == MenuInputEvent.MENU_CONFIRM && _runnable != null)
                _runnable.run();
        }
    }

    /**
     * The action to execute when this item receives a confirm command.
     */
    public void setAction(Runnable runnable) {
        _runnable = runnable;
    }
}
