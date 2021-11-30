package edu.csueastbay.cs401.DlinPong;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PongMenuController{
    public Toggle t = new Toggle();

    @FXML
    private AnchorPane PongMenuField;

    @FXML
    public TextField portalToggle;

    @FXML
    void portalButton(ActionEvent event) {
        if (portalToggle.getText().equals("ON")){
            portalToggle.setText("OFF");
            t.setToggle(0);
        } else if(portalToggle.getText().equals("OFF")) {
            portalToggle.setText("ON");
            t.setToggle(1);
        }
    }

    @FXML
    void startButton(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("pongField.fxml"));
        PongMenuField.getChildren().setAll(pane);
    }

}
