package edu.csueastbay.cs401.martinle;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Puckable;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    public static final boolean IS_GAME_OVER = false;

    private RPGpong game;
    private Timeline timeline;

    @FXML
    AnchorPane fieldPane;
    @FXML
    Label playerOneHP;
    @FXML
    Label playerTwoHP;
    @FXML
    Label gameResult;

    /**
    * Initialization point
    * sets up elements to field
    * sets up timeline
    * prints initialization results
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Martin's DarkPONG Game initialize!");
        game = new RPGpong(IS_GAME_OVER, FIELD_WIDTH, FIELD_HEIGHT);

        addGameElementsToField();
        setUpTimeline();
        System.out.println("Martin's DarkPONG Game has been initialized!");
        System.out.println("Martin's DarkPONG Game timeline set!");
        System.out.println("All your pucks are belong to us...");
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

    private void clearAllGameElementsOnField() {
        ArrayList<Puckable> pucks = game.getPucks();
        pucks.clear();

        ArrayList<Collidable> objects = game.getObjects();
        objects.clear();

        fieldPane.getChildren().clear();
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


    /*
    * sets up timeline
    * also checks game status if player has won or cpu did
    * displays game result on field pane
    * */
    private void setUpTimeline() {

        timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fieldPane.requestFocus();
                game.move();
                playerOneHP.setText(Integer.toString(game.getPlayerHP(1)));
                playerTwoHP.setText(Integer.toString(game.getPlayerHP(2)));
                if(game.getGameStatus()){
                    //set text of game status to game result
                    if(game.getVictor() == 1){
                        gameResult.setText("ENEMY DEFEATED");
                    }else if(game.getVictor() == 2){
                        gameResult.setText("ALL YOUR PUCKS ARE BELONG TO US...");
                    }
                }
            }
        }, new javafx.animation.KeyValue[]{}));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


}