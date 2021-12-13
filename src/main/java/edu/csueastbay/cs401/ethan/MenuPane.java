package edu.csueastbay.cs401.ethan;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.NamedArg;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * MenuPanes handle the creation of and transitions between different {@link Menu Menus}
 */
public class MenuPane extends StackPane {
    /**
     * A floating menu with a title and buttons.
     * @see MenuPane#getMenu(String)
     */
    public static class Menu extends VBox {
        /** The title of this Menu */
        public final StringProperty title = new SimpleStringProperty();
        /** The rows of this Menu */
        public final ObservableList<Node> entries;

        @FXML
        private Label titleLabel;

        @FXML
        private VBox buttonVBox;

        /**
         * Creates a Menu with the default title.
         * @see MenuPane#createMenu(String)
         */
        private Menu() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                loader.setController(this);
                loader.setRoot(this);
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            titleLabel.textProperty().bind(title);
            entries = buttonVBox.getChildren();
            entries.clear();
        }

        /**
         * Creates a Menu with the given title.
         * @see MenuPane#createMenu(String)
         */
        private Menu(@NamedArg("title") String title) {
            this();
            this.title.set(title);
        }

        /**
         * Adds the given button as a row by itself.
         * @param button the button to add
         */
        public void addButton(Button button) {
            getChildren().add(button);
        }

        /**
         * Adds a row to the menu with the given button and a label with the given text
         * @param labelText the text for the label
         * @param button the button to add
         */
        public void addLabelledButton(String labelText, Button button) {
            HBox row = new HBox();
            Label label = new Label(labelText);
            label.setMinWidth(Control.USE_PREF_SIZE);   // can't do this in css :(
            HBox.setHgrow(label, Priority.ALWAYS);
            row.getChildren().addAll(label, button);
            getChildren().add(row);
        }
    }

    private Menu current;                   // the current menu, defaults to the first one created
    private final Map<String, Menu> menus;  // all the menus, stored by string id
    private final Stack<Menu> prevMenus;    // the stack of menus "previousMenu()" will cycle through

    /** Creates a MenuPane */
    public MenuPane() {
        getStyleClass().add("pause-pane");
        menus = new HashMap<>();
        prevMenus = new Stack<>();
    }

    /**
     * Creates a {@link Menu Menu} with the given id. If one already exists, does nothing.
     * @param id the identifier for the new Menu
     */
    public void createMenu(String id) {
        if(menus.containsKey(id)) return;

        Menu menu = new Menu(id);
        if(current == null) {
            current = menu;
            getChildren().add(menu);
        }
        menus.put(id, menu);
    }

    /**
     * Returns the {@link Menu Menu} with the given id. If one does not exist, {@link MenuPane#createMenu(String) creates} it.
     * @param id the identifier of the menu
     * @return the menu with the given id
     */
    public Menu getMenu(String id) {
        if(!menus.containsKey(id)) createMenu(id);
        return menus.get(id);
    }

    /**
     * Switches to the {@link Menu Menu} with the given id immediately, with no transition.
     * @param id the identifier for the new Menu
     */
    public void setActiveMenu(String id) {
        if(!menus.containsKey(id)) {
            System.err.println("Invalid id: "+id);
            return;
        }
        if(current != null) {
            getChildren().remove(current);
        }
        current = menus.get(id);
        getChildren().add(current);
    }

    /**
     * Transitions right to the {@link Menu Menu} with the given id.
     * @param id the identifier for the new Menu
     */
    public void nextMenu(String id) {
        if(!menus.containsKey(id)){
            System.err.println("Invalid id: "+id);
            return;
        }
        prevMenus.push(current);
        transition(menus.get(id), true);
    }

    /**
     * Transitions left to the previous {@link Menu Menu}.
     * @return whether there was a previous menu
     */
    public boolean previousMenu() {
        if(prevMenus.empty()) return false;
        transition(prevMenus.pop(), false);
        return true;
    }

    /**
     * Helper method for transitions. Smoothly transitions to the given menu, in the specified direction.
     * @param menu the new menu
     * @param right if the new menu should be to the right of the current menu
     */
    private void transition(Menu menu, boolean right) {
        if(menu == current) return;
        double offset = right ? getWidth() : -getWidth();
        Menu prev = current;
        current = menu;
        current.setTranslateX(offset);
        getChildren().add(current);
        var transition = new Timeline(new KeyFrame(Duration.seconds(0.25),
                new KeyValue(prev.translateXProperty(), -offset, Interpolator.EASE_BOTH),
                new KeyValue(current.translateXProperty(), 0, Interpolator.EASE_BOTH)));
        transition.setOnFinished((event)->{
            getChildren().remove(prev);
            prev.setTranslateX(0);
        });
        transition.play();
    }

}
