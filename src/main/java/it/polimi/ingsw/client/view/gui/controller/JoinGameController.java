package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Handles the JoinGame scene.
 * Allows the user to access a Room directly by its ID.
 */
public class JoinGameController {

    @FXML
    private TextField roomID;

    @FXML
    private Label cantAccessRoom;

    @FXML
    private Button continueButton;

    @FXML
    private Button backButton;


    /**
     *Tries to access the room with the specified ID.
     * If it fails, it shows an error message, else it waits for all the players to join.
     * @param actionEvent button click
     */
    public void accessChosenRoom(ActionEvent actionEvent) {
        boolean canAccessRoom = GUI.getInstance().getClientController().accessRoom(Integer.parseInt(roomID.getText()));
        if(canAccessRoom) {
            continueButton.setVisible(false);
            continueButton.setDisable(true);
            backButton.setVisible(false);
            backButton.setDisable(true);
            GUI.setInARoom(true);
            GUI.getInstance().refresh();
        }else
            cantAccessRoom.setVisible(true);

    }

    /**
     * Switches back to previous scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void returnToPreviousScene(ActionEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/StartMenu.fxml", 477, 477);


    }
}
