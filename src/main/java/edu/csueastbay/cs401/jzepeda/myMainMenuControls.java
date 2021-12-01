package edu.csueastbay.cs401.jzepeda;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.Objects;

/**
 * Class Controller For The Starting Main Menu Screen.
 * Shown Before The Game Starts Upon Clicking 'play' Button.
 */
public class myMainMenuControls {

    @FXML
    private BorderPane baseBorderPane;

    @FXML
    ImageView imageLogo;

    @FXML
    ImageView imageView;

    @FXML
    ImageView pongLOGO;

    /**
     * Scene Changer Method For Main Menu -> Game Field FXML Files.
     *
     * @param event On Button Press.
     */
    @FXML
    void playPong(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/csueastbay/cs401/jzepeda/myPongField.fxml")));
            baseBorderPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}