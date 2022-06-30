package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handles StartMenu.FXML.
 * A redirection scene that allows the user to either create or join a room or to logout.
 */
public class StartMenuController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    /**
     * Switches to the CreateGame scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToCreateGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/GameCreation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to the JoinGame scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToJoinGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/JoinGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to the FilterSearch scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToFilterSearch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/FilterSearch.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Logs out and switches back to Connection scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToConnection(ActionEvent event) throws IOException {
        GUI.getInstance().getClientController().logout();
        root = FXMLLoader.load(getClass().getResource("/fxml/Connection.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

}
