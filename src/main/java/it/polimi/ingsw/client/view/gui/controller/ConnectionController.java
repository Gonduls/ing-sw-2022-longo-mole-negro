package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handles the Connection scene.
 * It asks the user for the ServerID and port in order to establish a connection with the Server.
 */
public class ConnectionController {
    private Parent root;
    private Scene scene;
    private Stage stage;

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

        root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

}
