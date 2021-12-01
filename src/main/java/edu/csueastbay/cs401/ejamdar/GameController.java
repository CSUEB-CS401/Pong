package edu.csueastbay.cs401.ejamdar;
import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.PongApplication;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 * GameController is the FXML class that assists with adding
 * elements to the screen as well as overall directing how
 * GUI elements are show and how GUI events are handled.
 * @author Eshaq Jamdar
 * @version 1.0
 * @see GameController
 */
public class GameController implements Initializable {

    /**
     * represents the width of the game plane
     */
    public static final int FIELD_WIDTH = 1300;
    /**
     * represents the height of the game plane
     */
    public static final int FIELD_HEIGHT = 860;
    /**
     * the victory score to win (unused)
     */
    public static final int VICTORY_SCORE = 10;
    /**
     * instance of JamdarPong
     */
    private JamdarPong game;
    /**
     * TimeLine instance
     */
    private Timeline timeline;
    /**
     * MediaPlayer instance
     */
    private MediaPlayer mediaPlayer;



    /**
     * fieldPane represents the current background of the game
     */
    @FXML
    AnchorPane fieldPane;
    /**
     * playerOne's score on screen
     */
    @FXML
    Label playerOneScore;
    /**
     * playerTwo's score on screen
     */
    @FXML
    Label playerTwoScore;
    /**
     * muteButton access variable
     */
    @FXML
    private Button muteButton;
    /**
     * exit button access variable
     */

    @FXML
    private Button exitButton;
    /**
     * boolean value for the mediaPlayer
     */
    private boolean isPlaying = true;

    /**
     * Method initialize will start the JavaFX scene
     * <p>
     * Initialize will first play the background song, make a new instance of game,
     * put the game in focus, add the elements of the field and sets up the timeline.
     * @param url of type URL
     * @param resourceBundle of type ResourceBundle
     * @see GameController
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            backgroundMusic(1);
            game = new JamdarPong(VICTORY_SCORE, FIELD_WIDTH, FIELD_HEIGHT);

            Platform.runLater(() -> fieldPane.requestFocus());
            addGameElementsToField();
            setUpTimeline();

        }catch(Exception e){
            System.out.println(e);
        }

    }
    /**
     * Method addGameElementsToField will add the pucks for the game onto the screen
     * <p>
     * Method will obtain the pucks arraylist and loop through every puck and add it to the field.
     * In addition, it will also add any other game objects such as paddles.
     * @see GameController
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
     * Method backgroundMusic
     * Will take in a number and based off it, play the correct sound.
     * <p>
     * Method takes in a number and depending on it, will play it.
     * Choice 1 will play the background song, choice 2 plays the point award sfx and
     * choice 3 will play the paddle/wall hitting sound.
     * @param num represents the sound file to be played: int
     * @see GameController
     */
    public void backgroundMusic(int num){
        if (num == 1) {
            Media media = new Media(getClass().getResource("/edu/csueastbay/cs401/ejamdar/menu.wav").toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }else if (num == 2){
            Media media = new Media(getClass().getResource("/edu/csueastbay/cs401/ejamdar/point.wav").toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }else if (num == 3){
            Media media = new Media(getClass().getResource("/edu/csueastbay/cs401/ejamdar/hit.wav").toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }
    /**
     * Method keyPress will print out the clicked keyPress to the console (debug)
     * <p>
     * Method will print to the screen the keyPress and then register the keyPress with
     * the instance of JamdarPong to register user input.
     * @param event of type KeyEvent representing an ASCII value on the keyboard.
     * @see GameController
     */
    @FXML
    public void keyPressed(KeyEvent event) {
        System.out.println("Pressed: " + event.getCode());
        game.keyPressed(event.getCode());
    }

    /**
     * Method keyReleased will print out the clicked keyRelease to the console (debug)
     * <p>
     * Ditto to the previous method but with the key that was let go.
     * @param event of type KeyEvent representing an ASCII value on the keyboard.
     * @see GameController
     */
    @FXML
    public void keyReleased(KeyEvent event) {
        game.keyReleased(event.getCode());
        System.out.println("Released: " + event.getCode());
    }

    /**
     * Method setUpTimeLine will help with initializing the initial screen
     * <p>
     * Method will make a new Timeline and from there, call upon a helper function
     * handle that will setScore for both players to their initial value of 0 and start the movement in the game.
     * @see GameController
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
    /**
     * Method mute will mute every instance of MediaPlayer
     * <p>
     * Method will check if the mediaPlayer is currently on and if so, pause the instances of mediaPlayer. If the button is
     * pressed and it is off, it is turned back on.
     * <b>NOTE:</b> Only mutes the stage music and not the sfx of the paddles or points.
     * @param event representing event based actions from the GUI
     * @see GameController
     */
    @FXML
    void mute(ActionEvent event) {
        if(isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        }else if (!isPlaying){
            mediaPlayer.play();
           isPlaying = true;
        }
    }

    /**
     * Method exit will exit gracefully from the program
     * <p>
     * Method will stop the mediaPlayer from playing and will take the current source window
     * and close the stage
     * <b>NOTE:</b> This may not be a proper close cause the sound effects for paddle and the puck continue to
     * play unless both windows are closed.
     * @param event representing event based actions from the GUI
     * @see GameController
     */
    @FXML
    void exit(ActionEvent event) throws IOException {
        mediaPlayer.stop();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
