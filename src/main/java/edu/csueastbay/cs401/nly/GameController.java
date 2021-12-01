package edu.csueastbay.cs401.nly;


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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public static final int FIELD_WIDTH = 1300;
    public static final int FIELD_HEIGHT = 860;
    public static final int VICTORY_SCORE = 10;

    private NgonPong game;
    private Timeline timeline;

    @FXML
    AnchorPane fieldPane;
    @FXML
    Label playerOneScore;
    @FXML
    Label playerTwoScore;

    /**
     * This method will initialize a new game, add the elements to the field, and set duration/points.
     * @param url string for the correct package name
     * @param resourceBundle string for the correct resource directory
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new NgonPong(VICTORY_SCORE, FIELD_WIDTH, FIELD_HEIGHT);
        Platform.runLater(()->fieldPane.requestFocus());
        addGameElementsToField();
        setUpTimeline();

    }

    /**
     * This method will add all the elements onto the game field
     */
    private void addGameElementsToField() {
        ArrayList<Puckable> pucks = game.getPucks();
        pucks.forEach((puck) -> {
            fieldPane.getChildren().add((Node) puck);
        });

        ArrayList<Collidable> objects = game.getObjects();
        objects.forEach((object)-> {
            fieldPane.getChildren().add((Node) object);
        });

    }

    /**
     * This method will handle the case there a control key is pressed down and will move the element(s) accordingly
     * @param event the key input from external keyboard
     */
    @FXML
    public void keyPressed(KeyEvent event) {
        System.out.println("Pressed: " + event.getCode());
        game.keyPressed(event.getCode());
    }

    /**
     * This method will handle the case there a control key is released and will move the element(s) accordingly
     * @param event the key input from external keyboard
     */
    @FXML
    public void keyReleased(KeyEvent event) {
        game.keyReleased(event.getCode());
        System.out.println("Released: " + event.getCode());
    }

    /**
     * This method will initialize the game duration and display the player points
     */
    private void setUpTimeline() {

        timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.move();
                playerOneScore.setText(Integer.toString(game.getPlayerScore(1)));
                playerTwoScore.setText(Integer.toString(game.getPlayerScore(2)));
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


}

