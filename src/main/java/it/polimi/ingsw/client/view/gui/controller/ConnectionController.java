package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Handles the Connection scene.
 * It asks the user for the ServerID and port in order to establish a connection with the Server.
 */
public class ConnectionController {
    @FXML
    private TextField textServerIP;

    @FXML
    private TextField textServerPort;

    /**
     * If it manages to establish a connection, it switches to the next scene.
     * @param event the button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToLoginScene(ActionEvent event) throws IOException {
        try{
            GUI.getInstance().createClientController(textServerIP.getText(), Integer.parseInt(textServerPort.getText()));
        }catch (IOException e){
            return;
        }

        GUI.getInstance().changeScene("/fxml/Login.fxml", 477, 477);
    }
}