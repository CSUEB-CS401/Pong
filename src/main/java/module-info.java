module edu.csueastbay.cs401.pong {
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.controls;

    opens edu.csueastbay.cs401.pong to javafx.fxml, javafx.graphics, javafx.media;
    opens edu.csueastbay.cs401.classic to javafx.fxml;
    opens edu.csueastbay.cs401.felixchoypong to javafx.fxml, javafx.graphics, javafx.media;

    exports edu.csueastbay.cs401.pong to javafx.fxml;
    exports edu.csueastbay.cs401.felixchoypong to javafx.fxml;
    opens edu.csueastbay.cs401.StarWarsPong to javafx.fxml;

    exports edu.csueastbay.cs401.pong;
}