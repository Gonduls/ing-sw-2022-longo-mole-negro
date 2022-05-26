module PSP3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.jline.style;
    requires org.fusesource.jansi;


    opens it.polimi.ingsw.client.view.gui to javafx.fxml;
    exports it.polimi.ingsw.client.view.gui;

}