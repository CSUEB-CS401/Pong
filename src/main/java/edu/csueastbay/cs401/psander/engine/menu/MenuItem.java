package edu.csueastbay.cs401.psander.engine.menu;

import edu.csueastbay.cs401.psander.engine.gameObjects.Component;
import edu.csueastbay.cs401.psander.engine.input.MenuInputEvent;

import java.util.List;

/**
 * An abstract base class for a menu item component.
 */
public abstract class MenuItem extends Component {
    /**
     * Process the specified menu input.
     * @param events The events to process.
     */
    public abstract void processMenuInput(List<MenuInputEvent> events);
}
