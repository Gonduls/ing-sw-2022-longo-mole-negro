package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.client.view.gui.controller.LobbyController;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.RoomInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class GUI extends Application implements UI{
    ClientController cc;
    static GUI instance;
    public static GUI getInstance(){
        if(instance == null)
            instance = new GUI();

        return instance;
    }

    public static void main(String[] args) {
        Application.launch(args);}

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Connection.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/Elements/Logo2.png"))));
        stage.show();


    }


    @Override
    public void killGame(){}

    @Override
    public void printStatus() {

    }

    @Override
    public void showPublicRooms(List<RoomInfo> rooms) {
        //chiama un metodo da definire in lobbycontroller passandogli la lista che poi utilizza per compilare quella gfx

    }

    @Override
    public void showMessage(Message message) {

    }

    @Override
    public void createGame(int numberOfPlayers, boolean expert, ClientModelManager cmm) {

    }

    public void createClientController(String ip, int port) throws IOException{
        cc = new ClientController(this, ip, port);
    }

    public ClientController getClientController() {
        return cc;
    }
}
