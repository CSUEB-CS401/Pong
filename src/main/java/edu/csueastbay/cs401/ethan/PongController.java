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

/**
 * The primary Controller for Pong, handles the {@link PongGame} and {@link MenuPane}, as well as providing
 * {@link Game#input input} by implementing {@link InputHandler} and tracking {@link KeyEvent KeyEvents}
 */
public class PongController extends StackPane implements InputHandler {

    /** PongController's singleton instance */
    private static final PongController instance = new PongController();
    public static PongController getInstance() { return instance; }

    /**
     * Minimal controller for field.fxml adds singleton PongController to scene
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

    /** The set of keys currently being held */
    Set<KeyCode> heldKeys;
    /** The map of controls, control:keycode */
    ObservableMap<String, KeyCode> controls;

    /** The current game */
    ObjectProperty<PongGame> game;
    /** The pane which displays {@link PongController#game} */
    PongPane pongPane;
    /** The pane to manage menus in front of the game */
    MenuPane menuPane;

    /**
     * Creates a PongController with default controls and a new game.
     */
    public PongController() {
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("pong.css")).toExternalForm());

        heldKeys = new HashSet<>();
        // LinkedHashMaps maintain insertion order for iteration, which is useful for the controls menu
        Map<String, KeyCode> temp = new LinkedHashMap<>();
        temp.put("Player 1 Up", KeyCode.W);
        temp.put("Player 1 Down", KeyCode.S);
        temp.put("Player 2 Up", KeyCode.UP);
        temp.put("Player 2 Down", KeyCode.DOWN);
        temp.put("Pause/Back", KeyCode.ESCAPE);
        controls = FXCollections.observableMap(temp);

        focusedProperty().addListener((obj, oldVal, newVal)->{
            // When focus is lost, pause the game, open the menu, and request focus
            if(!newVal) {
                if(game.get() != null) game.get().setPlaying(false);
                if(!menuPane.isVisible()) {
                    menuPane.setVisible(true);
                    menuPane.setActiveMenu("PAUSED");
                }
                requestFocus();
            }
        });

        // set up KeyEventHandlers
        setOnKeyPressed(this::onKeyPressed);
        setOnKeyReleased(e->heldKeys.remove(e.getCode()));

        game = new SimpleObjectProperty<>();
        game.addListener((obs, oldVal, newVal)->{
            // When the game is changed, update pongPane and set the input
            newVal.input = this;
            pongPane.setGame(newVal);
        });

        pongPane = new PongPane();
        getChildren().add(pongPane);

        menuPane = createMenus();
        getChildren().add(menuPane);
    }

    /**
     * Helper method which builds the {@link MenuPane} and {@link MenuPane.Menu Menus} for Pong
     * @return the completed menu
     */
    private MenuPane createMenus() {
        MenuPane menuPane = new MenuPane();
        var menu = menuPane.getMenu("PAUSED");
        Button button;  // reusable temp button

        button = new Button("RESUME");
        button.setOnAction(e->{ // hide the menu and unpause the game
            menuPane.setVisible(false);
            game.get().setPlaying(true);
        });
        button.disableProperty().bind(game.isNull()); // disable if there's no game
        menu.addButton(button);

        button = new Button("NEW GAME");
        button.setOnAction(e->{ // create a new game and unpauses
            game.set(new PongGame());
            menuPane.setVisible(false);
            game.get().setPlaying(true);
        });
        menu.addButton(button);

        button = new Button("CONTROLS");
        button.setOnAction(e->menuPane.nextMenu("CONTROLS")); // show controls menu
        menu.addButton(button);

        menu = menuPane.getMenu("CONTROLS");
        for(String control : controls.keySet()) {
            Button controlButton = new Button();    // adding a button for each control
            // control button text is <key> or <...> if the key is null
            controlButton.textProperty().bind(Bindings.createStringBinding(
                    ()->(controls.get(control) != null) ? String.format("<%s>", controls.get(control).getName()) : "<...>",
                    controls));
            controlButton.setOnAction(actionEvent->rebind(control));    // rebind the control
            menu.addLabelledButton(control+": ", controlButton);
        }
        button = new Button("DONE");
        button.setOnAction(e->menuPane.previousMenu()); // transition to previous menu
        button.disableProperty().bind(rebinding);       // disable while a control is being rebound
        menu.addButton(button);

        return menuPane;
    }

    /** Handles KeyPressed events, navigating menu on "Pause/Back" input, else updating {@link PongController#heldKeys} */
    public void onKeyPressed(KeyEvent event) {
        if(event.getCode().equals(controls.get("Pause/Back"))) {
            if(game.get() != null && game.get().isPlaying()) {
                game.get().setPlaying(false);
                menuPane.setActiveMenu("PAUSED");
                menuPane.setVisible(true);
            } else if(menuPane.isVisible()) {
                if(!menuPane.previousMenu() && game.get() != null) {
                    menuPane.setVisible(false);
                    game.get().setPlaying(true);
                }
            }
        } else {
            heldKeys.add(event.getCode());
        }
    }

    /** Whether a key is currently being rebound */
    private final BooleanProperty rebinding = new SimpleBooleanProperty(false);
    /** Rebinds a control by intercepting the next KeyPressed event */
    private void rebind(String control) {
        if(rebinding.get()) return;     // only one rebinding at a time
        rebinding.set(true);
        var oldControl = controls.put(control, null);           // remove and store the current key
        var oldHandler = getOnKeyPressed();   // and the current handler
        setOnKeyPressed(event -> {  // add the rebinding EventHandler
            if(event.getCode().equals(controls.get("Pause/Back"))) {    // If the user cancels,
                controls.put(control, oldControl);                      // put the old key back
            } else {                                                    // else
                controls.put(control, event.getCode());                 // put the new one
            }
            setOnKeyPressed(oldHandler);    // restore the old listener
            rebinding.set(false);           // release the rebinding lock
        });
    }

    @Override
    public boolean isHeld(String control) {
        return heldKeys.contains(controls.get(control));
    }
}
