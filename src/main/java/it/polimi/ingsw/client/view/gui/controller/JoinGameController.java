package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinGameController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField roomID;

    @FXML
    private Label cantAccessRoom;


    public void accessChosenRoom(ActionEvent actionEvent) {
        boolean canAccessRoom = GUI.getInstance().getClientController().accessRoom(Integer.parseInt(roomID.getText()));
        if(canAccessRoom) {
            GUI.getInstance().setInARoom(true);
        }else
            cantAccessRoom.setVisible(true);

    }
}
