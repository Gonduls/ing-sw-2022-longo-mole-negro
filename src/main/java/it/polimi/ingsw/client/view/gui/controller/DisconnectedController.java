package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
        import it.polimi.ingsw.messages.PlayerDisconnect;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Label;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;

/**
 * Handles the player disconnected scene
 */
public class DisconnectedController implements Initializable {
    private static PlayerDisconnect message;

    @FXML
    private Label MESSAGES;

    /**
     * Called to initialize a controller after its root element has been completely processed.
     * @param url  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(message == null) return;
        MESSAGES.setText("Player " + message.username() + " has left the room");
    }

    /**
     * Returns to the Lobby
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void returnToLobby(ActionEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/StartMenu.fxml", 500, 477);
        GUI.getInstance().setSetScene(false);
        GUI.getInstance().getClientController().startOver();
    }

    /**
     * Logs the player out
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void logout(ActionEvent event) throws IOException{
        GUI.getInstance().changeScene("/fxml/Connection.fxml", 500, 477);
        GUI.getInstance().setSetScene(false);
        GUI.getInstance().getClientController().logout();
        GUI.getInstance().getClientController().startOver();
    }

    /**
     * Sets the class's message to a given message of PlayerDisconnect type
     * @param message the given PlayerDisconnect message
     */
    public static void setDisconnectMessage(PlayerDisconnect message){
        DisconnectedController.message = message;
    }
}
