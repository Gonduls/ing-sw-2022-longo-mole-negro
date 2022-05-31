package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.RoomInfo;

import java.util.List;

public interface UI {

    //todo: check functions' names, add functions
    void printStatus();
    void showPublicRooms(List<RoomInfo> rooms);
    void showMessage(Message message);
    void createGame(int numberOfPlayers, boolean expert, ClientModelManager cmm);
    void killGame();
}
