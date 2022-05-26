module PSP3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.jline.style;
    requires org.fusesource.jansi;

    exports it.polimi.ingsw.client.view.gui to javafx.graphics;
    exports it.polimi.ingsw.client.view.gui.controller to javafx.fxml;
}