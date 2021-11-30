package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.pong.Goal;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
        // Hide score once corresponding player is eliminated.
        final Label[] scoreLabels = new Label[]{playerOneScore, playerTwoScore, playerThreeScore, playerFourScore};
        final BooleanBinding[] aliveBindings = new BooleanBinding[4];
        for (int i = 0; i < scoreLabels.length; ++i) {
            scoreLabels[i].textProperty().bind(game.playerScoreProperty(i + 1).asString());
            aliveBindings[i] = game.playerAlive(i + 1);
            scoreLabels[i].visibleProperty().bind(aliveBindings[i]);
        }

        // Hide paddle once corresponding player is eliminated.
        game.getObjects().stream().filter(obj -> obj.getType().endsWith("Paddle")).forEach(paddle -> {
            final int player = switch (paddle.getID()) {
                case "Player 1 Paddle" -> 1;
                case "Player 2 Paddle" -> 2;
                case "Player 3 Paddle" -> 3;
                case "Player 4 Paddle" -> 4;
                default -> 0;
            };
            if (player < 1) return;
            ((Node) paddle).visibleProperty().bind(aliveBindings[player - 1]);
        });

        // Reset goal color once corresponding player is eliminated.
        game.getObjects().stream().filter(obj -> obj.getType().equals("Goal")).forEach(goal -> {
            final int player = switch (goal.getID()) {
                case "Player 1 Goal" -> 1;
                case "Player 2 Goal" -> 2;
                case "Player 3 Goal" -> 3;
                case "Player 4 Goal" -> 4;
                default -> 0;
            };
            if (player < 1) return;
            final var aliveBinding = aliveBindings[player - 1];
            final var initialColor = ((Goal) goal).getFill();
            ((Goal) goal).fillProperty().bind(
                    Bindings.createObjectBinding(
                            () -> aliveBinding.get() ? initialColor : Color.WHITE,
                            aliveBinding
                    )
            );
        });
    }

    private void setUpTimeline() {
        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), actionEvent -> game.move())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
