package edu.csueastbay.cs401.jzepeda;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Puck;
import edu.csueastbay.cs401.pong.Puckable;
import edu.csueastbay.cs401.pong.Wall;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * FXML Controller Class For Game Field.
 */
public class myGameController implements Initializable {
    public static final int FIELD_WIDTH = 1300;
    public static final int FIELD_HEIGHT = 860;
    public static final int VICTORY_SCORE = 10;

    private myPongGame game;
    private Timeline timeline;

    @FXML
    BorderPane baseBorderPane;
    @FXML
    AnchorPane fieldPane;
    @FXML
    Label playerOneScore;
    @FXML
    Label playerTwoScore;
    @FXML
    ImageView backgroundImage;

    /**
     * Initialize Method For Timeline.
     * - Code Provided By Professor.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new myPongGame(VICTORY_SCORE, FIELD_WIDTH, FIELD_HEIGHT);

        addGameElementsToField();
        setUpTimeline();
    }

    /**
     * Elements Such As Pucks/Walls Added To Game Field Here.
     * - Code Provided By Professor.
     */
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

    /**
     * Methods For Key Pressing/Releasing Events For Paddle Events.
     * - Code Provided By Professor.
     */
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
     * Main Timeline Initialized Here.
     * - Code Provided By Professor.
     */
    private void setUpTimeline() {

        timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fieldPane.requestFocus();
                game.move();
                playerOneScore.setText(Integer.toString(game.getPlayerScore(1)));
                playerTwoScore.setText(Integer.toString(game.getPlayerScore(2)));
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * When The 'Add A Puck' Button Is Pressed, This Method Initializes A New Puck
     * And Adds It Onto The Playing Field. Infinite Pucks Can Be Added.
     *
     * @param event On Button Press.
     */
    @FXML
    public void addPuckButtonPressed(ActionEvent event) {

        // Creating New Puck And Adding To Game ...
        Puck test = new Puck(FIELD_WIDTH, FIELD_HEIGHT);
        fieldPane.getChildren().add(test);
        game.addPuck(test);
    }

    /**
     * When The 'Add Moving Barriers' Button Is Pressed, This Method Will
     * Initialize 2 New Walls With Moving/Rotating Attributes. These Walls
     * Are ID'd As 'Paddles' To Enforce The Collision When A Puck Hits It.
     *
     * @param event On Button Press.
     */
    @FXML
    public void addBarriers(ActionEvent event) {

        /**
         * Wall For Player 1 Side Is Initialized Here.
         */
        Wall barrier1 = new Wall("Paddle", 400, 200, 10, 80);
        barrier1.setFill(Color.DARKRED);
        fieldPane.getChildren().add(barrier1);
        game.addObject(barrier1);

        /**
         * Wall For Player 2 Side Is Initialized Here.
         */
        Wall barrier2 = new Wall("Paddle", 900, 700, 10, 80);
        barrier2.setFill(Color.DARKRED);
        fieldPane.getChildren().add(barrier2);
        game.addObject(barrier2);

        /**
         * Y-Value Moving + Rotation Attributes Declared Here, Using A Timeline
         * To Modify The Attributes Over A Given Time Period Indefinitely.
         */
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        final KeyValue barr1 = new KeyValue(barrier1.yProperty(), 700);
        final KeyValue barr1R = new KeyValue(barrier1.rotateProperty(), 800);
        final KeyValue barr2 = new KeyValue(barrier2.yProperty(), 200);
        final KeyValue barr2R = new KeyValue(barrier2.rotateProperty(), 800);
        final KeyFrame bar1 = new KeyFrame(Duration.millis(1000), barr1);
        final KeyFrame bar2 = new KeyFrame(Duration.millis(1000), barr2);
        final KeyFrame bar2R = new KeyFrame(Duration.millis(1000), barr2R);
        final KeyFrame bar1R = new KeyFrame(Duration.millis(1000), barr1R);
        timeline.getKeyFrames().add(bar1);
        timeline.getKeyFrames().add(bar2);
        timeline.getKeyFrames().add(bar1R);
        timeline.getKeyFrames().add(bar2R);
        timeline.play();
    }
}
