package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.messages.CreateRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *Handles the Game Creation.
 * It allows the user to create a new Game with the specified information about number of players, expert mode
 * and private mode.
 */
public class GameCreationController implements Initializable {

    @FXML
    private Spinner<Integer> mySpinner;

    @FXML
    private CheckBox checkPrivate;

    @FXML
    private CheckBox checkExpert;

    @FXML
    private Button continueButton;

    @FXML
    private Button backButton;

    @FXML
    private Label errorAccessRoom;

    /**
     * Initializes the scene.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,4);
        mySpinner.setValueFactory(valueFactory);

    }

    /**
     * Creates a new room with the chosen information.
     * If successful, returns the newly created roomID and waits for other players to join.
     * Else, it shows an error message.
     * @param event button click
     */
    public void generateRoomID(ActionEvent event) {

        boolean expertGame = checkExpert.isSelected();
        boolean privateGame = checkPrivate.isSelected();
        CreateRoom createRoom = new CreateRoom(mySpinner.getValue(), expertGame, privateGame);
        Integer roomID = (GUI.getInstance().getClientController().createRoom(createRoom));
        if(roomID < 0) {
            errorAccessRoom.setText("Could not access Room. Try Again.");
            errorAccessRoom.setVisible(true);
        } else {
            backButton.setVisible(false);
            backButton.setDisable(true);
            continueButton.setVisible(false);
            continueButton.setDisable(true);
            errorAccessRoom.setText("Room ID: " + roomID + " Waiting for players...");
            errorAccessRoom.setVisible(true);
            GUI.setInARoom(true);
            GUI.getInstance().refresh();
        }
    }

    /**
     * Switches back to previous scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void returnToPreviousScene(ActionEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/StartMenu.fxml", 500, 477);
    }

}
