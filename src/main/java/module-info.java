module edu.csueastbay.cs401.pong {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.csueastbay.cs401.pong to javafx.fxml;
    opens edu.csueastbay.cs401.classic to javafx.fxml;
    opens edu.csueastbay.cs401.Anthony to javafx.fxml;
    exports edu.csueastbay.cs401.pong;
}