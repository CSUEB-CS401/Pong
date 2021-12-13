package edu.csueastbay.cs401.psander;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the JavaFX FieldPane. Responsible for
 * retrieving an instance of PongWare and directing keyboard
 * input events to it.
 */
public class GameController implements Initializable {
    private PongWare _game;

    @FXML
    AnchorPane _fieldPane;

    @FXML
    Canvas _canvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _game = PongWare.getInstance();
        Platform.runLater(()-> _fieldPane.requestFocus());
        _game.init(_canvas.getGraphicsContext2D());
        _fieldPane.focusedProperty().addListener((obj, oldVal, newVal) -> {
            _game.setPlaying(newVal);
        });
    }

    /**
     * Accepts keyboard input and dispatches it to the game
     * object for handling.
     * @param event The current key event being processed.
     */
    @FXML
    public void keyPressed(KeyEvent event) {
        System.out.println("Pressed: " + event.getCode());
        _game.keyDown(event.getCode());
    }

    /**
     * Accepts keyboard input and dispatches it to the game
     * object for handling.
     * @param event The current key event being processed.
     */
    @FXML
    public void keyReleased(KeyEvent event) {
        _game.keyUp(event.getCode());
        System.out.println("Released: " + event.getCode());
    }
}
