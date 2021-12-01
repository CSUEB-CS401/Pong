package edu.csueastbay.cs401.srishti;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * This class invokes PuckEXT by calling the constructor
 * PuckEXT increases the size of the Puck
 * Adding background music as a change
 */

public class GameControllerEXT implements Initializable {
    public static final int FIELD_WIDTH = 1300;
    public static final int FIELD_HEIGHT = 860;
    public static final int VICTORY_SCORE = 10;

    private ClassicPongEXT game;
    private Timeline timeline;

    @FXML
    AnchorPane fieldPane;
    @FXML
    Label playerOneScore;
    @FXML
    Label playerTwoScore;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Added Path for audio file
        String path = "C:\\Users\\14082\\IdeaProjects\\Pong\\src\\main\\resources\\edu\\csueastbay\\cs401\\srishti\\pongMusic\\pongMusic.mp3";
        // Instantiating Media class
        Media media = new Media(new File(path).toURI().toString());
        // Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        // by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(false);
        mediaPlayer.setAutoPlay(true);
        game = new ClassicPongEXT(VICTORY_SCORE, FIELD_WIDTH, FIELD_HEIGHT);
        Platform.runLater(() -> fieldPane.requestFocus());
        addGameElementsToField();
        setUpTimeline();

    }

    private void addGameElementsToField() {
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
