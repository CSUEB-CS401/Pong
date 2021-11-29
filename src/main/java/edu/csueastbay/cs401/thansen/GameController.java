package edu.csueastbay.cs401.thansen;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public static final int FIELD_WIDTH = 1300;
    public static final int FIELD_HEIGHT = 860;
    public static final int LOSE_SCORE = 10;

    private FourWayPong game;

    @FXML
    AnchorPane fieldPane;
    @FXML
    Label playerOneScore;
    @FXML
    Label playerTwoScore;
    @FXML
    Label playerThreeScore;
    @FXML
    Label playerFourScore;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new FourWayPong(LOSE_SCORE, FIELD_WIDTH, FIELD_HEIGHT);
        Platform.runLater(() -> fieldPane.requestFocus());
        addGameElementsToField();
        setUpBindings();
        setUpTimeline();
    }

    private void addGameElementsToField() {
        game.getPucks().forEach(puck -> fieldPane.getChildren().add((Node) puck));
        game.getObjects().forEach(object -> fieldPane.getChildren().add((Node) object));
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        System.out.println("Pressed: " + event.getCode());
        game.keyPressed(event.getCode());
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        game.keyReleased(event.getCode());
        System.out.println("Released: " + event.getCode());
    }

    private void setUpBindings() {
        playerOneScore.textProperty().bind(game.playerScoreProperty(1).asString());
        playerTwoScore.textProperty().bind(game.playerScoreProperty(2).asString());
        playerThreeScore.textProperty().bind(game.playerScoreProperty(3).asString());
        playerFourScore.textProperty().bind(game.playerScoreProperty(4).asString());
    }

    private void setUpTimeline() {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            game.move();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
