package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.Log;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Handles the Login.FXML.
 * It allows the user to choose a unique username.
 */
public class LoginController {

    @FXML
    private TextField textUsername;

    @FXML
    private Label unableToLoginId;

    /**
     * Checks if the username wasn't already taken.
     * If not, it switches to the next scene, else it shows an error messages and waits for the user to
     * chose another username.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToStartMenuScene(ActionEvent event) throws IOException {
        try {
            boolean verifiedUsername = GUI.getInstance().getClientController().login(textUsername.getText());
            if(verifiedUsername) {
                GUI.getInstance().setUsername(textUsername.getText());
                GUI.getInstance().changeScene("/fxml/StartMenu.fxml", 500, 477);
            }
            else
                unableToLoginId.setVisible(true);

        }catch (UnexpectedMessageException e) {
            Log.logger.severe(e.getMessage());
        }

    }
}
