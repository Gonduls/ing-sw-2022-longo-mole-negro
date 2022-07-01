package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Handles StartMenu.FXML.
 * A redirection scene that allows the user to either create or join a room or to logout.
 */
public class StartMenuController {

    /**
     * Switches to the CreateGame scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToCreateGame(ActionEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/GameCreation.fxml", 477, 477);
        event.consume();
    }

    /**
     * Switches to the JoinGame scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToJoinGame(ActionEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/JoinGame.fxml", 477, 477);
        event.consume();
    }

    /**
     * Switches to the FilterSearch scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToFilterSearch(ActionEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/FilterSearch.fxml", 477, 477);
        event.consume();
    }

    /**
     * Logs out and switches back to Connection scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToConnection(ActionEvent event) throws IOException {
        GUI.getInstance().getClientController().logout();
        GUI.getInstance().changeScene("/fxml/Connection.fxml", 477, 477);
        event.consume();
    }
}
