package edu.csueastbay.cs401.pong;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private Registry registry;

    @FXML
    private BorderPane baseBorderPane;
    @FXML
    private Label titleLabel;
    @FXML
    private VBox studentGameList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        registry = new Registry();
        registry.register("Anthony'S Pong", "Anthony", "Anthony's Pong Game");
        registry.register("Classic Pong", "classic", "Classic Pong Game");


        registry.reset();

        while (registry.next()) {
            Button gameButton = new Button();
            gameButton.setText(registry.getStudentName());
            gameButton.setPrefWidth(180);
            gameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    loadGamePane("/edu/csueastbay/cs401/" + registry.getPackageName() +"/field.fxml");
                }
            });
            studentGameList.getChildren().add(gameButton);
        }
    }

    private void loadGamePane(String template) {

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(template));
            baseBorderPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
