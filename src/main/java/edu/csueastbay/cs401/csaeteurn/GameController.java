/**
 * 1st Improvement: Rematch button in actionButtonPress method
 * 2nd Improvement: Victory Screen in setupTimeline
 * 3rd Improvement: Background Image
 * 4th Improvement: Player win streak indicator in setupTimeline
 * 5th Improvement: Player 1 & 2 Label
 */

package edu.csueastbay.cs401.csaeteurn;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Puckable;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public static final int FIELD_WIDTH = 1300;
    public static final int FIELD_HEIGHT = 860;
    public static final int VICTORY_SCORE = 10;
    public Label player2Streak;
    public Label player1Streak;

    private SquidPong game;
    private Timeline timeline;

    @FXML
    AnchorPane fieldPane;
    @FXML
    Label playerOneScore;
    @FXML
    Label playerTwoScore;
    @FXML
    Label gameOver;
    @FXML
    Button restartGame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new SquidPong(VICTORY_SCORE, FIELD_WIDTH, FIELD_HEIGHT);
        Platform.runLater(() -> fieldPane.requestFocus());
        addGameElementsToField();
        setUpTimeline();
    }

    private void addGameElementsToField() {
        //adding the game objects to the game
        ArrayList<Puckable> pucks = game.getPucks();
        pucks.forEach((puck) -> {
            fieldPane.getChildren().add((Node) puck);
        });

        ArrayList<Collidable> objects = game.getObjects();
        objects.forEach((object) -> {
            fieldPane.getChildren().add((Node) object);
        });
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
    /**
     * Includes Victory screen
     * Player win stream
     * Ends game
     * Indicates the winner of the game
     */
    private void setUpTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.move();
                playerOneScore.setText(Integer.toString(game.getPlayerScore(1)));
                playerTwoScore.setText(Integer.toString(game.getPlayerScore(2)));

                game.setVictoryScore(3);
                // Victory Screen, win streak and stop game
                if (game.getPlayerScore(1) == game.getVictoryScore()) {
                    gameOver.setText("Player 1 Wins!");
                    player1Streak.setText(Integer.toString(game.getWinStreak(1)));
                    timeline.stop();
                }

                if (game.getPlayerScore(2) == game.getVictoryScore()) {
                    gameOver.setText("Player 2 Wins!");
                    player2Streak.setText(Integer.toString(game.getWinStreak(2)));
                    timeline.stop();
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * This object restarts the game using a button named REMATCH
     * Sets both player score to 0
     * Clears the victory message
     */
    @FXML
    void actionButtonPress(ActionEvent event) {
        while (game.getPlayerScore(1) > 0 || game.getPlayerScore(2) > 0 ){
            if (game.getPlayerScore(1) > 0)
                game.addPointsToPlayer(1,-1);
            if (game.getPlayerScore(2) > 0)
                game.addPointsToPlayer(2,-1);
        }
        timeline.play();
        gameOver.setText("");
    }
}