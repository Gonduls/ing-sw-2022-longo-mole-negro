package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.RoomInfo;

import java.util.List;

public interface UI {

    /**
     * Refreshes view
     */
    void refresh();

    /**
     * Prints out all public rooms to the player
     * @param rooms The room info relative to the public rooms
     */
    void showPublicRooms(List<RoomInfo> rooms);

    /**
     * Prints a message that has to be delivered to the player
     * @param message the message to be printed
     */
    void showMessage(Message message);

    /**
     * Sets game starting parameters
     * @param numberOfPlayers Either 2, 3, or 4
     * @param expert True if game is expert mode, false otherwise
     * @param cmm The ClientModelManager connected to the starting game
     */
    void createGame(int numberOfPlayers, boolean expert, ClientModelManager cmm);

    /**
     * Communicates that a merge has occurred and the island to be removed
     * @param secondIsland The index of the island that was removed in the model
     */
    void merge(int secondIsland);
}
