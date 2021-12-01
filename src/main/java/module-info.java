module edu.csueastbay.cs401.pong {
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.controls;

    opens edu.csueastbay.cs401.pong to javafx.fxml, javafx.graphics, javafx.media;
    opens edu.csueastbay.cs401.classic to javafx.fxml;
    opens edu.csueastbay.cs401.felixchoypong to javafx.fxml, javafx.graphics, javafx.media;
    opens edu.csueastbay.cs401.khlPong to javafx.fxml;
    opens edu.csueastbay.cs401.srishti to javafx.fxml;
    opens edu.csueastbay.cs401.thansen to javafx.fxml;
    opens edu.csueastbay.cs401.ethan to javafx.fxml;
    opens edu.csueastbay.cs401.ejamdar to javafx.fxml;
    opens edu.csueastbay.cs401.ethan.game to javafx.base;
    opens edu.csueastbay.cs401.nly to javafx.fxml;
    opens edu.csueastbay.cs401.StarWarsPong to javafx.fxml;
    opens edu.csueastbay.cs401.frantic to javafx.fxml;
    opens edu.csueastbay.cs401.jzepeda to javafx.fxml;

    exports edu.csueastbay.cs401.pong;
}