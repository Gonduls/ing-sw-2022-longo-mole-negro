package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;

public class ModelObserver {
    Room room;


    public ModelObserver(Room room){
        this.room=room;
    }

    //move student from a player entrance to an island
    public void moveFromEntranceToIsland(int player, Color color ,int islandIndex){
        room.sendBroadcast(new MoveStudent("ENTRANCE:"+player, "ISLAND:" + islandIndex, color));
    }

    public void moveFromEntranceToTable(int player, Color color){
        room.sendBroadcast(new MoveStudent("ENTRANCE:"+player, "DININGROOM:" + player, color));
    }

    public void moveMotherNature(int position){
        room.sendBroadcast(new MoveMotherNature(position));
    }

    public void moveTowerToPlayer(int player, int islandIndex,int numberOfTowers){
        room.sendBroadcast(new MoveTowers("ISLAND:"+islandIndex, "PLAYER:"+player, numberOfTowers));
    }

    public void moveTowerToIsland(int player, int islandIndex,int numberOfTowers){
        room.sendBroadcast(new MoveTowers("PLAYER:"+player, "ISLAND:"+islandIndex, numberOfTowers));

    }

    public void mergeIslands(int firstIslandIndex, int secondIslandIndex){

        room.sendBroadcast(new MergeIslands(firstIslandIndex, secondIslandIndex));

    }

    public void activateCharacterCard(int id,int playerNumber){
        room.sendBroadcast(new ActivateCharacterCard(id, playerNumber));
    }

    public void removeNoEntry(int islandIndex){
        room.sendBroadcast(new NoEntry(false,islandIndex));
    }


    public void addNoEntry(int islandIndex){
        room.sendBroadcast(new NoEntry(true,islandIndex));
    }

    public void changePhase(GamePhase phase){
        room.sendBroadcast(new ChangePhase(phase));
    }


    public void changeTurn(int player){
        room.sendBroadcast(new ChangeTurn(player));
    }


    public void addStudentToCard(int idCard, Color color){
        room.sendBroadcast(new AddStudentTo("CARD:"+idCard,color));
    }

    public void moveStudentFromCardToEntrance(int idCard ,int player,Color color){
        room.sendBroadcast(new MoveStudent("CARD:"+idCard, "ENTRANCE:"+player ,color));

    }

    public void moveStudentFromCardToTable(int idCard ,int player,Color color){
        room.sendBroadcast(new MoveStudent("CARD:"+idCard, "DININGROOM:"+player ,color));

    }

    public void moveStudentFromEntranceToCard(int idCard ,int player,Color color){
        room.sendBroadcast(new MoveStudent( "ENTRANCE:"+player ,"CARD:"+idCard,color));

    }

    public void moveStudentFromTableToCard(int idCard ,int player,Color color){
        room.sendBroadcast(new MoveStudent( "DININGROOM:"+player ,"CARD:"+idCard,color));

    }

    public void playAssistantCard(AssistantCard assCard, int player){
        room.sendBroadcast(new PlayAssistantCard(assCard, player));
    }


    public void setProfessorTo(Color color, int playerNumber){
        room.sendBroadcast(new SetProfessorTo(color, playerNumber));
    }

    public void payPrice(int amount, int playerNumber){

       // room.sendBroadcast(new PayPrice(amount, playerNumber));
    }

    public void addCoin(int playerNumber){
        room.sendBroadcast(new AddCoin(playerNumber));
    }

    public void addStudentToEntrance(int playerNumber, Color color){
        room.sendBroadcast(new AddStudentTo("ENTRANCE:"+playerNumber, color));
    }

    public void addStudentToCloud(int cloudIndex, Color color){
        room.sendBroadcast(new AddStudentTo("CLOUD:"+cloudIndex, color));

    }

    public void notifyCharacterCard(int card){
        room.sendBroadcast(new NotifyCharacterCard(card));
    }

    public void moveStudentFromCardToIsland(int islandIndex, Color color){
        room.sendBroadcast(new MoveStudent("CARD:0", "ISLAND:"+islandIndex, color));
    }

    public void addStudentToIsland(int islandIndex, Color color){
        room.sendBroadcast(new AddStudentTo("ISLAND:"+islandIndex, color));
    }

    public void moveStudentFromCloudToPlayer(int cloudindex, int playerNumber, Color color){
        room.sendBroadcast(new MoveStudent("CLOUD:"+cloudindex, "ENTRANCE:"+playerNumber, color));
    }
}
