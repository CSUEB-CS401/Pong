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

    }

    @FXML
    public void keyPressed(KeyEvent event) {
        //System.out.println("Pressed: " + event.getCode());
        game.keyPressed(event.getCode());
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        game.keyReleased(event.getCode());
        //System.out.println("Released: " + event.getCode());
    }

    public void UpdateField()
    {
        if(game.SpawnSpeed())
        {
            System.out.println("Speed Upgrade Spawned");
          fieldPane.getChildren().add(game.getSpeedUpgrades());

        }
        if(game.RemoveSpeedSpawn())
        {
            System.out.println("Speed Upgrade Despawned");
            fieldPane.getChildren().remove(game.getSpeedUpgrades());

        }

        if(game.SpawnHeight())
        {
            System.out.println("Height Upgrade spawned");
            fieldPane.getChildren().add(game.getHeightUpgrades());
        }
        if(game.RemoveHeightSpawn())
        {
            System.out.println("Height Upgrade Despawned");
            fieldPane.getChildren().remove(game.getHeightUpgrades());

        }

    }


    private void setUpTimeline() {

        timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UpdateField();
                game.move();
                playerOneScore.setText(Integer.toString(game.getPlayerScore(1)));
                playerTwoScore.setText(Integer.toString(game.getPlayerScore(2)));
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


}
