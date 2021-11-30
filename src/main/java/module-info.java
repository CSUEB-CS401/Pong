module edu.csueastbay.cs401.pong {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens edu.csueastbay.cs401.pong to javafx.fxml;
    opens edu.csueastbay.cs401.classic to javafx.fxml;
    opens edu.csueastbay.cs401.thansen to javafx.fxml;
    opens edu.csueastbay.cs401.ethan to javafx.fxml;
    opens edu.csueastbay.cs401.ethan.game to javafx.base;
    opens edu.csueastbay.cs401.nly to javafx.fxml;
    opens edu.csueastbay.cs401.StarWarsPong to javafx.fxml;
    opens edu.csueastbay.cs401.frantic to javafx.fxml;
    opens edu.csueastbay.cs401.srishti to javafx.fxml;
    exports edu.csueastbay.cs401.pong;
}
