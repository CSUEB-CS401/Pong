package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Game;
import edu.csueastbay.cs401.ethan.game.InputHandler;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.*;

public class PongController extends StackPane implements InputHandler {

    private static final PongController instance = new PongController();
    public static PongController getInstance() { return instance; }

    /**
     * Minimal controller adds singleton PongController to scene
     */
    public static class Landing implements Initializable {
        @FXML
        private SubScene subScene;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            Group root = new Group();
            root.getChildren().add(PongController.getInstance());
            subScene.setRoot(root);
            Platform.runLater(PongController.getInstance()::requestFocus);
        }
    }

    Set<KeyCode> heldKeys;
    ObservableMap<String, KeyCode> controls;

    ObjectProperty<PongGame> game;
    PongPane pongPane;
    MenuPane menuPane;

    public PongController() {
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("pong.css")).toExternalForm());

        heldKeys = new HashSet<>();
        Map<String, KeyCode> temp = new LinkedHashMap<>();
        temp.put("Player 1 Up", KeyCode.W);
        temp.put("Player 1 Down", KeyCode.S);
        temp.put("Player 2 Up", KeyCode.UP);
        temp.put("Player 2 Down", KeyCode.DOWN);
        temp.put("Pause/Back", KeyCode.ESCAPE);
        controls = FXCollections.observableMap(temp);

        focusedProperty().addListener((obj, oldVal, newVal)->{
            if(!newVal) {
                if(game.get() != null) game.get().setPlaying(false);
                if(!menuPane.isVisible()) {
                    menuPane.setVisible(true);
                    menuPane.setActiveMenu("PAUSED");
                }
                requestFocus();
            }
        });

        setOnKeyPressed(this::onKeyPressed);
        setOnKeyReleased(e->heldKeys.remove(e.getCode()));

        game = new SimpleObjectProperty<>();
        game.addListener((obs, oldVal, newVal)->{
            newVal.input = this;
            pongPane.setGame(newVal);
        });

        pongPane = new PongPane();
        getChildren().add(pongPane);

        menuPane = createMenus();
        getChildren().add(menuPane);

        game.set(new PongGame());
    }

    private MenuPane createMenus() {
        MenuPane menuPane = new MenuPane();
        var menu = menuPane.getMenu("PAUSED");
        Button button;

        button = new Button("RESUME");
        button.setOnAction(e->{
            menuPane.setVisible(false);
            game.get().setPlaying(true);
        });
        button.disableProperty().bind(Bindings.selectBoolean(game, "gameOver"));
        menu.addButton(button);

        button = new Button("NEW GAME");
        button.setOnAction(e->{
            game.set(new PongGame());
            menuPane.setVisible(false);
            game.get().setPlaying(true);
        });
        menu.addButton(button);

        button = new Button("CONTROLS");
        button.setOnAction(e->menuPane.nextMenu("CONTROLS"));
        menu.addButton(button);

        menu = menuPane.getMenu("CONTROLS");
        for(String control : controls.keySet()) {
            Button controlButton = new Button();
            controlButton.textProperty().bind(Bindings.createStringBinding(
                    ()->(controls.get(control) != null) ? String.format("<%s>", controls.get(control).getName()) : "<...>",
                    controls));

            controlButton.setOnAction(actionEvent->rebind(control));
            menu.addLabelledButton(control+": ", controlButton);
        }
        button = new Button("DONE");
        button.setOnAction(e->menuPane.previousMenu());
        button.disableProperty().bind(rebinding);
        menu.addButton(button);

        return menuPane;
    }

    public void onKeyPressed(KeyEvent event) {
        if(event.getCode().equals(controls.get("Pause/Back"))) {
            if(game.get().isPlaying()) {
                game.get().setPlaying(false);
                menuPane.setActiveMenu("PAUSED");
                menuPane.setVisible(true);
            } else if(menuPane.isVisible()) {
                if(!menuPane.previousMenu()) {
                    menuPane.setVisible(false);
                    game.get().setPlaying(true);
                }
            }
        } else {
            heldKeys.add(event.getCode());
        }
    }

    private final BooleanProperty rebinding = new SimpleBooleanProperty(false);
    private void rebind(String control) {
        if(rebinding.get()) return;
        rebinding.set(true);
        var oldControl = controls.put(control, null);
        var oldHandler = getOnKeyPressed();
        setOnKeyPressed(event -> {
            if(event.getCode().equals(controls.get("Pause/Back"))) {
                controls.put(control, oldControl);
            } else {
                controls.put(control, event.getCode());
            }
            setOnKeyPressed(oldHandler);
            rebinding.set(false);
        });
    }

    @Override
    public boolean isHeld(String control) {
        return heldKeys.contains(controls.get(control));
    }
}
