module edu.csueastbay.cs401.pong {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;

    opens edu.csueastbay.cs401.pong to javafx.fxml, javafx.graphics, javafx.media;
    opens edu.csueastbay.cs401.classic to javafx.fxml;
    opens edu.csueastbay.cs401.ttruong to javafx.fxml;
    opens edu.csueastbay.cs401.felixchoypong to javafx.fxml, javafx.graphics, javafx.media;
    opens edu.csueastbay.cs401.khlPong to javafx.fxml;
    opens edu.csueastbay.cs401.srishti to javafx.fxml;
    opens edu.csueastbay.cs401.thansen to javafx.fxml;
    opens edu.csueastbay.cs401.ethan to javafx.fxml;
    opens edu.csueastbay.cs401.ejamdar to javafx.fxml;
    opens edu.csueastbay.cs401.ethan.game to javafx.base;
    opens edu.csueastbay.cs401.nly to javafx.fxml;
    opens edu.csueastbay.cs401.StarWarsPong to javafx.fxml;
    opens edu.csueastbay.cs401.DlinPong to javafx.fxml;
    opens edu.csueastbay.cs401.frantic to javafx.fxml;
    opens edu.csueastbay.cs401.jzepeda to javafx.fxml;
    opens edu.csueastbay.cs401.pyae to javafx.fxml;
    opens edu.csueastbay.cs401.rravi to javafx.fxml;
    opens edu.csueastbay.cs401.lbernard to javafx.fxml;
    opens edu.csueastbay.cs401.ronan to javafx.fxml;
    opens edu.csueastbay.cs401.csaeteurn to javafx.fxml;
    opens edu.csueastbay.cs401.HanishPatel to javafx.fxml;
    opens edu.csueastbay.cs401.psinha to javafx.fxml;
    opens edu.csueastbay.cs401.ggamata2011 to javafx.fxml;
    opens edu.csueastbay.cs401.vnguyen to javafx.fxml;
    opens edu.csueastbay.cs401.mattsPong to javafx.fxml;
    opens edu.csueastbay.cs401.LSingh to javafx.fxml;
    opens edu.csueastbay.cs401.psander to javafx.fxml;

    exports edu.csueastbay.cs401.pong;

}