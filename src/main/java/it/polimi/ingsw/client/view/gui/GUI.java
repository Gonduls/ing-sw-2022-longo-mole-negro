package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.RoomInfo;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class GUI extends Application implements UI{

    public static void main(String args[]){
        Login.main(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

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
