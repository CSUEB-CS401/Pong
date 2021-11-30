package edu.csueastbay.cs401.jzepeda;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.Objects;

public class myMainMenuControls {

    @FXML
    private BorderPane baseBorderPane;

    @FXML
    ImageView imageLogo;

    @FXML
    ImageView imageView;

    @FXML
    ImageView pongLOGO;

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
