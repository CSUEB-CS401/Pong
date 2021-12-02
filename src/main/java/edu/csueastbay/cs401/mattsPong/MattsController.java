package edu.csueastbay.cs401.mattsPong;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Puck;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.DoubleToIntFunction;

public class MattsController implements Initializable {

    public static final int FIELD_WIDTH = 1300;
    public static final int FIELD_HEIGHT = 860;
    public static final int VICTORY_SCORE = 10;

    private MattsGame game;
    private Timeline timeline;
    private long timeTest;
    private long currTime = 0;
    private int puckCounter = 1;

    @FXML
    AnchorPane fieldPane;
    @FXML
    Label playerOneScore;
    @FXML
    Label playerTwoScore;
    @FXML
    Label playerOneBlocks;
    @FXML
    Label playerTwoBlocks;
    @FXML
    Label timer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new MattsGame(VICTORY_SCORE, FIELD_WIDTH, FIELD_HEIGHT);
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
            System.out.println(object.getID());
            fieldPane.getChildren().add((Node) object);
        });

    }

    // Adds a puck if 30 seconds have passed. Use currTime and puckCounter to make sure
    // only one puck is created every 30 seconds since timeline will run multiple times in a second.
    void addExtraPucks(){
        timeTest = game.getTime(game.startTime) % 30;
        if (currTime != timeTest){
            currTime = timeTest;
            puckCounter = 0;
        }else if (currTime == 0 && puckCounter == 0){
            Puck timerPuck = new Puck(FIELD_WIDTH, FIELD_HEIGHT);
            timerPuck.setID("Timer Puck");
            game.addPuck(timerPuck);
            fieldPane.getChildren().add((Node) timerPuck);
            puckCounter = 1;
        }
    }

    // Removes a "Timer Puck" if it scores a goal and removes it from the ArrayList.
    private void removeExtraPucks(){
        ArrayList<Puckable> pucks = game.getPucks();
        ArrayList<Collidable> objects = game.getObjects();
        Iterator<Puckable> itr = pucks.iterator();
        while(itr.hasNext()) {
            Puckable puck = itr.next();
            objects.forEach((object) -> {
                Collision collision = object.getCollision((Shape) puck);
                if (collision.isCollided() && Objects.equals(collision.getType(), "Goal") && puck.getID() == "Timer Puck") {
                    game.collisionHandler(puck, collision);
                    fieldPane.getChildren().remove((Node) puck);
                    game.removePuck(puck);
                }
            });
        }
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
                removeExtraPucks();
                playerOneScore.setText("Score: "+Integer.toString(game.getPlayerScore(1)));
                playerTwoScore.setText("Score: "+Integer.toString(game.getPlayerScore(2)));
                timer.setText(game.getTime(game.startTime) / 60 +":"+(game.getTime(game.startTime) % 60));
                playerOneBlocks.setText("Blocks: "+Integer.toString(game.getPlayerBlockScore(1)));
                playerTwoBlocks.setText("Blocks: "+Integer.toString(game.getPlayerBlockScore(2)));
                addExtraPucks();
                game.checkPowerUpDuration();
            }

        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


}
