package edu.csueastbay.cs401.vnguyen;

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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Used to load the scene. Added Key S,L to speed up the puck
 *
 * @see Puckable
 * @see Collidable
 * @see Animation
 * @see Platform
 * @see ActionEvent
 * @see EventHandler
 * @see FXML
 * @see Pos
 * @see Node
 * @see Label
 * @see KeyEvent
 * @see AnchorPane
 * @see Shape
 * @see Duration
 * @see ResourceBundle
 * @see ArrayList
 *
 */


public class GameController implements Initializable {
    /**
     * This is to set up scene and update the elements
     */
    public static final int FIELD_WIDTH = 1300;
    public static final int FIELD_HEIGHT = 860;
    public static final int VICTORY_SCORE = 10;

    private ViPong game;
    private Timeline timeline;
    private Boolean endFlag = false;

    @FXML
    AnchorPane fieldPane;
    @FXML
    Label playerOneScore;
    @FXML
    Label playerTwoScore;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new ViPong(VICTORY_SCORE, FIELD_WIDTH, FIELD_HEIGHT);
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
     * This keeps the scene running.
     * The screen will be freezed when there is a winner
     */

    private void setUpTimeline() {

        timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (endFlag==false) {//this will freeze the screen if one of the player reaches the victory score
                    game.move();
                    int victor = game.getVictor();
                    if (victor == 0) {
                        playerOneScore.setText(Integer.toString(game.getPlayerScore(1)));
                        playerTwoScore.setText(Integer.toString(game.getPlayerScore(2)));
                    }
                    else
                    {
                        if (victor == 1) {
                            playerOneScore.setText("Winner: " + Integer.toString(game.getPlayerScore(1)));
                            playerTwoScore.setText(Integer.toString(game.getPlayerScore(2)));
                        }
                        else
                        {
                            playerOneScore.setText(Integer.toString(game.getPlayerScore(1)));
                            playerTwoScore.setText("Winner: " + Integer.toString(game.getPlayerScore(2)));
                            playerTwoScore.setAlignment(Pos.CENTER_RIGHT);
                        }
                        game.playSound("src/main/java/edu/csueastbay/cs401/vnguyen/end.wav");
                        endFlag = true;
                    }
                }
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


}
