package it.polimi.ingsw.server;


import it.polimi.ingsw.messages.Message;

/**
 *  THIS CLASS IS ONLY USED IN TESTING
 *  THIS IS DONE TO TEST THE MODEL WITHOUT HAVING TO DEAL WITH THE NETWORK STUFF
 */

public class DummyRoom extends  Room{
    public DummyRoom(int id, int numberOfPlayers, boolean expert) {
        super(id, numberOfPlayers, expert);
    }

    @Override
    public void sendBroadcast(Message message) {
        System.out.println(message.toString());
    }
}
