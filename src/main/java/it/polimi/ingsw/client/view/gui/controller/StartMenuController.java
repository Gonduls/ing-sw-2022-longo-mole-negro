package it.polimi.ingsw.client.view.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartMenuController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    public void switchToCreateGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/GameCreation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

    public void switchToJoinGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/JoinGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLobby(ActionEvent event) throws IOException {
        //TODO : chiedi filtri
        root = FXMLLoader.load(getClass().getResource("/fxml/Lobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

    public void switchToConnection(ActionEvent event) throws IOException {

        //TODO: in realtà il logout deve fare tutto il procedimento
        root = FXMLLoader.load(getClass().getResource("/fxml/Connection.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

}
