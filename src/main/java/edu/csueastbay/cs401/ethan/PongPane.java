package edu.csueastbay.cs401.ethan;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/** Simple class to display a {@link PongGame} */
public class PongPane extends StackPane {

    @FXML
    private Group entityGroup;

    @FXML
    private Label p1Score;

    @FXML
    private Label p2Score;

    /**
     * Creates a new PongPane and loads the appropriate layout
     * @see PongPane#setGame(PongGame)
     */
    public PongPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pong_pane.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Sets the {@link PongGame} this PongPane should display
     * @param game the game to display
     */
    public void setGame(PongGame game) {
        entityGroup.getChildren().setAll(game.pane);
        p1Score.textProperty().bind(game.p1Score.asString());
        p2Score.textProperty().bind(game.p2Score.asString());
    }
}
