package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class JoinGameController {
    @FXML
    private TextField roomID;

    @FXML
    private Label cantAccessRoom;


    public void accessChosenRoom(ActionEvent actionEvent) {
        boolean canAccessRoom = GUI.getInstance().getClientController().accessRoom(Integer.parseInt(roomID.getText()));
        if(canAccessRoom) {
            //TODO joins room with that ID

        }else
            cantAccessRoom.setVisible(true);

    }
}
