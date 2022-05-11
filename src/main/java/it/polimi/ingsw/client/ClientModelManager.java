package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.*;

import java.util.HashMap;
import java.util.List;

public class ClientModelManager {
    String[] players;
    HashMap<String, School> schools;
    Professors professors;
    List<Island> islands;
    List<Cloud> clouds;

    public ClientModelManager(int numberOfPlayers, boolean expert){
        players = new String[numberOfPlayers];
    }

    void updateModel(Message message){
        //parsing message

        //update model

        //view.update()
    }
    void addPlayer(String player, int position){}
}
