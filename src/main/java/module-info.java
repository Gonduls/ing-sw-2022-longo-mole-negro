module PSP3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.jline.style;
    requires org.fusesource.jansi;

    opens it.polimi.ingsw.client.view.gui.controller to javafx.fxml;
    opens it.polimi.ingsw to javafx.fxml;
    exports it.polimi.ingsw;
    exports it.polimi.ingsw.client.view.gui;
    exports it.polimi.ingsw.client.view.gui.controller;
}