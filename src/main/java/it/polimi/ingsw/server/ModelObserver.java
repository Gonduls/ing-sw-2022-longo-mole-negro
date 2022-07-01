package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.AssistantCard;

/**
 * Creates messages that update the model
 */
public class ModelObserver {
    Room room;

    /**
     * Sets the room
     * @param room The room where the messages will be sent
     */
    public ModelObserver(Room room){
        this.room=room;
    }

    /**
     * Creates and sends a MoveStudent message
     * @param player The index of the player
     * @param color The target color
     * @param islandIndex The target island's index
     */
    public void moveFromEntranceToIsland(int player, Color color ,int islandIndex){
        room.sendBroadcast(new MoveStudent("ENTRANCE:"+player, "ISLAND:" + islandIndex, color));
    }

    /**
     * Creates and sends a MoveStudent message
     * @param player The index of the player
     * @param color The target color
     */
    public void moveFromEntranceToTable(int player, Color color){
        room.sendBroadcast(new MoveStudent("ENTRANCE:"+player, "DININGROOM:" + player, color));
    }

    /**
     * Creates and sends a MoveMotherNature message
     * @param position MotherNature final island index
     */
    public void moveMotherNature(int position){
        room.sendBroadcast(new MoveMotherNature(position));
    }

    /**
     * Creates and sends a MoveTowers message
     * @param player The index of the player
     * @param islandIndex The target island's index
     * @param numberOfTowers The towers' amount
     */
    public void moveTowerToPlayer(int player, int islandIndex,int numberOfTowers){
        room.sendBroadcast(new MoveTowers("ISLAND:"+islandIndex, "PLAYER:"+player, numberOfTowers));
    }

    /**
     * Creates and sends a MoveTowers message
     * @param player The index of the player
     * @param islandIndex The target island's index
     * @param numberOfTowers The towers' amount
     */
    public void moveTowerToIsland(int player, int islandIndex,int numberOfTowers){
        room.sendBroadcast(new MoveTowers("PLAYER:"+player, "ISLAND:"+islandIndex, numberOfTowers));

    }

    /**
     * Creates and sends a MergeIslands message
     * @param firstIslandIndex The target first island's index
     * @param secondIslandIndex The target second island's index
     */
    public void mergeIslands(int firstIslandIndex, int secondIslandIndex){
        room.sendBroadcast(new MergeIslands(firstIslandIndex, secondIslandIndex));
    }

    /**
     * Creates and sends a ActivateCharacterCard message
     * @param idCard The card identifier
     * @param player The index of the player
     */
    public void activateCharacterCard(int idCard,int player){
        room.sendBroadcast(new ActivateCharacterCard(idCard, player));
    }

    /**
     * Creates and sends a NoEntry message
     * @param islandIndex The target island's index
     */
    public void removeNoEntry(int islandIndex){
        room.sendBroadcast(new NoEntry(false,islandIndex));
    }

    /**
     * Creates and sends a NoEntry message
     * @param islandIndex The target island's index
     */
    public void addNoEntry(int islandIndex){
        room.sendBroadcast(new NoEntry(true,islandIndex));
    }

    /**
     * Creates and sends a ChangePhase message
     * @param phase The target phase
     */
    public void changePhase(GamePhase phase){
        room.sendBroadcast(new ChangePhase(phase));
    }

    /**
     * Creates and sends a ChangeTurn message
     * @param player The index of the player
     */
    public void changeTurn(int player){
        room.sendBroadcast(new ChangeTurn(player));
    }

    /**
     * Creates and sends a AddStudentTo message
     * @param idCard The card identifier
     * @param color The target color
     */
    public void addStudentToCard(int idCard, Color color){
        room.sendBroadcast(new AddStudentTo("CARD:"+idCard,color));
    }

    /**
     * Creates and sends a MoveStudent message
     * @param idCard The card identifier
     * @param player The index of the player
     * @param color The target color
     */
    public void moveStudentFromCardToEntrance(int idCard ,int player,Color color){
        room.sendBroadcast(new MoveStudent("CARD:"+idCard, "ENTRANCE:"+player ,color));
    }

