package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.RoomInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GUI extends Application implements UI{

    public static void main(String[] args) {
        Application.launch(args);}

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/fxml/Connection.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

    public void connectionScene() {

    }


    @Override
    public void printStatus() {

    }

    @Override
    public void showPublicRooms(List<RoomInfo> rooms) {

    }

    @Override
    public void showMessage(Message message) {

    }

    @Override
    public void createGame(int numberOfPlayers, boolean expert, ClientModelManager cmm) {

    }



}
