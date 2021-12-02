package edu.csueastbay.cs401.ggamata2011;

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
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Spawns In Game Elements and controls Timeline of game
 */
public class GameController implements Initializable {
    public static final int FIELD_WIDTH = 1300;
    public static final int FIELD_HEIGHT = 860;
    public static final int VICTORY_SCORE = 10;

    private Pong2TheSequel game;
    private Timeline timeline;

    @FXML
    AnchorPane fieldPane;
    @FXML
    Label playerOneScore;
    @FXML
    Label playerTwoScore;

    /**
     * Initializes Game Elements and request focus on the fieldpane
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new Pong2TheSequel(VICTORY_SCORE, FIELD_WIDTH, FIELD_HEIGHT);
        Platform.runLater(()->fieldPane.requestFocus());
        addGameElementsToField();
        setUpTimeline();
    }


    private void addGameElementsToField() {
        ArrayList<Puckable> pucks = game.getPucks();
        pucks.forEach((puck) -> {
            fieldPane.getChildren().add((Node) puck);
        });

        ArrayList<Collidable> objects = game.getObjects();
        objects.forEach((object)-> {
            fieldPane.getChildren().add((Node) object);
        });

        fieldPane.getChildren().add(game.getSpeedUpgrades());
        fieldPane.getChildren().add(game.getHeightUpgrades());
        fieldPane.getChildren().add(game.getDebuff());

    }

    /**
     * Gets Keypress
     * @param event keypress
     */
    @FXML
    public void keyPressed(KeyEvent event) {
        game.keyPressed(event.getCode());
    }

    /**
     * Gets KeyRelease
     * @param event KeyRelease
     */
    @FXML
    public void keyReleased(KeyEvent event) {
        game.keyReleased(event.getCode());
    }


    /**
     * Sets Up Game timeline and keeps the game running
     */
    private void setUpTimeline() {

        timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.move();

                if((game.getVictor() != 1) || (game.getVictor() != 2)) {
                    playerOneScore.setText(Integer.toString(game.getPlayerScore(1)));
                    playerTwoScore.setText(Integer.toString(game.getPlayerScore(2)));
                }
                if (game.getVictor() == 1)
                {
                    System.out.println("Player 1 Won!");
                    playerOneScore.setText("P1!");
                }
                if (game.getVictor() == 2)
                {
                    System.out.println("Player 2 Won!");
                    playerTwoScore.setText("P2!");
                }
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


}
