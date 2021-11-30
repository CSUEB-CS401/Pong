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

public class MenuPane extends StackPane {
    public static class Menu extends VBox {
        public final StringProperty title = new SimpleStringProperty();
        public final ObservableList<Node> entries;

        @FXML
        private Label titleLabel;

        @FXML
        private VBox buttonVBox;

        public Menu() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                loader.setController(this);
                loader.setRoot(this);
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            titleLabel.textProperty().bind(title);
            buttonVBox.getChildren().clear();
            entries = buttonVBox.getChildren();
        }

        public Menu(@NamedArg("title") String title) {
            this();
            this.title.set(title);
        }

        public void addButton(Button button) {
            getChildren().add(button);
        }

        public void addLabelledButton(String labelText, Button button) {
            HBox row = new HBox();
            Label label = new Label(labelText);
            label.setMinWidth(Control.USE_PREF_SIZE);   // can't do this in css :(
            HBox.setHgrow(label, Priority.ALWAYS);
            row.getChildren().addAll(label, button);
            getChildren().add(row);
        }
    }

    private Menu current;
    private final Map<String, Menu> menus;
    private final Stack<Menu> prevMenus;

    public MenuPane() {
        getStyleClass().add("pause-pane");
        menus = new HashMap<>();
        prevMenus = new Stack<>();
    }

    public void createMenu(String id) {
        if(menus.containsKey(id)) return;

        Menu menu = new Menu(id);
        if(current == null) {
            current = menu;
            getChildren().add(menu);
        }
        menus.put(id, menu);
    }

    public Menu getMenu(String id) {
        if(!menus.containsKey(id)) createMenu(id);
        return menus.get(id);
    }

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

    public void nextMenu(String id) {
        if(!menus.containsKey(id)){
            System.err.println("Invalid id: "+id);
            return;
        }
        prevMenus.push(current);
        transition(menus.get(id), true);
    }

    public boolean previousMenu() {
        if(prevMenus.empty()) return false;
        transition(prevMenus.pop(), false);
        return true;
    }

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
