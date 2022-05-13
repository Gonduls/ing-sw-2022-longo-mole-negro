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
    StudentHolder[] characterCards;


    public ClientModelManager(int numberOfPlayers, boolean expert){
        players = new String[numberOfPlayers];
    }

    void updateModel(Message message){
        //parsing message

        //update model

        //view.update()
    }
    void addPlayer(String player, int position){}

    void putSHInCharacterCard(int index0, int index1, int index2) {
        this.characterCards[0] = CharacterCard.hasStudentHolder(index0) ? new StudentHolder() : null;
        this.characterCards[1] = CharacterCard.hasStudentHolder(index1) ? new StudentHolder() : null;
        this.characterCards[2] = CharacterCard.hasStudentHolder(index2) ? new StudentHolder() : null;
    }

    private StudentHolder parsePosition(String pos){
        String[] splitPos = pos.split(":");
        int index = Integer.parseInt(splitPos[1]);

        switch (splitPos[0]) {
            case("ISLAND"):
                return islands.get(index);
            case("CLOUD"):
                return clouds.get(index);
            case("ENTRANCE"):
                return schools.get(index).getStudentsAtEntrance();
            case("DININGROOM"):
                return schools.get(index).getStudentsAtTables();
            case("CHARACTERCARD"):
                return characterCards[index];
            default:
                return null;
        }
    }
}
