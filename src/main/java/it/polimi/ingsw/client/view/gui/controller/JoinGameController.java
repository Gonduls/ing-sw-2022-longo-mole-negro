package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JoinGameController {
    @FXML
    private TextField roomID;

    @FXML
    private Label cantAccessRoom;


    public void accessChosenRoom(ActionEvent actionEvent) {
        //ehhh non credo funzioni
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        GUI.getInstance().setStage(stage);
        boolean canAccessRoom = GUI.getInstance().getClientController().accessRoom(Integer.parseInt(roomID.getText()));
        if(canAccessRoom) {
            GUI.getInstance().setInARoom(true);
        }else
            cantAccessRoom.setVisible(true);

    }
}