    /**
     * Creates and sends a MoveStudent message
     * @param idCard The card identifier
     * @param player The index of the player
     * @param color The target color
     */
    public void moveStudentFromCardToTable(int idCard ,int player,Color color){
        room.sendBroadcast(new MoveStudent("CARD:"+idCard, "DININGROOM:"+player ,color));
    }

    /**
     * Creates and sends a MoveStudent message
     * @param idCard The card identifier
     * @param player The index of the player
     * @param color The target color
     */
    public void moveStudentFromEntranceToCard(int idCard ,int player,Color color){
        room.sendBroadcast(new MoveStudent( "ENTRANCE:"+player ,"CARD:"+idCard,color));
    }

    /**
     * Creates and sends a PlayAssistantCard message
     * @param assistantCard The target AssistantCard
     * @param player The index of the player
     */
    public void playAssistantCard(AssistantCard assistantCard, int player){
        room.sendBroadcast(new PlayAssistantCard(assistantCard, player));
    }

    /**
     * Creates and sends a SetProfessorTo message
     * @param color The target color
     * @param player The index of the player
     */
    public void setProfessorTo(Color color, int player){
        room.sendBroadcast(new SetProfessorTo(color, player));
    }

    /**
     * Creates and sends a AddCoin message
     * @param player The index of the player
     */
    public void addCoin(int player){
        room.sendBroadcast(new AddCoin(player));
    }

    /**
     * Creates and sends a AddStudentTo message
     * @param player The index of the player
     * @param color The target color
     */
    public void addStudentToEntrance(int player, Color color){
        room.sendBroadcast(new AddStudentTo("ENTRANCE:"+player, color));
    }

    /**
     * Creates and sends a AddStudentTo message
     * @param cloudIndex The target cloud index
     * @param color The target color
     */
    public void addStudentToCloud(int cloudIndex, Color color){
        room.sendBroadcast(new AddStudentTo("CLOUD:"+cloudIndex, color));

    }

    /**
     * Creates and sends a NotifyCharacterCard message
     * @param idCard The card identifier
     */
    public void notifyCharacterCard(int idCard){
        room.sendBroadcast(new NotifyCharacterCard(idCard));
    }

    /**
     * Creates and sends a MoveStudent message
     * @param islandIndex The target island's index
     * @param color The target color
     */
    public void moveStudentFromCardToIsland(int islandIndex, Color color){
        room.sendBroadcast(new MoveStudent("CARD:0", "ISLAND:"+islandIndex, color));
    }

    /**
     * Creates and sends a AddStudentTo message
     * @param islandIndex The target island's index
     * @param color The target color
     */
    public void addStudentToIsland(int islandIndex, Color color){
        room.sendBroadcast(new AddStudentTo("ISLAND:"+islandIndex, color));
    }

    /**
     * Creates and sends a MoveStudent message
     * @param cloudIndex The target cloud index
     * @param player The index of the player
     * @param color The target color
     */
    public void moveStudentFromCloudToPlayer(int cloudIndex, int player, Color color){
        room.sendBroadcast(new MoveStudent("CLOUD:"+ cloudIndex, "ENTRANCE:"+player, color));
    }

    /**
     * Creates and sends a MoveStudent message
     * @param player The index of the player
     * @param color The target color
     */
    public void moveStudentFromTableToEntrance(int player, Color color){
        room.sendBroadcast(new MoveStudent("DININGROOM:"+player, "ENTRANCE:" + player, color));

    }

    /**
     * Creates and sends a MoveStudent message
     * @param player The index of the player
     * @param color The target color
     */
    public void moveStudentFromTAbleToBag(int player, Color color){
        room.sendBroadcast(new MoveStudent("DININGROOM:"+player, "BAG", color));
    }

    /**
     * Creates and sends a EndGame message
     * @param winners The array containing the winners' names
     */
    public void sendEndGame(String[] winners){
        room.sendBroadcast(new EndGame(winners));
    }
}
