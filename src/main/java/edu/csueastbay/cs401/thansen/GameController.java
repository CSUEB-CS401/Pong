package edu.csueastbay.cs401.thansen;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public static final int FIELD_WIDTH = 1300;
    public static final int FIELD_HEIGHT = 1300;
    public static final int LOSE_SCORE = 5;

    private FourWayPong game;

    @FXML
    AnchorPane fieldPane;
    @FXML
    Line line1;
    @FXML
    Line line2;
    @FXML
    Label playerOneScore;
    @FXML
    Label playerTwoScore;
    @FXML
    Label playerThreeScore;
    @FXML
    Label playerFourScore;
    @FXML
    Label gameOverLabel;

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
        System.out.println("Released: " + event.getCode());
        game.keyReleased(event.getCode());
    }

    private void setUpBindings() {
        // Hide score once corresponding player is eliminated.
        final Label[] scoreLabels = new Label[]{playerOneScore, playerTwoScore, playerThreeScore, playerFourScore};
        for (int i = 0; i < scoreLabels.length; ++i) {
            scoreLabels[i].textProperty().bind(game.playerScoreProperty(i + 1).asString());
            scoreLabels[i].visibleProperty().bind(game.playerAlive(i + 1).and(game.stillPlaying()));
        }

        // Display victor on game-over.
        final var victor = game.victorProperty();
        gameOverLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            final int value = victor.get();
            if (value < 1) return "";
            return "Player " + value + " wins!";
        }, victor));
        gameOverLabel.visibleProperty().bind(game.gameOver());

        // Remove divider lines on game-over.
        line1.visibleProperty().bind(game.stillPlaying());
        line2.visibleProperty().bind(game.stillPlaying());
    }

    private void setUpTimeline() {
        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), actionEvent -> game.move())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        // Stop the game on game-over.
        game.gameOver().addListener((ov, prev, next) -> {
            if (next) timeline.stop();
        });
    }
}
