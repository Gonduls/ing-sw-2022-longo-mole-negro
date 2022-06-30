package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            GUI.getInstance().setInARoom(true);
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
        Parent root;
        Stage stage;
        Scene scene;

        root = FXMLLoader.load(getClass().getResource("/fxml/StartMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();

    }
}
